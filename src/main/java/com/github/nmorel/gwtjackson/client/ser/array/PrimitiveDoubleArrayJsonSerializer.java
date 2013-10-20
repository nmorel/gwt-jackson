package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array of double.
 *
 * @author Nicolas Morel
 */
public class PrimitiveDoubleArrayJsonSerializer extends JsonSerializer<double[]> {

    private static final PrimitiveDoubleArrayJsonSerializer INSTANCE = new PrimitiveDoubleArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveDoubleArrayJsonSerializer}
     */
    public static PrimitiveDoubleArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveDoubleArrayJsonSerializer() { }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull double[] values, JsonSerializationContext ctx ) throws IOException {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        writer.beginArray();
        for ( double value : values ) {
            writer.value( value );
        }
        writer.endArray();
    }
}
