package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.sql.Time;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlTimeKeySerializer;
import com.github.nmorel.gwtjackson.client.utils.DateFormat;

/**
 * @author Nicolas Morel
 */
public class SqlTimeKeySerializerTest extends AbstractKeySerializerTest<Time> {

    @Override
    protected SqlTimeKeySerializer createSerializer() {
        return SqlTimeKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        Time time = new Time( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        String expected = DateFormat.format( time );
        assertSerialization( expected, time );
    }
}
