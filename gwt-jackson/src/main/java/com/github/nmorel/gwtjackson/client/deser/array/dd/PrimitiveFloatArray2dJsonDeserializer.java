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

package com.github.nmorel.gwtjackson.client.deser.array.dd;

import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.FloatJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for 2D array of float.
 *
 * @author Nicolas Morel
 */
public class PrimitiveFloatArray2dJsonDeserializer extends AbstractArray2dJsonDeserializer<float[][]> {

    private static final PrimitiveFloatArray2dJsonDeserializer INSTANCE = new PrimitiveFloatArray2dJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveFloatArray2dJsonDeserializer}
     */
    public static PrimitiveFloatArray2dJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveFloatArray2dJsonDeserializer() { }

    @Override
    public float[][] doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        List<List<Float>> list = deserializeIntoList( reader, ctx, FloatJsonDeserializer.getInstance(), params );

        if ( list.isEmpty() ) {
            return new float[0][0];
        }

        List<Float> firstList = list.get( 0 );
        if ( firstList.isEmpty() ) {
            return new float[list.size()][0];
        }

        float[][] array = new float[list.size()][firstList.size()];

        int i = 0;
        int j;
        for ( List<Float> innerList : list ) {
            j = 0;
            for ( Float value : innerList ) {
                if ( null != value ) {
                    array[i][j] = value;
                }
                j++;
            }
            i++;
        }
        return array;
    }
}
