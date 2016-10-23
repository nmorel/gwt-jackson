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

package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * {@link KeySerializer} implementation that uses {@link Object#toString()} method.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class ToStringKeySerializer extends KeySerializer<Object> {

    private static final ToStringKeySerializer INSTANCE = new ToStringKeySerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link ToStringKeySerializer}
     */
    public static ToStringKeySerializer getInstance() {
        return INSTANCE;
    }

    private ToStringKeySerializer() { }

    /** {@inheritDoc} */
    @Override
    protected String doSerialize( Object value, JsonSerializationContext ctx ) {
        return value.toString();
    }
}
