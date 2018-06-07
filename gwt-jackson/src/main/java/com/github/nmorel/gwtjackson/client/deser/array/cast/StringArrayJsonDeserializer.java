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

package com.github.nmorel.gwtjackson.client.deser.array.cast;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.arrays.FastArrayString;
import com.github.nmorel.gwtjackson.client.deser.array.AbstractArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonDeserializer} implementation for array of {@link String}.
 * <p>Not working in production mode, cast problem. Can maybe work with disableCastChecking</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class StringArrayJsonDeserializer extends AbstractArrayJsonDeserializer<String[]> {

    private static final StringArrayJsonDeserializer INSTANCE = new StringArrayJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link StringArrayJsonDeserializer}
     */
    public static StringArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private StringArrayJsonDeserializer() { }

    /** {@inheritDoc} */
    @Override
    public String[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        FastArrayString jsArray = new FastArrayString();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            if ( JsonToken.NULL == reader.peek() ) {
                reader.skipValue();
                jsArray.push( null );
            } else {
                jsArray.push( reader.nextString() );
            }
        }
        reader.endArray();

        return jsArray.reinterpretCast();
    }

    /** {@inheritDoc} */
    @Override
    protected String[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        return new String[]{reader.nextString()};
    }
}
