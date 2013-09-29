package com.github.nmorel.gwtjackson.client.deser;

import java.util.UUID;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class UUIDJsonDeserializerTest extends AbstractJsonDeserializerTest<UUID> {

    private static String uuid = "550e8400-e29b-41d4-a716-446655440000";

    @Override
    protected JsonDeserializer<UUID> createDeserializer() {
        return UUIDJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( UUID.fromString( uuid ), "\"" + uuid + "\"" );
    }
}
