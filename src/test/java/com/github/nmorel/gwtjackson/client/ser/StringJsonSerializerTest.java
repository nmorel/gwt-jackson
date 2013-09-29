package com.github.nmorel.gwtjackson.client.ser;

/**
 * @author Nicolas Morel
 */
public class StringJsonSerializerTest extends AbstractJsonSerializerTest<String> {

    @Override
    protected StringJsonSerializer createSerializer() {
        return StringJsonSerializer.getInstance();
    }

    public void testEncodeValue() {
        assertSerialization( "\"Hello World!\"", "Hello World!" );
        assertSerialization( "\"\"", "" );
    }
}
