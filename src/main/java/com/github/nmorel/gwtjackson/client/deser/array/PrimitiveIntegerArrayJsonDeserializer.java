package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for array of int.
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

    private PrimitiveIntegerArrayJsonDeserializer() { }

    @Override
    public int[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        List<Integer> list = decodeList( reader, ctx, NumberJsonDeserializer.getIntegerInstance() );

        int[] result = new int[list.size()];
        int i = 0;
        for ( Integer value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }
}
