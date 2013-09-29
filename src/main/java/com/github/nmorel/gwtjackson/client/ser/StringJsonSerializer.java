package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for {@link String}.
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
    public void doSerialize( JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx ) throws IOException {
        writer.value( value );
    }
}
