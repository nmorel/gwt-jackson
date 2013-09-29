package com.github.nmorel.gwtjackson.client.deser.date;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.DateJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimestampJsonDeserializerTest extends AbstractJsonDeserializerTest<Timestamp> {

    @Override
    protected JsonDeserializer<Timestamp> createDeserializer() {
        return DateJsonDeserializer.getSqlTimestampInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Timestamp( 1377543971773l ), "1377543971773" );
        // can't do better without timezone
        assertNotNull( deserialize( "\"2012-08-18 17:45:56.543\"" ) );
    }
}
