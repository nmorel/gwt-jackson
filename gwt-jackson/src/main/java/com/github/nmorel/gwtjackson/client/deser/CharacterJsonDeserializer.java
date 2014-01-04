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

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonDeserializer} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 */
public class CharacterJsonDeserializer extends JsonDeserializer<Character> {

    private static final CharacterJsonDeserializer INSTANCE = new CharacterJsonDeserializer();

    /**
     * @return an instance of {@link CharacterJsonDeserializer}
     */
    public static CharacterJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private CharacterJsonDeserializer() { }

    @Override
    public Character doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
            IOException {
        if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
            return (char) reader.nextInt();
        } else {
            String value = reader.nextString();
            if ( value.isEmpty() ) {
                return null;
            }
            return value.charAt( 0 );
        }
    }
}
