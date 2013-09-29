package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for array of long.
 *
 * @author Nicolas Morel
 */
public class PrimitiveLongArrayJsonSerializer extends JsonSerializer<long[]> {

    private static final PrimitiveLongArrayJsonSerializer INSTANCE = new PrimitiveLongArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveLongArrayJsonSerializer}
     */
    public static PrimitiveLongArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveLongArrayJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull long[] values, JsonEncodingContext ctx ) throws IOException {
        writer.beginArray();
        for ( long value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
