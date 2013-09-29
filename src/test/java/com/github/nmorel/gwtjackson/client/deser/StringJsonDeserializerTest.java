package com.github.nmorel.gwtjackson.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class StringJsonDeserializerTest extends AbstractJsonDeserializerTest<String> {

    @Override
    protected JsonDeserializer<String> createDeserializer() {
        return StringJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( "", "\"\"" );
        assertDeserialization( "Json", "Json" );
        assertDeserialization( "&é(-è_ çà)='", "\"&é(-è_ çà)='\"" );
    }
}
