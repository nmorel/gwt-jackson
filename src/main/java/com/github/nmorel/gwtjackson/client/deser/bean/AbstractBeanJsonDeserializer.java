package com.github.nmorel.gwtjackson.client.deser.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link JsonDeserializer} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonDeserializer<T, B extends InstanceBuilder<T>> extends JsonDeserializer<T> {

    private final Map<String, BeanPropertyDeserializer<T, B, ?>> deserializers = new LinkedHashMap<String, BeanPropertyDeserializer<T, B,
        ?>>();

    private final Map<String, BackReferenceProperty<T, ?>> backReferenceDeserializers = new LinkedHashMap<String,
        BackReferenceProperty<T, ?>>();

    private IdentityDeserializationInfo<?> identityInfo;

    private SuperclassDeserializationInfo<T> superclassInfo;

    protected abstract boolean isInstantiable();

    protected abstract B newInstanceBuilder( JsonDeserializationContext ctx );

    /**
     * Add a {@link BeanPropertyDeserializer}
     *
     * @param propertyName name of the property
     * @param deserializer deserializer
     */
    protected void addProperty( String propertyName, BeanPropertyDeserializer<T, B, ?> deserializer ) {
        deserializers.put( propertyName, deserializer );
    }

    /**
     * Add a {@link BackReferenceProperty}
     *
     * @param referenceName name of the reference
     * @param backReference backReference
     */
    protected void addProperty( String referenceName, BackReferenceProperty<T, ?> backReference ) {
        backReferenceDeserializers.put( referenceName, backReference );
    }

    @Override
    public T doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( null != identityInfo && !JsonToken.BEGIN_OBJECT.equals( reader.peek() ) ) {
            IdKey id = identityInfo.getIdKey( reader, ctx );
            Object instance = ctx.getObjectWithId( id );
            if ( null == instance ) {
                throw ctx.traceError( "Cannot find an object with id " + id );
            }
            return (T) instance;
        }

        T result;

        if ( null != superclassInfo && superclassInfo.isIncludeTypeInfo() ) {
            switch ( superclassInfo.getInclude() ) {
                case PROPERTY:
                    // the type info is the first property of the object
                    reader.beginObject();
                    String name = reader.nextName();
                    if ( !superclassInfo.getPropertyName().equals( name ) ) {
                        // the type info is always the first value. If we don't find it, we throw an error
                        throw ctx.traceError( "Cannot find the type info" );
                    }
                    String typeInfoProperty = reader.nextString();

                    result = deserializeSubtype( reader, ctx, typeInfoProperty );
                    reader.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    reader.beginObject();
                    String typeInfoWrapObj = reader.nextName();
                    reader.beginObject();
                    result = deserializeSubtype( reader, ctx, typeInfoWrapObj );
                    reader.endObject();
                    reader.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    reader.beginArray();
                    String typeInfoWrapArray = reader.nextString();
                    reader.beginObject();
                    result = deserializeSubtype( reader, ctx, typeInfoWrapArray );
                    reader.endObject();
                    reader.endArray();
                    break;

                default:
                    throw ctx.traceError( "JsonTypeInfo.As." + superclassInfo.getInclude() + " is not supported" );
            }
        } else if ( isInstantiable() ) {
            reader.beginObject();
            result = deserializeObject( reader, ctx );
            reader.endObject();
        } else {
            throw ctx.traceError( "Cannot instantiate the type" );
        }

        return result;
    }

    /**
     * Deserializes all the properties of the bean. The {@link JsonReader} must be in a json object.
     *
     * @param reader reader
     * @param ctx context of the deserialization process
     *
     * @throws IOException if an error occurs while reading a property
     */
    public final T deserializeObject( final JsonReader reader, final JsonDeserializationContext ctx ) throws IOException {
        B builder = newInstanceBuilder( ctx );

        if ( !JsonToken.NAME.equals( reader.peek() ) ) {
            // empty object, just return a new instance with no property
            return builder.build( ctx );
        }

        // we store any property place before the identity property to ensure that we add the instance to the identity map before
        // resolving its children
        // TODO do the same with JsonCreator properties
        boolean foundIdentity;
        List<BufferedProperty<T, B, ?>> bufferedProperties;
        if ( null == identityInfo ) {
            bufferedProperties = Collections.emptyList();
            foundIdentity = true;
        } else {
            bufferedProperties = new ArrayList<BufferedProperty<T, B, ?>>();
            foundIdentity = false;
        }

        while ( JsonToken.NAME.equals( reader.peek() ) ) {
            String name = reader.nextName();

            if ( null != identityInfo && identityInfo.getPropertyName().equals( name ) ) {
                readIdentityProperty( reader, ctx, builder, name );
                foundIdentity = true;
                flushBufferedProperties( bufferedProperties, builder, ctx );
            } else if ( JsonToken.NULL.equals( reader.peek() ) ) {
                reader.skipValue();
            } else {
                BeanPropertyDeserializer<T, B, ?> property = deserializers.get( name );
                if ( null == property ) {
                    // TODO add a configuration to tell if we skip or throw an unknown property
                    reader.skipValue();
                } else if ( foundIdentity ) {
                    property.deserialize( reader, builder, ctx );
                } else {
                    bufferedProperties.add( property.bufferProperty( reader, ctx ) );
                }
            }
        }

        // flush any properties left. it can happen if we expect an identity property and it's not present.
        flushBufferedProperties( bufferedProperties, builder, ctx );

        return builder.build( ctx );
    }

    private void readIdentityProperty( JsonReader reader, final JsonDeserializationContext ctx, B builder,
                                       String name ) throws IOException {
        if ( JsonToken.NULL.equals( reader.peek() ) ) {
            // identity property is here but empty
            reader.skipValue();
        } else {
            BeanPropertyDeserializer<T, B, ?> property = deserializers.get( name );
            final Object id;
            if ( null == property ) {
                id = identityInfo.getDeserializer( ctx ).deserialize( reader, ctx );
            } else {
                id = property.deserialize( reader, builder, ctx );
            }

            builder.addCallback( new InstanceBuilderCallback<T>() {
                @Override
                public void onInstanceCreated( T instance ) {
                    ctx.addObjectId( identityInfo.newIdKey( id ), instance );
                }
            } );
        }
    }

    private void flushBufferedProperties( List<BufferedProperty<T, B, ?>> bufferedProperties, B builder, JsonDeserializationContext ctx ) {
        if ( !bufferedProperties.isEmpty() ) {
            for ( BufferedProperty<T, B, ?> bufferedProperty : bufferedProperties ) {
                bufferedProperty.flush( builder, ctx );
            }
            bufferedProperties.clear();
        }
    }

    public final T deserializeSubtype( JsonReader reader, JsonDeserializationContext ctx, String typeInfo ) throws IOException {
        SubtypeDeserializer<? extends T> deserializer = superclassInfo.getDeserializer( typeInfo );
        if ( null == deserializer ) {
            throw ctx.traceError( "No deserializer found for the type " + typeInfo );
        }

        return deserializer.deserializeObject( reader, ctx );
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T value, JsonDeserializationContext ctx ) {
        if ( null == value ) {
            return;
        }

        if ( null != superclassInfo ) {
            SubtypeDeserializer deserializer = superclassInfo.getDeserializer( value.getClass() );
            if ( null == deserializer ) {
                // should never happen, the generator add every subtype to the map
                throw ctx.traceError( "Cannot find deserializer for class " + value.getClass() );
            }
            JsonDeserializer<T> jsonDeserializer = deserializer.getDeserializer( ctx );
            if ( jsonDeserializer != this ) {
                // we test if it's not this deserializer to avoid an infinite loop
                jsonDeserializer.setBackReference( referenceName, reference, value, ctx );
                return;
            }
        }

        BackReferenceProperty backReferenceProperty = backReferenceDeserializers.get( referenceName );
        if ( null == backReferenceProperty ) {
            throw ctx.traceError( "The back reference '" + referenceName + "' does not exist" );
        }
        backReferenceProperty.setBackReference( value, reference, ctx );
    }

    protected final IdentityDeserializationInfo<?> getIdentityInfo() {
        return identityInfo;
    }

    protected final void setIdentityInfo( IdentityDeserializationInfo<?> identityInfo ) {
        this.identityInfo = identityInfo;
    }

    protected final SuperclassDeserializationInfo<T> getSuperclassInfo() {
        return superclassInfo;
    }

    protected final void setSuperclassInfo( SuperclassDeserializationInfo<T> superclassInfo ) {
        this.superclassInfo = superclassInfo;
    }
}
