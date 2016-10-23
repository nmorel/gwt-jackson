/*
 * Copyright 2016 Nicolas Morel
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

package com.github.nmorel.gwtjackson.rebind.type;

import com.github.nmorel.gwtjackson.client.Deserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Class used to handle the generation of {@link Deserializer}
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public class JParameterizedDeserializer extends JParameterizedMapper<JDeserializerType> {

    /**
     * <p>Constructor for JParameterizedDeserializer.</p>
     *
     * @param key a {@link com.github.nmorel.gwtjackson.rebind.type.JDeserializerType} object.
     * @param json a {@link com.github.nmorel.gwtjackson.rebind.type.JDeserializerType} object.
     */
    public JParameterizedDeserializer( JDeserializerType key, JDeserializerType json ) {
        super( key, json );
    }

    /** {@inheritDoc} */
    @Override
    protected Class getMainClass() {
        return Deserializer.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Class getKeyClass() {
        return KeyDeserializer.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Class getJsonClass() {
        return JsonDeserializer.class;
    }

}
