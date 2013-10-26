package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.IntegerJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

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

    private PrimitiveIntegerArrayJsonDeserializer() { }

    @Override
    public int[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        List<Integer> list = deserializeIntoList( reader, ctx, IntegerJsonDeserializer.getInstance() );

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

    @Override
    protected int[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return new int[]{IntegerJsonDeserializer.getInstance().deserialize( reader, ctx )};
    }
}
