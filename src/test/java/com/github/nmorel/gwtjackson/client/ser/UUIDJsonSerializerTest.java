package com.github.nmorel.gwtjackson.client.ser;

import java.util.UUID;

/**
 * @author Nicolas Morel
 */
public class UUIDJsonSerializerTest extends AbstractJsonSerializerTest<UUID> {

    @Override
    protected UUIDJsonSerializer createSerializer() {
        return UUIDJsonSerializer.getInstance();
    }

    @Override
    public void testSerializeValue() {
        String uuid = "550e8400-e29b-41d4-a716-446655440000";
        assertSerialization( "\"" + uuid + "\"", UUID.fromString( uuid ) );
    }
}
