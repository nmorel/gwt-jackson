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

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for {@link Void}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class VoidJsonDeserializer extends JsonDeserializer<Void> {

    private static final VoidJsonDeserializer INSTANCE = new VoidJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link VoidJsonDeserializer}
     */
    public static VoidJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private VoidJsonDeserializer() { }

    /** {@inheritDoc} */
    @Override
    public Void doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        // we should never be here, the null value is already handled and it's the only possible value for Void
        reader.skipValue();
        return null;
    }
}
