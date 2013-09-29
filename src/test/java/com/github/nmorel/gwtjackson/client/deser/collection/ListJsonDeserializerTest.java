package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class ListJsonDeserializerTest extends AbstractJsonDeserializerTest<List<String>> {

    @Override
    protected JsonDeserializer<List<String>> createDeserializer() {
        return ListJsonDeserializer.newInstance( StringJsonDeserializer.getInstance() );
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( Arrays.asList( "Hello", " ", "World", "!" ), "[Hello, \" \", \"World\", \"!\"]" );
        assertDeserialization( Collections.<String>emptyList(), "[]" );
    }

}
