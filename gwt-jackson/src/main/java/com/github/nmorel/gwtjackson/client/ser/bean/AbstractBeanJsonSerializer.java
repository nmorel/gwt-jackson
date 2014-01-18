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
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

    private final Map<String, BeanPropertySerializer<T, ?>> serializers;// = new LinkedHashMap<String, BeanPropertySerializer<T, ?>>();

    private final Map<Class<? extends T>, SubtypeSerializer<? extends T>> subtypeClassToSerializer;

    private final IdentitySerializationInfo<T> defaultIdentityInfo;

    private final TypeSerializationInfo<T> defaultTypeInfo;

    protected AbstractBeanJsonSerializer() {
        this.serializers = initSerializers();
        this.defaultIdentityInfo = initIdentityInfo();
        this.defaultTypeInfo = initTypeInfo();
        this.subtypeClassToSerializer = initMapSubtypeClassToSerializer();
    }

    /**
     * Initialize the {@link Map} containing the property serializers. Returns an empty map if there are no properties to
     * serialize.
     */
    protected Map<String, BeanPropertySerializer<T, ?>> initSerializers() {
        return Collections.emptyMap();
    }

    /**
     * Initialize the {@link IdentitySerializationInfo}. Returns null if there is no {@link JsonIdentityInfo} annotation on bean.
     */
    protected IdentitySerializationInfo<T> initIdentityInfo() {
        return null;
    }

    /**
     * Initialize the {@link TypeSerializationInfo}. Returns null if there is no {@link JsonTypeInfo} annotation on bean.
     */
    protected TypeSerializationInfo<T> initTypeInfo() {
        return null;
    }

    /**
     * Initialize the {@link Map} containing the {@link SubtypeSerializer}. Returns an empty map if the bean has no subtypes.
     */
    protected Map<Class<? extends T>, SubtypeSerializer<? extends T>> initMapSubtypeClassToSerializer() {
        return Collections.emptyMap();
    }

    public abstract Class getSerializedType();

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull T value, JsonSerializationContext ctx, JsonSerializerParameters params ) throws
            IOException {

        // Processing the parameters. We fallback to default if parameter is not present.
        final IdentitySerializationInfo identityInfo = null == params.getIdentityInfo() ? defaultIdentityInfo : params.getIdentityInfo();
        final TypeSerializationInfo typeInfo = null == params.getTypeInfo() ? defaultTypeInfo : params.getTypeInfo();
        final Set<String> ignoredProperties = null == params.getIgnoredProperties() ? Collections.<String>emptySet() : params
                .getIgnoredProperties();

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
                idWriter = new ObjectIdSerializer( propertySerializer.getValue( value, ctx ), propertySerializer.getSerializer() );
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
                    getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx, identityInfo, typeInfo, ignoredProperties );
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
                    getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx, identityInfo, typeInfo, ignoredProperties );
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
                    getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx, identityInfo, typeInfo, ignoredProperties );
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
            getSerializer( writer, value, ctx ).serializeObject( writer, value, ctx, identityInfo, typeInfo, ignoredProperties );
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
        return (AbstractBeanJsonSerializer<T>) subtypeSerializer.getSerializer();
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
    public final void serializeObject( JsonWriter writer, T value, JsonSerializationContext ctx, IdentitySerializationInfo identityInfo,
                                       TypeSerializationInfo typeInfo, Set<String> ignoredProperties ) throws IOException {
        for ( Map.Entry<String, BeanPropertySerializer<T, ?>> entry : serializers.entrySet() ) {
            if ( (null == identityInfo || !identityInfo.isProperty() || !identityInfo.getPropertyName().equals( entry
                    .getKey() )) && !ignoredProperties.contains( entry.getKey() ) ) {
                writer.name( entry.getKey() );
                entry.getValue().serialize( writer, value, ctx );
            }
        }
    }
}
