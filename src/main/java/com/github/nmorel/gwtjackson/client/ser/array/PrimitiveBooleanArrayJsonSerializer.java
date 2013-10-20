package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array of boolean.
 *
 * @author Nicolas Morel
 */
public class PrimitiveBooleanArrayJsonSerializer extends JsonSerializer<boolean[]> {

    private static final PrimitiveBooleanArrayJsonSerializer INSTANCE = new PrimitiveBooleanArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveBooleanArrayJsonSerializer}
     */
    public static PrimitiveBooleanArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveBooleanArrayJsonSerializer() { }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull boolean[] values, JsonSerializationContext ctx ) throws IOException {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        writer.beginArray();
        for ( boolean value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
