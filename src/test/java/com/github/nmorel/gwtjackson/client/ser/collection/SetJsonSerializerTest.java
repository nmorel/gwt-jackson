package com.github.nmorel.gwtjackson.client.ser.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class SetJsonSerializerTest extends AbstractJsonSerializerTest<Set<String>> {

    @Override
    protected IterableJsonSerializer<Set<String>, String> createSerializer() {
        return IterableJsonSerializer.newInstance( StringJsonSerializer.getInstance() );
    }

    public void testEncodeValue() {
        // can't predict the order so we just encode one element
        assertSerialization( "[\"Hello\"]", new HashSet<String>( Arrays.asList( "Hello", "Hello" ) ) );
        assertSerialization( "[]", Collections.<String>emptySet() );
    }

}
