package com.github.nmorel.gwtjackson.client.deser.map.key;

/**
 * @author Nicolas Morel
 */
public class BooleanKeyDeserializerTest extends AbstractKeyDeserializerTest<Boolean> {

    @Override
    protected BooleanKeyDeserializer createDeserializer() {
        return BooleanKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertTrue( deserialize( "true" ) );
        assertTrue( deserialize( "trUe" ) );

        assertFalse( deserialize( "faLse" ) );
        assertFalse( deserialize( "false" ) );
        assertFalse( deserialize( "other" ) );
    }
}
