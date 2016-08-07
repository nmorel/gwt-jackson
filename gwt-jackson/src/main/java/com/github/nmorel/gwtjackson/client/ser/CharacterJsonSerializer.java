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

package com.github.nmorel.gwtjackson.client.ser;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 */
public class CharacterJsonSerializer extends JsonSerializer<Character> {

    private static final CharacterJsonSerializer INSTANCE = new CharacterJsonSerializer();

    /**
     * @return an instance of {@link CharacterJsonSerializer}
     */
    public static CharacterJsonSerializer getInstance() {
        return INSTANCE;
    }

    private static char defaultValue;

    private CharacterJsonSerializer() { }

    @Override
    protected boolean isDefault( Character value ) {
        return null == value || value == defaultValue;
    }

    @Override
    public void doSerialize( JsonWriter writer, Character value, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.value( value.toString() );
    }
}
