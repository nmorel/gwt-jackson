/*
 * Copyright 2014 Nicolas Morel
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
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Interface hiding the actual implementation doing the bean deserialization.
 *
 * @author Nicolas Morel.
 */
interface InternalDeserializer<T, S extends JsonDeserializer<T>> {

    S getDeserializer();

    T deserializeInline( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                         IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation, Map<String,
            String> bufferedProperties );

    T deserializeWrapped( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                          IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation );

}

