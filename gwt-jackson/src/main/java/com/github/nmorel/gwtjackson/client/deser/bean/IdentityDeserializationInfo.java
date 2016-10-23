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

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Contains identity informations for deserialization process.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public interface IdentityDeserializationInfo<T> {

    /**
     * <p>getPropertyName</p>
     *
     * @return name of the identifier property
     */
    String getPropertyName();

    /**
     * <p>isProperty</p>
     *
     * @return true if the identifier is also a property of the bean
     */
    boolean isProperty();

    /**
     * <p>newIdKey</p>
     *
     * @param id Identifier
     * @return a new {@link IdKey}
     */
    IdKey newIdKey( Object id );

    /**
     * Reads the id and returns it.
     *
     * @param reader reader
     * @param ctx context of the deserialization process
     * @return the identifier
     */
    Object readId( JsonReader reader, JsonDeserializationContext ctx );
}
