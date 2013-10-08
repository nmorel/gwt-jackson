package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.util.UUID;

/**
 * @author Nicolas Morel
 */
public class UUIDKeySerializerTest extends AbstractKeySerializerTest<UUID> {

    @Override
    protected UUIDKeySerializer createSerializer() {
        return UUIDKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        String uuid = "550e8400-e29b-41d4-a716-446655440000";
        assertSerialization(uuid, UUID.fromString(uuid));
    }
}
