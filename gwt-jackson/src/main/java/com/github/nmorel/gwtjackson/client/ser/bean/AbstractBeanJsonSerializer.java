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

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
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
 * @version $Id: $
 */
public abstract class AbstractBeanJsonSerializer<T> extends JsonSerializer<T> implements InternalSerializer<T> {

    protected final BeanPropertySerializer[] serializers;

    private final Map<Class, SubtypeSerializer> subtypeClassToSerializer;

    private final IdentitySerializationInfo<T> defaultIdentityInfo;

    private final TypeSerializationInfo<T> defaultTypeInfo;

    private final AnyGetterPropertySerializer<T> anyGetterPropertySerializer;

    /**
     * <p>Constructor for AbstractBeanJsonSerializer.</p>
     */
    protected AbstractBeanJsonSerializer() {
        this.serializers = initSerializers();
        this.defaultIdentityInfo = initIdentityInfo();
        this.defaultTypeInfo = initTypeInfo();
        this.subtypeClassToSerializer = initMapSubtypeClassToSerializer();
        this.anyGetterPropertySerializer = initAnyGetterPropertySerializer();
    }

    /**
     * Initialize the {@link Map} containing the property serializers. Returns an empty map if there are no properties to
     * serialize.
     *
     * @return an array of {@link com.github.nmorel.gwtjackson.client.ser.bean.BeanPropertySerializer} objects.
     */
    protected BeanPropertySerializer[] initSerializers() {
        return new BeanPropertySerializer[0];
    }

    /**
     * Initialize the {@link IdentitySerializationInfo}. Returns null if there is no {@link JsonIdentityInfo} annotation on bean.
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo} object.
     */
    protected IdentitySerializationInfo<T> initIdentityInfo() {
        return null;
    }

    /**
     * Initialize the {@link TypeSerializationInfo}. Returns null if there is no {@link JsonTypeInfo} annotation on bean.
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.ser.bean.TypeSerializationInfo} object.
     */
    protected TypeSerializationInfo<T> initTypeInfo() {
        return null;
    }

    /**
     * Initialize the {@link Map} containing the {@link SubtypeSerializer}. Returns an empty map if the bean has no subtypes.
     *
     * @return a {@link java.util.Map} object.
     */
    protected Map<Class, SubtypeSerializer> initMapSubtypeClassToSerializer() {
        return Collections.emptyMap();
    }

    /**
     * Initialize the {@link AnyGetterPropertySerializer}. Returns null if there is no method annoted with {@link JsonAnyGetter} on bean.
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.ser.bean.AnyGetterPropertySerializer} object.
     */
    protected AnyGetterPropertySerializer<T> initAnyGetterPropertySerializer() {
        return null;
    }

    /**
     * <p>getSerializedType</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    public abstract Class getSerializedType();

    /** {@inheritDoc} */
    @Override
    public void doSerialize( JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        getSerializer( writer, value, ctx ).serializeInternally( writer, value, ctx, params, defaultIdentityInfo, defaultTypeInfo );
    }

    private InternalSerializer<T> getSerializer( JsonWriter writer, T value, JsonSerializationContext ctx ) {
        if ( value.getClass() == getSerializedType() ) {
            return this;
        }
        SubtypeSerializer subtypeSerializer = subtypeClassToSerializer.get( value.getClass() );
        if ( null == subtypeSerializer ) {
            if ( ctx.getLogger().isLoggable( Level.FINE ) ) {
                ctx.getLogger().fine( "Cannot find serializer for class " + value
                        .getClass() + ". Fallback to the serializer of " + getSerializedType() );
            }
            return this;
        }
        return subtypeSerializer;
    }

    /** {@inheritDoc} */
    public void serializeInternally( JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params,
                                     IdentitySerializationInfo<T> defaultIdentityInfo, TypeSerializationInfo<T> defaultTypeInfo ) {

        // Processing the parameters. We fallback to default if parameter is not present.
        final IdentitySerializationInfo identityInfo = null == params.getIdentityInfo() ? defaultIdentityInfo : params.getIdentityInfo();
        final TypeSerializationInfo typeInfo = null == params.getTypeInfo() ? defaultTypeInfo : params.getTypeInfo();
        final Set<String> ignoredProperties = null == params.getIgnoredProperties() ? Collections.<String>emptySet() : params
                .getIgnoredProperties();

        if ( params.isUnwrapped() ) {
            // if unwrapped, we serialize the properties inside the current object
            serializeProperties( writer, value, ctx, ignoredProperties, identityInfo );
            return;
        }

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

        if ( null != typeInfo ) {
            String typeInformation = typeInfo.getTypeInfo( value.getClass() );
            if ( null == typeInformation ) {
                ctx.getLogger().log( Level.WARNING, "Cannot find type info for class " + value.getClass() );
            } else {
                switch ( typeInfo.getInclude() ) {
                    case PROPERTY:
                        // type info is included as a property of the object
                        serializeObject( writer, value, ctx, ignoredProperties, identityInfo, idWriter, typeInfo
                                .getPropertyName(), typeInformation );
                        return;

                    case WRAPPER_OBJECT:
                        // type info is included in a wrapper object that contains only one property. The name of this property is the type
                        // info and the value the object
                        writer.beginObject();
                        writer.name( typeInformation );
                        serializeObject( writer, value, ctx, ignoredProperties, identityInfo, idWriter );
                        writer.endObject();
                        return;

                    case WRAPPER_ARRAY:
                        // type info is included in a wrapper array that contains two elements. First one is the type
                        // info and the second one the object
                        writer.beginArray();
                        writer.value( typeInformation );
                        serializeObject( writer, value, ctx, ignoredProperties, identityInfo, idWriter );
                        writer.endArray();
                        return;

                    default:
                        ctx.getLogger().log( Level.SEVERE, "JsonTypeInfo.As." + typeInfo.getInclude() + " is not supported" );
                }
            }
        }

        serializeObject( writer, value, ctx, ignoredProperties, identityInfo, idWriter );
    }

    /**
     * Serializes all the properties of the bean in a json object.
     *
     * @param writer writer
     * @param value bean to serialize
     * @param ctx context of the serialization process
     * @param ignoredProperties ignored properties
     * @param identityInfo identity info
     * @param idWriter identifier writer
     */
    private void serializeObject( JsonWriter writer, T value, JsonSerializationContext ctx, Set<String> ignoredProperties,
                                  IdentitySerializationInfo identityInfo, ObjectIdSerializer<?> idWriter ) {
        serializeObject( writer, value, ctx, ignoredProperties, identityInfo, idWriter, null, null );
    }

    /**
     * Serializes all the properties of the bean in a json object.
     *
     * @param writer writer
     * @param value bean to serialize
     * @param ctx context of the serialization process
     * @param ignoredProperties ignored properties
     * @param identityInfo identity info
     * @param idWriter identifier writer
     * @param typeName in case of type info as property, the name of the property
     * @param typeInformation in case of type info as property, the type information
     */
    protected void serializeObject( JsonWriter writer, T value, JsonSerializationContext ctx, Set<String> ignoredProperties,
                                    IdentitySerializationInfo identityInfo, ObjectIdSerializer<?> idWriter, String typeName, String
            typeInformation ) {
        writer.beginObject();

        if ( null != typeName && null != typeInformation ) {
            writer.name( typeName );
            writer.value( typeInformation );
        }

        if ( null != idWriter ) {
            writer.name( identityInfo.getPropertyName() );
            idWriter.serializeId( writer, ctx );
        }

        serializeProperties( writer, value, ctx, ignoredProperties, identityInfo );

        writer.endObject();
    }

    private void serializeProperties( JsonWriter writer, T value, JsonSerializationContext ctx, Set<String> ignoredProperties,
                                      IdentitySerializationInfo identityInfo ) {
        for ( BeanPropertySerializer<T, ?> propertySerializer : serializers ) {
            if ( (null == identityInfo || !identityInfo.isProperty() || !identityInfo.getPropertyName().equals( propertySerializer
                    .getPropertyName() )) && !ignoredProperties.contains( propertySerializer.getPropertyName() ) ) {
                propertySerializer.serializePropertyName( writer, value, ctx );
                propertySerializer.serialize( writer, value, ctx );
            }
        }

        if ( null != anyGetterPropertySerializer ) {
            anyGetterPropertySerializer.serialize( writer, value, ctx );
        }
    }
}
