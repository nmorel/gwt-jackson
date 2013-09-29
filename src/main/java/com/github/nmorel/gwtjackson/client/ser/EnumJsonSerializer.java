package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for {@link Enum}.
 *
 * @author Nicolas Morel
 */
public class EnumJsonSerializer<E extends Enum<E>> extends JsonSerializer<E> {

    private static final EnumJsonSerializer<?> INSTANCE = new EnumJsonSerializer();

    /**
     * @return an instance of {@link EnumJsonSerializer}
     */
    public static EnumJsonSerializer getInstance() {
        return INSTANCE;
    }

    private EnumJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull E value, JsonEncodingContext ctx ) throws IOException {
        writer.value( value.name() );
    }
}
