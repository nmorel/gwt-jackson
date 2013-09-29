package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for {@link Boolean}.
 *
 * @author Nicolas Morel
 */
public class BooleanJsonSerializer extends JsonSerializer<Boolean> {

    private static final BooleanJsonSerializer INSTANCE = new BooleanJsonSerializer();

    /**
     * @return an instance of {@link BooleanJsonSerializer}
     */
    public static BooleanJsonSerializer getInstance() {
        return INSTANCE;
    }

    private BooleanJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull Boolean value, JsonEncodingContext ctx ) throws IOException {
        writer.value( value );
    }
}
