package com.github.nmorel.gwtjackson.client.ser;

/**
 * @author Nicolas Morel
 */
public class BooleanJsonSerializerTest extends AbstractJsonSerializerTest<Boolean> {

    @Override
    protected BooleanJsonSerializer createSerializer() {
        return BooleanJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "true", true );
        assertSerialization( "true", Boolean.TRUE );
        assertSerialization( "false", false );
        assertSerialization( "false", Boolean.FALSE );
    }
}
