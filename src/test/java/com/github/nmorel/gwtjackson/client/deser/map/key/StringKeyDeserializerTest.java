package com.github.nmorel.gwtjackson.client.deser.map.key;

/**
 * @author Nicolas Morel
 */
public class StringKeyDeserializerTest extends AbstractKeyDeserializerTest<String> {

    @Override
    protected StringKeyDeserializer createDeserializer() {
        return StringKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( "", "" );
        assertDeserialization( "Json", "Json" );
        assertDeserialization( "&é(-è_ çà)='", "&é(-è_ çà)='" );
    }
}
