package com.github.nmorel.gwtjackson.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class BooleanJsonDeserializerTest extends AbstractJsonDeserializerTest<Boolean> {

    @Override
    protected JsonDeserializer<Boolean> createDeserializer() {
        return BooleanJsonDeserializer.getInstance();
    }

    @Override
    public void testDecodeValue() {
        assertTrue( deserialize( "true" ) );
        assertTrue( deserialize( "\"trUe\"" ) );
        assertTrue( deserialize( "1" ) );

        assertFalse( deserialize( "faLse" ) );
        assertFalse( deserialize( "\"false\"" ) );
        assertFalse( deserialize( "0" ) );
        assertFalse( deserialize( "other" ) );
    }
}
