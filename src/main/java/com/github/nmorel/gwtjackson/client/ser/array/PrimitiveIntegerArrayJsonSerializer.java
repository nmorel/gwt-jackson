package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for array of int.
 *
 * @author Nicolas Morel
 */
public class PrimitiveIntegerArrayJsonSerializer extends JsonSerializer<int[]> {

    private static final PrimitiveIntegerArrayJsonSerializer INSTANCE = new PrimitiveIntegerArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveIntegerArrayJsonSerializer}
     */
    public static PrimitiveIntegerArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveIntegerArrayJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull int[] values, JsonEncodingContext ctx ) throws IOException {
        writer.beginArray();
        for ( int value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
