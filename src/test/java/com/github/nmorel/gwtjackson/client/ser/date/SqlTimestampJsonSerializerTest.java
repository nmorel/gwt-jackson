package com.github.nmorel.gwtjackson.client.ser.date;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.DateJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimestampJsonSerializerTest extends AbstractJsonSerializerTest<Timestamp> {

    @Override
    protected DateJsonSerializer<Timestamp> createSerializer() {
        return DateJsonSerializer.getSqlTimestampInstance();
    }

    public void testSerializeValue() {
        // don't know how to deal with the timezone so we just use the same date
        Timestamp date = new Timestamp( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        assertSerialization( "" + date.getTime(), date );
    }
}
