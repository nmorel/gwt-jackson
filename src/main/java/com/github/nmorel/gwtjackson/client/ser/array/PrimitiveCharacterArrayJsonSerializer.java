package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array of char.
 *
 * @author Nicolas Morel
 */
public class PrimitiveCharacterArrayJsonSerializer extends JsonSerializer<char[]> {

    private static final PrimitiveCharacterArrayJsonSerializer INSTANCE = new PrimitiveCharacterArrayJsonSerializer();

    /**
     * @return an instance of {@link PrimitiveCharacterArrayJsonSerializer}
     */
    public static PrimitiveCharacterArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveCharacterArrayJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull char[] values, JsonEncodingContext ctx ) throws IOException {
        writer.value( new String( values ) );
    }
}
