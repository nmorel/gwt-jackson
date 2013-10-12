package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.ByteKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class ByteKeyDeserializerTest extends AbstractKeyDeserializerTest<Byte> {

    @Override
    protected ByteKeyDeserializer createDeserializer() {
        return ByteKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Byte( "34" ), "34" );
        assertDeserialization( new Byte( "1" ), "1" );
        assertDeserialization( Byte.MIN_VALUE, "-128" );
        assertDeserialization( Byte.MAX_VALUE, "127" );
    }
}
