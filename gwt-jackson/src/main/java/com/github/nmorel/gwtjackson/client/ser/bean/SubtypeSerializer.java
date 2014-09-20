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

import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Delegate the serialization of a subtype to a corresponding {@link JsonSerializer}
 *
 * @author Nicolas Morel
 */
public abstract class SubtypeSerializer<T, S extends JsonSerializer<T>> extends HasSerializer<T, S> implements InternalSerializer<T> {

    /**
     * Delegate the serialization of a subtype to a corresponding {@link AbstractBeanJsonSerializer}
     *
     * @author Nicolas Morel
     */
    public abstract static class BeanSubtypeSerializer<T> extends SubtypeSerializer<T, AbstractBeanJsonSerializer<T>> {

        @Override
        public void serializeInternally( JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params,
                                         IdentitySerializationInfo<T> defaultIdentityInfo, TypeSerializationInfo<T> defaultTypeInfo,
                                         Map<String, BeanPropertySerializer<T, ?>> serializers ) {
            getSerializer().serializeInternally( writer, value, ctx, params, defaultIdentityInfo, defaultTypeInfo, serializers );
        }
    }

    /**
     * Delegate the serialization of an enum subtype to a corresponding {@link EnumJsonSerializer}
     *
     * @author Nicolas Morel.
     */
    public abstract static class EnumSubtypeSerializer<E extends Enum<E>> extends SubtypeSerializer<E, EnumJsonSerializer<E>> {

        @Override
        public void serializeInternally( JsonWriter writer, E value, JsonSerializationContext ctx, JsonSerializerParameters params,
                                         IdentitySerializationInfo<E> defaultIdentityInfo, TypeSerializationInfo<E> defaultTypeInfo,
                                         Map<String, BeanPropertySerializer<E, ?>> serializers ) {

            final TypeSerializationInfo typeInfo = null == params.getTypeInfo() ? defaultTypeInfo : params.getTypeInfo();

            if ( null != typeInfo ) {
                String typeInformation = typeInfo.getTypeInfo( value.getClass() );
                if ( null == typeInformation ) {
                    throw ctx.traceError( value, "Cannot find type info for class " + value.getClass(), writer );
                }

                switch ( typeInfo.getInclude() ) {
                    case WRAPPER_OBJECT:
                        // type info is included in a wrapper object that contains only one property. The name of this property is the type
                        // info and the value the object
                        writer.beginObject();
                        writer.name( typeInformation );
                        getSerializer().serialize( writer, value, ctx, params );
                        writer.endObject();
                        break;

                    default:
                        // included as wrapper array even if property is set
                        writer.beginArray();
                        writer.value( typeInformation );
                        getSerializer().serialize( writer, value, ctx, params );
                        writer.endArray();
                }
            } else {
                getSerializer().serialize( writer, value, ctx, params );
            }
        }
    }

    /**
     * Delegate the serialization of a subtype to a corresponding {@link JsonSerializer}
     *
     * @author Nicolas Morel.
     */
    public abstract static class DefaultSubtypeSerializer<T> extends SubtypeSerializer<T, JsonSerializer<T>> {

        @Override
        public void serializeInternally( JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params,
                                         IdentitySerializationInfo<T> defaultIdentityInfo, TypeSerializationInfo<T> defaultTypeInfo,
                                         Map<String, BeanPropertySerializer<T, ?>> serializers ) {
            getSerializer().serialize( writer, value, ctx, params );
        }
    }
}
