package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for {@link String}.
 *
 * @author Nicolas Morel
 */
public class StringJsonSerializer extends JsonSerializer<String> {

    private static final StringJsonSerializer INSTANCE = new StringJsonSerializer();

    /**
     * @return an instance of {@link StringJsonSerializer}
     */
    public static StringJsonSerializer getInstance() {
        return INSTANCE;
    }

    private StringJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull String value, JsonEncodingContext ctx ) throws IOException {
        writer.value( value );
    }
}
