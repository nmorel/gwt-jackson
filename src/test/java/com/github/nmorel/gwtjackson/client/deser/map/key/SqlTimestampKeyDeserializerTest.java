package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.SqlTimestampKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimestampKeyDeserializerTest extends AbstractKeyDeserializerTest<Timestamp> {

    @Override
    protected SqlTimestampKeyDeserializer createDeserializer() {
        return SqlTimestampKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Timestamp( 1377543971773l ), "1377543971773" );
        assertDeserialization( new Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) ), "2012-08-18T17:45:56.543+02:00" );
    }
}
