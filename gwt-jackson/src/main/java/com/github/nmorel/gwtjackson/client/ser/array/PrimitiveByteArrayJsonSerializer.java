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

package com.github.nmorel.gwtjackson.client.ser.array;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.utils.Base64Utils;

/**
 * Default {@link JsonSerializer} implementation for array of byte.
 *
 * @author Nicolas Morel
 */
public class PrimitiveByteArrayJsonSerializer extends JsonSerializer<byte[]> {

    private static final PrimitiveByteArrayJsonSerializer INSTANCE = new PrimitiveByteArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveByteArrayJsonSerializer}
     */
    public static PrimitiveByteArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveByteArrayJsonSerializer() { }

    @Override
    protected boolean isEmpty( byte[] value ) {
        return null == value || value.length == 0;
    }

    @Override
    public void doSerialize( JsonWriter writer, byte[] values, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        writer.unescapeValue( Base64Utils.toBase64( values ) );
    }
}
