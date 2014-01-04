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

package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.ByteJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.utils.Base64;

/**
 * Default {@link JsonDeserializer} implementation for array of byte.
 *
 * @author Nicolas Morel
 */
public class PrimitiveByteArrayJsonDeserializer extends AbstractArrayJsonDeserializer<byte[]> {

    private static final PrimitiveByteArrayJsonDeserializer INSTANCE = new PrimitiveByteArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveByteArrayJsonDeserializer}
     */
    public static PrimitiveByteArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveByteArrayJsonDeserializer() { }

    @Override
    public byte[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
            IOException {
        List<Byte> list = deserializeIntoList( reader, ctx, ByteJsonDeserializer.getInstance(), params );

        byte[] result = new byte[list.size()];
        int i = 0;
        for ( Byte value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    @Override
    protected byte[] doDeserializeNonArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
            IOException {
        if ( JsonToken.STRING == reader.peek() ) {
            return Base64.decode( reader.nextString() ).getBytes();
        } else if ( ctx.isAcceptSingleValueAsArray() ) {
            return doDeserializeSingleArray( reader, ctx, params );
        } else {
            throw ctx.traceError( "Cannot deserialize a byte[] out of " + reader.peek() + " token", reader );
        }
    }

    @Override
    protected byte[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx,
                                               JsonDeserializerParameters params ) throws IOException {
        return new byte[]{ByteJsonDeserializer.getInstance().deserialize( reader, ctx, params )};
    }
}
