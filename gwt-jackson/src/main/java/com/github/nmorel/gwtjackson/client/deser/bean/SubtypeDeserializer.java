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

package com.github.nmorel.gwtjackson.client.deser.bean;

import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
import com.github.nmorel.gwtjackson.client.ser.bean.BeanPropertySerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.TypeSerializationInfo;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Delegate the deserialization of a subtype to a corresponding {@link AbstractBeanJsonDeserializer}
 *
 * @author Nicolas Morel
 */
public abstract class SubtypeDeserializer<T, D extends JsonDeserializer<T>> extends HasDeserializer<T,
        D> implements InternalDeserializer<T, D> {

    /**
     * Delegate the deserialization of a subtype to a corresponding {@link AbstractBeanJsonDeserializer}
     *
     * @author Nicolas Morel
     */
    public abstract static class BeanSubtypeDeserializer<T> extends SubtypeDeserializer<T, AbstractBeanJsonDeserializer<T>> {

        @Override
        public T deserializeInline( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                    IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation,
                                    Map<String, String> bufferedProperties ) {
            return getDeserializer().deserializeInline( reader, ctx, params, identityInfo, typeInfo, typeInformation, bufferedProperties );
        }

        @Override
        public T deserializeWrapped( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                     IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation ) {
            return getDeserializer().deserializeWrapped( reader, ctx, params, identityInfo, typeInfo, typeInformation );
        }
    }

    /**
     * Delegate the deserialization of an enum subtype to a corresponding {@link EnumJsonDeserializer}
     *
     * @author Nicolas Morel
     */
    public abstract static class EnumSubtypeDeserializer<E extends Enum<E>> extends SubtypeDeserializer<E, EnumJsonDeserializer<E>> {

        @Override
        public E deserializeInline( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                    IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation,
                                    Map<String, String> bufferedProperties ) {
            throw ctx.traceError( "Cannot deserialize into a bean with an enum" );
        }

        @Override
        public E deserializeWrapped( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                     IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation ) {
            return getDeserializer().deserialize( reader, ctx, params );
        }
    }

    /**
     * Delegate the deserialization of a subtype to a corresponding {@link JsonDeserializer}
     *
     * @author Nicolas Morel.
     */
    public abstract static class DefaultSubtypeDeserializer<T> extends SubtypeDeserializer<T, JsonDeserializer<T>> {

        @Override
        public T deserializeInline( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                    IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation,
                                    Map<String, String> bufferedProperties ) {
            throw ctx.traceError( "Cannot deserialize into a bean when not using an AbstractBeanJsonDeserializer" );
        }

        @Override
        public T deserializeWrapped( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                     IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation ) {
            return getDeserializer().deserialize( reader, ctx, params );
        }
    }
}
