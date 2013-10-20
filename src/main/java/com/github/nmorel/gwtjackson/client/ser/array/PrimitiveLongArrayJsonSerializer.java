package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array of long.
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
    public void doSerialize( JsonWriter writer, @Nonnull long[] values, JsonSerializationContext ctx ) throws IOException {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        writer.beginArray();
        for ( long value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
