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

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Contains the id of a bean and a serializer to facilitate the serialization of the next instances of the object.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class ObjectIdSerializer<I> {

    private final I id;

    private final JsonSerializer<I> serializer;

    /**
     * <p>Constructor for ObjectIdSerializer.</p>
     *
     * @param id a I object.
     * @param serializer a {@link com.github.nmorel.gwtjackson.client.JsonSerializer} object.
     */
    public ObjectIdSerializer( I id, JsonSerializer<I> serializer ) {
        this.id = id;
        this.serializer = serializer;
    }

    /**
     * <p>serializeId</p>
     *
     * @param writer a {@link com.github.nmorel.gwtjackson.client.stream.JsonWriter} object.
     * @param ctx a {@link com.github.nmorel.gwtjackson.client.JsonSerializationContext} object.
     */
    public void serializeId( JsonWriter writer, JsonSerializationContext ctx ) {
        serializer.serialize( writer, id, ctx );
    }
}
