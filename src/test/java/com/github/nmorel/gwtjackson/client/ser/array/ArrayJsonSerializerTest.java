package com.github.nmorel.gwtjackson.client.ser.array;

import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class ArrayJsonSerializerTest extends AbstractJsonSerializerTest<String[]> {

    @Override
    protected JsonSerializer<String[]> createSerializer() {
        return ArrayJsonSerializer.newInstance( StringJsonSerializer.getInstance() );
    }

    public void testSerializeValue() {
        assertSerialization( "[\"Hello\",\" \",\"World\",\"!\"]", new String[]{"Hello", " ", "World", "!"} );
        assertSerialization( "[]", new String[0] );
    }

}
