package com.github.nmorel.gwtjackson.client.ser.bean;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonSerializer} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonSerializer<T> extends JsonSerializer<T> {

    private final Map<String, BeanPropertySerializer<T, ?>> serializers = new LinkedHashMap<String, BeanPropertySerializer<T, ?>>();

    private IdentitySerializationInfo<T, ?> identityInfo;

    private SuperclassSerializationInfo<T> superclassInfo;

    /**
     * Adds an {@link BeanPropertySerializer}.
     *
     * @param propertyName name of the property
     * @param serializer serializer
     */
    protected void addPropertySerializer( String propertyName, BeanPropertySerializer<T, ?> serializer ) {
        serializers.put( propertyName, serializer );
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull T value, JsonSerializationContext ctx ) throws IOException {
        ObjectIdSerializer<?> idWriter = null;
        if ( null != identityInfo ) {
            idWriter = ctx.getObjectId( value );
            if ( null != idWriter ) {
                // the bean has already been serialized, we just serialize the id
                idWriter.serializeId( writer, ctx );
                return;
            }

            idWriter = identityInfo.getObjectId( value, ctx );
            if ( identityInfo.isAlwaysAsId() ) {
                idWriter.serializeId( writer, ctx );
                return;
            }
            ctx.addObjectId( value, idWriter );
        }

        if ( null != superclassInfo ) {
            SubtypeSerializer serializer = superclassInfo.getSerializer( value.getClass() );
            if ( null == serializer ) {
                throw ctx.traceError( value, "Cannot find serializer for class " + value.getClass() );
            }

            if ( !superclassInfo.isIncludeTypeInfo() ) {
                // we don't include type info so we just serialize the properties
                writer.beginObject();
                if ( null != idWriter ) {
                    writer.name( identityInfo.getPropertyName() );
                    idWriter.serializeId( writer, ctx );
                }
                serializer.serializeObject( writer, value, ctx );
                writer.endObject();
            } else {
                String typeInfo = superclassInfo.getTypeInfo( value.getClass() );
                if ( null == typeInfo ) {
                    throw ctx.traceError( value, "Cannot find type info for class " + value.getClass() );
                }

                switch ( superclassInfo.getInclude() ) {
                    case PROPERTY:
                        // type info is included as a property of the object
                        writer.beginObject();
                        writer.name( superclassInfo.getPropertyName() );
                        writer.value( typeInfo );
                        if ( null != idWriter ) {
                            writer.name( identityInfo.getPropertyName() );
                            idWriter.serializeId( writer, ctx );
                        }
                        serializer.serializeObject( writer, value, ctx );
                        writer.endObject();
                        break;

                    case WRAPPER_OBJECT:
                        // type info is included in a wrapper object that contains only one property. The name of this property is the type
                        // info and the value the object
                        writer.beginObject();
                        writer.name( typeInfo );
                        writer.beginObject();
                        if ( null != idWriter ) {
                            writer.name( identityInfo.getPropertyName() );
                            idWriter.serializeId( writer, ctx );
                        }
                        serializer.serializeObject( writer, value, ctx );
                        writer.endObject();
                        writer.endObject();
                        break;

                    case WRAPPER_ARRAY:
                        // type info is included in a wrapper array that contains two elements. First one is the type
                        // info and the second one the object
                        writer.beginArray();
                        writer.value( typeInfo );
                        writer.beginObject();
                        if ( null != idWriter ) {
                            writer.name( identityInfo.getPropertyName() );
                            idWriter.serializeId( writer, ctx );
                        }
                        serializer.serializeObject( writer, value, ctx );
                        writer.endObject();
                        writer.endArray();
                        break;

                    default:
                        throw ctx.traceError( value, "JsonTypeInfo.As." + superclassInfo.getInclude() + " is not supported" );
                }
            }
        } else {
            writer.beginObject();
            if ( null != idWriter ) {
                writer.name( identityInfo.getPropertyName() );
                idWriter.serializeId( writer, ctx );
            }
            serializeObject( writer, value, ctx );
            writer.endObject();
        }
    }

    /**
     * Serializes all the properties of the bean. The {@link JsonWriter} must be in a json object.
     *
     * @param writer writer
     * @param value bean to serialize
     * @param ctx context of the serialization process
     *
     * @throws IOException if an error occurs while writing a property
     */
    public final void serializeObject( JsonWriter writer, T value, JsonSerializationContext ctx ) throws IOException {
        for ( Map.Entry<String, BeanPropertySerializer<T, ?>> entry : serializers.entrySet() ) {
            writer.name( entry.getKey() );
            entry.getValue().serialize( writer, value, ctx );
        }
    }

    protected final IdentitySerializationInfo<T, ?> getIdentityInfo() {
        return identityInfo;
    }

    protected final void setIdentityInfo( IdentitySerializationInfo<T, ?> identityInfo ) {
        this.identityInfo = identityInfo;
    }

    protected final SuperclassSerializationInfo<T> getSuperclassInfo() {
        return superclassInfo;
    }

    protected final void setSuperclassInfo( SuperclassSerializationInfo<T> superclassInfo ) {
        this.superclassInfo = superclassInfo;
    }
}
