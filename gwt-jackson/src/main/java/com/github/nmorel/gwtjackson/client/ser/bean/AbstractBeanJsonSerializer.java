/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.ser.bean;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonSerializer} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonSerializer<T> extends JsonSerializer<T> {

    private final Map<String, BeanPropertySerializer<T, ?>> serializers = new LinkedHashMap<String, BeanPropertySerializer<T, ?>>();

    private IdentitySerializationInfo<T> identityInfo;

    private TypeSerializationInfo<T> typeInfo;

    private final Map<Class<? extends T>, SubtypeSerializer<? extends T>> subtypeClassToSerializer = new IdentityHashMap<Class<? extends
        T>, SubtypeSerializer<? extends T>>();

    public abstract Class getSerializedType();

    /**
     * Adds an {@link BeanPropertySerializer}.
     *
     * @param propertyName name of the property
     * @param serializer serializer
     */
    protected void addPropertySerializer( String propertyName, BeanPropertySerializer<T, ?> serializer ) {
        serializers.put( propertyName, serializer );
    }

    /**
     * Adds a {@link SubtypeSerializer}.
     *
     * @param clazz {@link Class} associated to the serializer
     * @param subtypeSerializer Serializer
     */
    protected <S extends T> void addSubtypeSerializer( Class<S> clazz, SubtypeSerializer<S> subtypeSerializer ) {
        subtypeClassToSerializer.put( clazz, subtypeSerializer );
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull T value, JsonSerializationContext ctx, JsonSerializerParameters params ) throws
        IOException {
        ObjectIdSerializer<?> idWriter = null;
        if ( null != identityInfo ) {
            idWriter = ctx.getObjectId( value );
            if ( null != idWriter ) {
                // the bean has already been serialized, we just serialize the id
                idWriter.serializeId( writer, ctx );
                return;
            }

            if ( identityInfo.isProperty() ) {
                BeanPropertySerializer<T, ?> propertySerializer = serializers.get( identityInfo.getPropertyName() );
                idWriter = new ObjectIdSerializer( propertySerializer.getValue( value, ctx ), propertySerializer.getSerializer( ctx ) );
            } else {
                idWriter = identityInfo.getObjectId( value, ctx );
            }
            if ( identityInfo.isAlwaysAsId() ) {
                idWriter.serializeId( writer, ctx );
                return;
            }
            ctx.addObjectId( value, idWriter );
        }

        if ( null != typeInfo ) {
            String typeInformation = typeInfo.getTypeInfo( value.getClass() );
            if ( null == typeInformation ) {
                throw ctx.traceError( value, "Cannot find type info for class " + value.getClass(), writer );
            }

            switch ( typeInfo.getInclude() ) {
                case PROPERTY:
                    // type info is included as a property of the object
                    writer.beginObject();
                    writer.name( typeInfo.getPropertyName() );
                    writer.value( typeInformation );
                    if ( null != idWriter ) {
                        writer.name( identityInfo.getPropertyName() );
                        idWriter.serializeId( writer, ctx );
                    }
                    getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx );
                    writer.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    writer.beginObject();
                    writer.name( typeInformation );
                    writer.beginObject();
                    if ( null != idWriter ) {
                        writer.name( identityInfo.getPropertyName() );
                        idWriter.serializeId( writer, ctx );
                    }
                    getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx );
                    writer.endObject();
                    writer.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    writer.beginArray();
                    writer.value( typeInformation );
                    writer.beginObject();
                    if ( null != idWriter ) {
                        writer.name( identityInfo.getPropertyName() );
                        idWriter.serializeId( writer, ctx );
                    }
                    getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx );
                    writer.endObject();
                    writer.endArray();
                    break;

                default:
                    throw ctx.traceError( value, "JsonTypeInfo.As." + typeInfo.getInclude() + " is not supported", writer );
            }
        } else {
            writer.beginObject();
            if ( null != idWriter ) {
                writer.name( identityInfo.getPropertyName() );
                idWriter.serializeId( writer, ctx );
            }
            getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx );
            writer.endObject();
        }
    }

    private AbstractBeanJsonSerializer<T> getSerializer( JsonWriter writer, T value, JsonSerializationContext ctx ) {
        if ( value.getClass() == getSerializedType() ) {
            return this;
        }
        SubtypeSerializer subtypeSerializer = subtypeClassToSerializer.get( value.getClass() );
        if ( null == subtypeSerializer ) {
            throw ctx.traceError( value, "Cannot find serializer for class " + value.getClass(), writer );
        }
        return (AbstractBeanJsonSerializer<T>) subtypeSerializer.getSerializer( ctx );
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
            if ( null == identityInfo || !identityInfo.isProperty() || !identityInfo.getPropertyName().equals( entry.getKey() ) ) {
                writer.name( entry.getKey() );
                entry.getValue().serialize( writer, value, ctx );
            }
        }
    }

    protected final void setIdentityInfo( IdentitySerializationInfo<T> identityInfo ) {
        this.identityInfo = identityInfo;
    }

    protected final void setTypeInfo( TypeSerializationInfo<T> typeInfo ) {
        this.typeInfo = typeInfo;
    }
}
