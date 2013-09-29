package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BooleanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for array of boolean.
 *
 * @author Nicolas Morel
 */
public class PrimitiveBooleanArrayJsonDeserializer extends AbstractArrayJsonDeserializer<boolean[]> {

    private static final PrimitiveBooleanArrayJsonDeserializer INSTANCE = new PrimitiveBooleanArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveBooleanArrayJsonDeserializer}
     */
    public static PrimitiveBooleanArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveBooleanArrayJsonDeserializer() { }

    @Override
    public boolean[] doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        List<Boolean> list = deserializeIntoList( reader, ctx, BooleanJsonDeserializer.getInstance() );

        boolean[] result = new boolean[list.size()];
        int i = 0;
        for ( Boolean value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }
}
