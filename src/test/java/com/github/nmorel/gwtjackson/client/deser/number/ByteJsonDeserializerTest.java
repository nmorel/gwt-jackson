package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class ByteJsonDeserializerTest extends AbstractJsonDeserializerTest<Byte> {

    @Override
    protected JsonDeserializer<Byte> createDeserializer() {
        return NumberJsonDeserializer.getByteInstance();
    }

    @Override
    public void testDecodeValue() {
        assertDeserialization( new Byte( "34" ), "34" );
        assertDeserialization( new Byte( "1" ), "\"1\"" );
        assertDeserialization( Byte.MIN_VALUE, "-128" );
        assertDeserialization( Byte.MAX_VALUE, "127" );
    }
}
