package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for array of short.
 *
 * @author Nicolas Morel
 */
public class PrimitiveShortArrayJsonSerializer extends JsonSerializer<short[]> {

    private static final PrimitiveShortArrayJsonSerializer INSTANCE = new PrimitiveShortArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveShortArrayJsonSerializer}
     */
    public static PrimitiveShortArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveShortArrayJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull short[] values, JsonEncodingContext ctx ) throws IOException {
        writer.beginArray();
        for ( short value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
