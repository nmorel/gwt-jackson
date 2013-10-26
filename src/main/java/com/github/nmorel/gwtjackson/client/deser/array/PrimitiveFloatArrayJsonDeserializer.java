package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.FloatJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for array of float.
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
    public float[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        List<Float> list = deserializeIntoList( reader, ctx, FloatJsonDeserializer.getInstance() );

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

    @Override
    protected float[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return new float[]{FloatJsonDeserializer.getInstance().deserialize( reader, ctx )};
    }
}
