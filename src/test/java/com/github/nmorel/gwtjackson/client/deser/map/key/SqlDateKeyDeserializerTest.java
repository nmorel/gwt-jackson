package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.sql.Date;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.SqlDateKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlDateKeyDeserializerTest extends AbstractKeyDeserializerTest<Date> {

    @Override
    protected SqlDateKeyDeserializer createDeserializer() {
        return SqlDateKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Date( 1377543971773l ), "1377543971773" );
        assertDeserialization( new Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) ), "2012-08-18T17:45:56.543+02:00" );
    }
}
