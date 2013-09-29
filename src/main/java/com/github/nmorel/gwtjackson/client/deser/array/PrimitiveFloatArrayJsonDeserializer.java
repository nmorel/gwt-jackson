package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for array of float.
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
    public float[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        List<Float> list = decodeList( reader, ctx, NumberJsonDeserializer.getFloatInstance() );

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
}
