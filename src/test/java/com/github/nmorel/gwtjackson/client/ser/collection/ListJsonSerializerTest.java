package com.github.nmorel.gwtjackson.client.ser.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class ListJsonSerializerTest extends AbstractJsonSerializerTest<List<String>> {

    @Override
    protected IterableJsonSerializer<List<String>, String> createSerializer() {
        return IterableJsonSerializer.newInstance( StringJsonSerializer.getInstance() );
    }

    public void testSerializeValue() {
        assertSerialization( "[\"Hello\",\" \",\"World\",\"!\"]", Arrays.asList( "Hello", " ", "World", "!" ) );
        assertSerialization( "[]", Collections.<String>emptyList() );
    }

}
