package com.github.nmorel.gwtjackson.client.deser.array;

import java.util.Arrays;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer.ArrayCreator;

/**
 * @author Nicolas Morel
 */
public class ArrayJsonDeserializerTest extends AbstractJsonDeserializerTest<String[]> {

    @Override
    protected JsonDeserializer<String[]> createDeserializer() {
        return ArrayJsonDeserializer.newInstance( StringJsonDeserializer.getInstance(), new ArrayCreator<String>() {
            @Override
            public String[] create( int length ) {
                return new String[length];
            }
        } );
    }

    @Override
    public void testDecodeValue() {
        assertTrue( Arrays.deepEquals( new String[]{"Hello", " ", "World", "!"}, deserialize( "[Hello, \" \", \"World\", " +
            "" + "\"!\"]" ) ) );
        assertTrue( Arrays.deepEquals( new String[0], deserialize( "[]" ) ) );
    }

}
