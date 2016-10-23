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

package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;

/**
 * Default {@link KeyDeserializer} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class CharacterKeyDeserializer extends KeyDeserializer<Character> {

    private static final CharacterKeyDeserializer INSTANCE = new CharacterKeyDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link CharacterKeyDeserializer}
     */
    public static CharacterKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private CharacterKeyDeserializer() { }

    /** {@inheritDoc} */
    @Override
    protected Character doDeserialize( String key, JsonDeserializationContext ctx ) {
        return key.charAt( 0 );
    }
}
