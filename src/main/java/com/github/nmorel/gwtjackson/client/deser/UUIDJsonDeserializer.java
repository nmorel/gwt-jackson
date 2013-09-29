package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;
import java.util.UUID;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.UUID}.
 *
 * @author Nicolas Morel
 */
public class UUIDJsonDeserializer extends JsonDeserializer<UUID> {

    private static final UUIDJsonDeserializer INSTANCE = new UUIDJsonDeserializer();

    /**
     * @return an instance of {@link UUIDJsonDeserializer}
     */
    public static UUIDJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private UUIDJsonDeserializer() { }

    @Override
    public UUID doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        return UUID.fromString( reader.nextString() );
    }
}
