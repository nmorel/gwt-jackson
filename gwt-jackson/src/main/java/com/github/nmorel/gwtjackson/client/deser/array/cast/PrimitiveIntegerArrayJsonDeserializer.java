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

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.IntegerJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.AbstractArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayInteger;

/**
 * Default {@link JsonDeserializer} implementation for array of int.
 *
 * @author Nicolas Morel
 */
public class PrimitiveIntegerArrayJsonDeserializer extends AbstractArrayJsonDeserializer<int[]> {

    private static final PrimitiveIntegerArrayJsonDeserializer INSTANCE = new PrimitiveIntegerArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveIntegerArrayJsonDeserializer}
     */
    public static PrimitiveIntegerArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private static native int[] reinterpretCast( JsArrayInteger value ) /*-{
        return value;
    }-*/;

    private static int DEFAULT;

    private PrimitiveIntegerArrayJsonDeserializer() { }

    @Override
    public int[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
        IOException {
        JsArrayInteger jsArray = JsArrayInteger.createArray().cast();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            if ( JsonToken.NULL == reader.peek() ) {
                reader.skipValue();
                jsArray.push( DEFAULT );
            } else {
                jsArray.push( reader.nextInt() );
            }
        }
        reader.endArray();

        if ( GWT.isScript() ) {
            return reinterpretCast( jsArray );
        } else {
            int length = jsArray.length();
            int[] ret = new int[length];
            for ( int i = 0; i < length; i++ ) {
                ret[i] = jsArray.get( i );
            }
            return ret;
        }
    }

    @Override
    protected int[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx,
                                              JsonDeserializerParameters params ) throws IOException {
        return new int[]{IntegerJsonDeserializer.getInstance().deserialize( reader, ctx, params )};
    }
}
