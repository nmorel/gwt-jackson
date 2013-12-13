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
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.FloatJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for array of float.
 *
 * @author Nicolas Morel
 */
public class PrimitiveFloatArrayJsonDeserializer extends AbstractArrayJsonDeserializer<float[]> {

    private static final PrimitiveFloatArrayJsonDeserializer INSTANCE = new PrimitiveFloatArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveFloatArrayJsonDeserializer}
     */
    public static PrimitiveFloatArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveFloatArrayJsonDeserializer() { }

    @Override
    public float[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
        IOException {
        List<Float> list = deserializeIntoList( reader, ctx, FloatJsonDeserializer.getInstance(), params );

        float[] result = new float[list.size()];
        int i = 0;
        for ( Float value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    @Override
    protected float[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx,
                                                JsonDeserializerParameters params ) throws IOException {
        return new float[]{FloatJsonDeserializer.getInstance().deserialize( reader, ctx, params )};
    }
}
