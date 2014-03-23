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
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.IntegerJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for 2D array of int.
 *
 * @author Nicolas Morel
 */
public class PrimitiveIntegerArray2dJsonDeserializer extends AbstractArray2dJsonDeserializer<int[][]> {

    private static final PrimitiveIntegerArray2dJsonDeserializer INSTANCE = new PrimitiveIntegerArray2dJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveIntegerArray2dJsonDeserializer}
     */
    public static PrimitiveIntegerArray2dJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveIntegerArray2dJsonDeserializer() { }

    @Override
    public int[][] doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        List<List<Integer>> list = deserializeIntoList( reader, ctx, IntegerJsonDeserializer.getInstance(), params );

        if ( list.isEmpty() ) {
            return new int[0][0];
        }

        List<Integer> firstList = list.get( 0 );
        if ( firstList.isEmpty() ) {
            return new int[list.size()][0];
        }

        int[][] array = new int[list.size()][firstList.size()];

        int i = 0;
        int j;
        for ( List<Integer> innerList : list ) {
            j = 0;
            for ( Integer value : innerList ) {
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
