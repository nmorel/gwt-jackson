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
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.DoubleJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for array of double.
 *
 * @author Nicolas Morel
 */
public class PrimitiveDoubleArrayJsonDeserializer extends AbstractArrayJsonDeserializer<double[]> {

    private static final PrimitiveDoubleArrayJsonDeserializer INSTANCE = new PrimitiveDoubleArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveDoubleArrayJsonDeserializer}
     */
    public static PrimitiveDoubleArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveDoubleArrayJsonDeserializer() { }

    @Override
    public double[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        List<Double> list = deserializeIntoList( reader, ctx, DoubleJsonDeserializer.getInstance() );

        double[] result = new double[list.size()];
        int i = 0;
        for ( Double value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    @Override
    protected double[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return new double[]{DoubleJsonDeserializer.getInstance().deserialize( reader, ctx )};
    }
}
