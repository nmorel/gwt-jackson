package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for array of short.
 *
 * @author Nicolas Morel
 */
public class PrimitiveShortArrayJsonDeserializer extends AbstractArrayJsonDeserializer<short[]> {

    private static final PrimitiveShortArrayJsonDeserializer INSTANCE = new PrimitiveShortArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveShortArrayJsonDeserializer}
     */
    public static PrimitiveShortArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveShortArrayJsonDeserializer() { }

    @Override
    public short[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        List<Short> list = decodeList( reader, ctx, NumberJsonDeserializer.getShortInstance() );

        short[] result = new short[list.size()];
        int i = 0;
        for ( Short value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }
}
