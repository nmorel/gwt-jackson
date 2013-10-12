package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.sql.Time;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.SqlTimeKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimeKeyDeserializerTest extends AbstractKeyDeserializerTest<Time> {

    @Override
    protected SqlTimeKeyDeserializer createDeserializer() {
        return SqlTimeKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Time( 1377543971773l ), "1377543971773" );
        assertDeserialization( new Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) ), "2012-08-18T17:45:56.543+02:00" );
    }
}
