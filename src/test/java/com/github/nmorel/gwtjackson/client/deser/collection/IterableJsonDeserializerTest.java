package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Arrays;
import java.util.Collections;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class IterableJsonDeserializerTest extends AbstractJsonDeserializerTest<Iterable<String>> {

    @Override
    protected JsonDeserializer<Iterable<String>> createDeserializer() {
        return IterableJsonDeserializer.newInstance( StringJsonDeserializer.getInstance() );
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( Arrays.asList( "Hello", " ", "World", "!" ), "[Hello, \" \", \"World\", \"!\"]" );
        assertDeserialization( Collections.<String>emptyList(), "[]" );
    }

}
