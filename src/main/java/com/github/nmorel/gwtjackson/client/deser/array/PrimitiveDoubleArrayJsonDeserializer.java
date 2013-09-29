package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for array of double.
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
    public double[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        List<Double> list = decodeList( reader, ctx, NumberJsonDeserializer.getDoubleInstance() );

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
}
