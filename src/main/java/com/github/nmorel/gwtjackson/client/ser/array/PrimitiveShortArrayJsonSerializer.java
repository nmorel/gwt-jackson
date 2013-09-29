package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array of short.
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
    public void doSerialize( JsonWriter writer, @Nonnull short[] values, JsonSerializationContext ctx ) throws IOException {
        writer.beginArray();
        for ( short value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
