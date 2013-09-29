package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.UUID;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for {@link java.util.UUID}.
 *
 * @author Nicolas Morel
 */
public class UUIDJsonSerializer extends JsonSerializer<UUID> {

    private static final UUIDJsonSerializer INSTANCE = new UUIDJsonSerializer();

    /**
     * @return an instance of {@link UUIDJsonSerializer}
     */
    public static UUIDJsonSerializer getInstance() {
        return INSTANCE;
    }

    private UUIDJsonSerializer() { }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull UUID value, JsonEncodingContext ctx ) throws IOException {
        writer.value( value.toString() );
    }
}
