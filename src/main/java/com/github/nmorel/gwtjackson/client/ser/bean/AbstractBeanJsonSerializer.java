package com.github.nmorel.gwtjackson.client.ser.bean;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonSerializer} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonSerializer<T> extends JsonSerializer<T> {

    private final Map<String, EncoderProperty<T, ?>> encoders = new LinkedHashMap<String, EncoderProperty<T, ?>>();

    private IdentitySerializationInfo<T, ?> identityInfo;

    private SuperclassSerializationInfo<T> superclassInfo;

    /**
     * Add an {@link EncoderProperty}
     *
     * @param propertyName name of the property
     * @param encoder encoder
     */
    protected void addProperty( String propertyName, EncoderProperty<T, ?> encoder ) {
        encoders.put( propertyName, encoder );
    }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull T value, JsonEncodingContext ctx ) throws IOException {
        ObjectIdSerializer<?> idWriter = null;
        if ( null != identityInfo ) {
            idWriter = ctx.getObjectId( value );
            if ( null != idWriter ) {
                // the bean has already been encoded, we just encode the id-
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
                // we don't include type info so we just encode the properties
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

    public final void serializeObject( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException {
        for ( Map.Entry<String, EncoderProperty<T, ?>> entry : encoders.entrySet() ) {
            writer.name( entry.getKey() );
            entry.getValue().encode( writer, value, ctx );
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
