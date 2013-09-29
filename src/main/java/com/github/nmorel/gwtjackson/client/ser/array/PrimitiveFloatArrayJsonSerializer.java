package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array of float.
 *
 * @author Nicolas Morel
 */
public class PrimitiveFloatArrayJsonSerializer extends JsonSerializer<float[]> {

    private static final PrimitiveFloatArrayJsonSerializer INSTANCE = new PrimitiveFloatArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveFloatArrayJsonSerializer}
     */
    public static PrimitiveFloatArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveFloatArrayJsonSerializer() { }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull float[] values, JsonSerializationContext ctx ) throws IOException {
        writer.beginArray();
        for ( float value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
