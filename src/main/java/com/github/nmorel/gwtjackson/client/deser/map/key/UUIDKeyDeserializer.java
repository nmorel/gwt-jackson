package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.util.UUID;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link KeyDeserializer} implementation for {@link UUID}.
 *
 * @author Nicolas Morel
 */
public final class UUIDKeyDeserializer extends KeyDeserializer<UUID> {

    private static final UUIDKeyDeserializer INSTANCE = new UUIDKeyDeserializer();

    /**
     * @return an instance of {@link UUIDKeyDeserializer}
     */
    public static UUIDKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private UUIDKeyDeserializer() { }

    @Override
    protected UUID doDeserialize( String key, JsonDeserializationContext ctx ) {
        return UUID.fromString( key );
    }
}
