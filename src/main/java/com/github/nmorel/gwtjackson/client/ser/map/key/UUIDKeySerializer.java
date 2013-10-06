package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;
import java.util.UUID;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link UUID}.
 *
 * @author Nicolas Morel
 */
public final class UUIDKeySerializer extends KeySerializer<UUID> {

    private static final UUIDKeySerializer INSTANCE = new UUIDKeySerializer();

    /**
     * @return an instance of {@link UUIDKeySerializer}
     */
    public static UUIDKeySerializer getInstance() {
        return INSTANCE;
    }

    private UUIDKeySerializer() { }

    @Override
    protected String doSerialize( @Nonnull UUID value, JsonSerializationContext ctx ) {
        return value.toString();
    }
}
