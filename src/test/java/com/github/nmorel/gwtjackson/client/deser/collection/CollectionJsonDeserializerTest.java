package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class CollectionJsonDeserializerTest extends AbstractJsonDeserializerTest<Collection<String>> {

    @Override
    protected JsonDeserializer<Collection<String>> createDeserializer() {
        return CollectionJsonDeserializer.newInstance( StringJsonDeserializer.getInstance() );
    }

    @Override
    public void testDecodeValue() {
        assertDeserialization( Arrays.asList( "Hello", " ", "World", "!" ), "[Hello, \" \", \"World\", \"!\"]" );
        assertDeserialization( Collections.<String>emptyList(), "[]" );
    }

}
