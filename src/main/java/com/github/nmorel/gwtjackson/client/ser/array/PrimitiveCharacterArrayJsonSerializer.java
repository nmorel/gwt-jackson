package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
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
    public void doSerialize( JsonWriter writer, @Nonnull char[] values, JsonSerializationContext ctx ) throws IOException {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        if ( ctx.isWriteCharArraysAsJsonArrays() && !(ctx.isWriteSingleElemArraysUnwrapped() && values.length == 1) ) {
            writer.beginArray();
            for ( char value : values ) {
                writer.value( Character.toString( value ) );
            }
            writer.endArray();
        } else {
            writer.value( new String( values ) );
        }
    }
}
