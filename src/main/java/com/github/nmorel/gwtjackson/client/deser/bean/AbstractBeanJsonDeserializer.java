package com.github.nmorel.gwtjackson.client.deser.bean;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonDeserializer<T, B extends InstanceBuilder<T>> extends JsonDeserializer<T> {

    private final Map<String, DecoderProperty<T, B>> decoders = new LinkedHashMap<String, DecoderProperty<T, B>>();

    private final Map<String, BackReferenceProperty<T, ?>> backReferenceDecoders = new LinkedHashMap<String, BackReferenceProperty<T, ?>>();

    private IdentityDeserializationInfo<?> identityInfo;

    private SuperclassDeserializationInfo<T> superclassInfo;

    protected abstract boolean isInstantiable();

    protected abstract B newInstanceBuilder( JsonDecodingContext ctx );

    /**
     * Add a {@link DecoderProperty}
     *
     * @param propertyName name of the property
     * @param decoder decoder
     */
    protected void addProperty( String propertyName, DecoderProperty<T, B> decoder ) {
        decoders.put( propertyName, decoder );
    }

    /**
     * Add a {@link BackReferenceProperty}
     *
     * @param referenceName name of the reference
     * @param backReference backReference
     */
    protected void addProperty( String referenceName, BackReferenceProperty<T, ?> backReference ) {
        backReferenceDecoders.put( referenceName, backReference );
    }

    @Override
    public T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
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

                    result = decodeSubtype( reader, ctx, typeInfoProperty );
                    reader.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    reader.beginObject();
                    String typeInfoWrapObj = reader.nextName();
                    reader.beginObject();
                    result = decodeSubtype( reader, ctx, typeInfoWrapObj );
                    reader.endObject();
                    reader.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    reader.beginArray();
                    String typeInfoWrapArray = reader.nextString();
                    reader.beginObject();
                    result = decodeSubtype( reader, ctx, typeInfoWrapArray );
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

    public final T deserializeObject( final JsonReader reader, final JsonDecodingContext ctx ) throws IOException {
        B builder = newInstanceBuilder( ctx );

        while ( JsonToken.NAME.equals( reader.peek() ) ) {
            String name = reader.nextName();
            if ( JsonToken.NULL.equals( reader.peek() ) ) {
                reader.skipValue();
                continue;
            }

            DecoderProperty<T, B> property = decoders.get( name );
            if ( null != identityInfo ) {
                final Object id;
                if ( null == property ) {
                    id = identityInfo.getDeserializer( ctx ).decode( reader, ctx );
                } else {
                    id = property.decode( reader, builder, ctx );
                }
                builder.addCallback( new InstanceBuilderCallback<T>() {
                    @Override
                    public void onInstanceCreated( T instance ) {
                        ctx.addObjectId( identityInfo.newIdKey( id ), instance );
                    }
                } );
            } else if ( null == property ) {
                // TODO add a configuration to tell if we skip or throw an unknown property
                reader.skipValue();
            } else {
                property.decode( reader, builder, ctx );
            }
        }

        return builder.build( ctx );
    }

    public final T decodeSubtype( JsonReader reader, JsonDecodingContext ctx, String typeInfo ) throws IOException {
        SubtypeDeserializer<? extends T> mapper = superclassInfo.getMapper( typeInfo );
        if ( null == mapper ) {
            throw ctx.traceError( "No mapper found for the type " + typeInfo );
        }

        return mapper.deserializeObject( reader, ctx );
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T value, JsonDecodingContext ctx ) {
        if ( null == value ) {
            return;
        }

        if ( null != superclassInfo ) {
            SubtypeDeserializer mapper = superclassInfo.getMapper( value.getClass() );
            if ( null == mapper ) {
                // should never happen, the generator add every subtype to the map
                throw ctx.traceError( "Cannot find mapper for class " + value.getClass() );
            }
            JsonDeserializer<T> jsonDeserializer = mapper.getDeserializer( ctx );
            if ( jsonDeserializer != this ) {
                // we test if it's not this mapper to avoid an infinite loop
                jsonDeserializer.setBackReference( referenceName, reference, value, ctx );
                return;
            }
        }

        BackReferenceProperty backReferenceProperty = backReferenceDecoders.get( referenceName );
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
