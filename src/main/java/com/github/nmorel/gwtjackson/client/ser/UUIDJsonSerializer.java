package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.UUID;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for {@link UUID}.
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
    public void doSerialize( JsonWriter writer, @Nonnull UUID value, JsonSerializationContext ctx ) throws IOException {
        writer.value( value.toString() );
    }
}
