package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.util.UUID;

/**
 * @author Nicolas Morel
 */
public class UUIDKeyDeserializerTest extends AbstractKeyDeserializerTest<UUID> {

    private static String uuid = "550e8400-e29b-41d4-a716-446655440000";

    @Override
    protected UUIDKeyDeserializer createDeserializer() {
        return UUIDKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( UUID.fromString( uuid ), uuid );
    }
}
