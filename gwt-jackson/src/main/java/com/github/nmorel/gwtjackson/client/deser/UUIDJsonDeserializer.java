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

package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;
import java.util.UUID;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for {@link java.util.UUID}.
 *
 * @author Nicolas Morel
 */
public class UUIDJsonDeserializer extends JsonDeserializer<UUID> {

    private static final UUIDJsonDeserializer INSTANCE = new UUIDJsonDeserializer();

    /**
     * @return an instance of {@link UUIDJsonDeserializer}
     */
    public static UUIDJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private UUIDJsonDeserializer() { }

    @Override
    public UUID doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws IOException {
        return UUID.fromString( reader.nextString() );
    }
}
