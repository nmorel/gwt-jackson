package com.github.nmorel.gwtjackson.client.deser.bean;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
public abstract class AbstractBeanJsonDeserializer<T> extends JsonDeserializer<T> {

    private final Map<String, BeanPropertyDeserializer<T, ?>> deserializers = new LinkedHashMap<String, BeanPropertyDeserializer<T, ?>>();

    private final Map<String, BackReferenceProperty<T, ?>> backReferenceDeserializers = new LinkedHashMap<String,
        BackReferenceProperty<T, ?>>();

    private final Set<String> ignoredProperties = new HashSet<String>();

    private final Set<String> requiredProperties = new HashSet<String>();

    private InstanceBuilder<T> instanceBuilder;

    private IdentityDeserializationInfo<T, ?> identityInfo;

    private SuperclassDeserializationInfo<T> superclassInfo;

    /**
     * Add a {@link BeanPropertyDeserializer}
     *
     * @param propertyName name of the property
     * @param deserializer deserializer
     */
    protected final void addProperty( String propertyName, boolean required, BeanPropertyDeserializer<T, ?> deserializer ) {
        deserializers.put( propertyName, deserializer );
        if ( required ) {
            requiredProperties.add( propertyName );
        }
    }

    /**
     * Add a {@link BackReferenceProperty}
     *
     * @param referenceName name of the reference
     * @param backReference backReference
     */
    protected final void addProperty( String referenceName, BackReferenceProperty<T, ?> backReference ) {
        backReferenceDeserializers.put( referenceName, backReference );
    }

    /**
     * Add an ignored property
     *
     * @param propertyName name of the property
     */
    protected final void addIgnoredProperty( String propertyName ) {
        ignoredProperties.add( propertyName );
    }

    @Override
    public T doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( null != identityInfo && !JsonToken.BEGIN_OBJECT.equals( reader.peek() ) ) {
            IdKey id = identityInfo.readIdKey( reader, ctx );
            Object instance = ctx.getObjectWithId( id );
            if ( null == instance ) {
                throw ctx.traceError( "Cannot find an object with id " + id, reader );
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
                        throw ctx.traceError( "Cannot find the type info", reader );
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
                    throw ctx.traceError( "JsonTypeInfo.As." + superclassInfo.getInclude() + " is not supported", reader );
            }
        } else if ( null != instanceBuilder ) {
            reader.beginObject();
            result = deserializeObject( reader, ctx );
            reader.endObject();
        } else {
            throw ctx.traceError( "Cannot instantiate the type", reader );
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

        // we will remove the properties read from this list and check at the end it's empty
        Set<String> requiredPropertiesLeft = requiredProperties.isEmpty() ? Collections
            .<String>emptySet() : new HashSet<String>( requiredProperties );

        // we first instantiate the bean. It might buffer properties if there are properties required for constructor and they are not in
        // first position
        Instance<T> instance = instanceBuilder.newInstance( reader, ctx );

        // we then look for identity. It can also buffer properties it is not in current reader position.
        readIdentityProperty( instance, reader, ctx );

        // we flush any buffered properties
        flushBufferedProperties( instance, requiredPropertiesLeft, ctx );

        T bean = instance.getInstance();

        while ( JsonToken.NAME.equals( reader.peek() ) ) {
            String propertyName = reader.nextName();

            requiredPropertiesLeft.remove( propertyName );

            if ( ignoredProperties.contains( propertyName ) ) {
                reader.skipValue();
                continue;
            }

            BeanPropertyDeserializer<T, ?> property = getPropertyDeserializer( propertyName, ctx );
            if ( null == property ) {
                reader.skipValue();
            } else {
                property.deserialize( reader, bean, ctx );
            }
        }

        if ( !requiredPropertiesLeft.isEmpty() ) {
            throw ctx.traceError( "Required properties are missing : " + requiredPropertiesLeft, reader );
        }
        return bean;
    }

    private void readIdentityProperty( Instance<T> instance, JsonReader reader, final JsonDeserializationContext ctx ) throws IOException {
        if ( null == identityInfo ) {
            return;
        }

        JsonReader identityReader = null;

        // we look if it has not been already buffered
        String propertyValue = instance.getBufferedProperties().remove( identityInfo.getPropertyName() );
        if ( null != propertyValue ) {
            identityReader = ctx.newJsonReader( propertyValue );
        } else {
            // we search for the identity property
            while ( JsonToken.NAME.equals( reader.peek() ) ) {
                String name = reader.nextName();

                if ( ignoredProperties.contains( name ) ) {
                    reader.skipValue();
                    continue;
                }

                if ( identityInfo.getPropertyName().equals( name ) ) {
                    identityReader = reader;
                    break;
                } else {
                    instance.getBufferedProperties().put( name, reader.nextValue() );
                }
            }
        }

        if ( null != identityReader ) {
            identityInfo.readAndAddIdToContext( identityReader, instance.getInstance(), ctx );
        }
    }

    private void flushBufferedProperties( Instance<T> instance, Set<String> requiredPropertiesLeft, JsonDeserializationContext ctx ) {
        if ( !instance.getBufferedProperties().isEmpty() ) {
            for ( Entry<String, String> bufferedProperty : instance.getBufferedProperties().entrySet() ) {
                String propertyName = bufferedProperty.getKey();

                requiredPropertiesLeft.remove( propertyName );

                if ( ignoredProperties.contains( propertyName ) ) {
                    continue;
                }

                BeanPropertyDeserializer<T, ?> property = getPropertyDeserializer( propertyName, ctx );
                if ( null != property ) {
                    property.deserialize( ctx.newJsonReader( bufferedProperty.getValue() ), instance.getInstance(), ctx );
                }
            }
            instance.getBufferedProperties().clear();
        }
    }

    private BeanPropertyDeserializer<T, ?> getPropertyDeserializer( String propertyName, JsonDeserializationContext ctx ) {
        BeanPropertyDeserializer<T, ?> property = deserializers.get( propertyName );
        if ( null == property ) {
            if ( ctx.isFailOnUnknownProperties() ) {
                throw ctx.traceError( "Unknown property '" + propertyName + "'" );
            }
        }
        return property;
    }

    public final T deserializeSubtype( JsonReader reader, JsonDeserializationContext ctx, String typeInfo ) throws IOException {
        SubtypeDeserializer<? extends T> deserializer = superclassInfo.getDeserializer( typeInfo );
        if ( null == deserializer ) {
            throw ctx.traceError( "No deserializer found for the type " + typeInfo, reader );
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
            if ( jsonDeserializer.getClass() != getClass() ) {
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

    protected final InstanceBuilder<T> getInstanceBuilder() {
        return instanceBuilder;
    }

    protected final void setInstanceBuilder( InstanceBuilder<T> instanceBuilder ) {
        this.instanceBuilder = instanceBuilder;
    }

    protected final IdentityDeserializationInfo<T, ?> getIdentityInfo() {
        return identityInfo;
    }

    protected final void setIdentityInfo( IdentityDeserializationInfo<T, ?> identityInfo ) {
        this.identityInfo = identityInfo;
    }

    protected final SuperclassDeserializationInfo<T> getSuperclassInfo() {
        return superclassInfo;
    }

    protected final void setSuperclassInfo( SuperclassDeserializationInfo<T> superclassInfo ) {
        this.superclassInfo = superclassInfo;
    }
}
