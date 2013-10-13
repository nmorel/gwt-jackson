package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.ByteJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class ByteJsonDeserializerTest extends AbstractJsonDeserializerTest<Byte> {

    @Override
    protected ByteJsonDeserializer createDeserializer() {
        return ByteJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Byte( "34" ), "34" );
        assertDeserialization( new Byte( "1" ), "\"1\"" );
        assertDeserialization( Byte.MIN_VALUE, "-128" );
        assertDeserialization( Byte.MAX_VALUE, "127" );
    }
}
