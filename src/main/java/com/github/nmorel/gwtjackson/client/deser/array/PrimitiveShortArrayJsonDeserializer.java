package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.ShortJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for array of short.
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
    public short[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        List<Short> list = deserializeIntoList( reader, ctx, ShortJsonDeserializer.getInstance() );

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

    @Override
    protected short[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return new short[]{ShortJsonDeserializer.getInstance().deserialize( reader, ctx )};
    }
}
