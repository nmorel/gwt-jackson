package com.github.nmorel.gwtjackson.client.ser.collection;

import java.util.Arrays;
import java.util.Collections;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class IterableJsonSerializerTest extends AbstractJsonSerializerTest<Iterable<String>> {

    @Override
    protected IterableJsonSerializer<Iterable<String>, String> createSerializer() {
        return IterableJsonSerializer.newInstance( StringJsonSerializer.getInstance() );
    }

    public void testEncodeValue() {
        assertSerialization( "[\"Hello\",\" \",\"World\",\"!\"]", Arrays.asList( "Hello", " ", "World", "!" ) );
        assertSerialization( "[]", Collections.<String>emptyList() );
    }

}
