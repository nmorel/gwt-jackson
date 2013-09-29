package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class SetJsonDeserializerTest extends AbstractJsonDeserializerTest<Set<String>> {

    @Override
    protected JsonDeserializer<Set<String>> createDeserializer() {
        return SetJsonDeserializer.newInstance( StringJsonDeserializer.getInstance() );
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new HashSet<String>( Arrays.asList( "Hello", " ", "World", "!" ) ), "[Hello, \" \", \"World\", " +
            "" + "\"!\"]" );
        assertDeserialization( Collections.<String>emptySet(), "[]" );
    }

}
