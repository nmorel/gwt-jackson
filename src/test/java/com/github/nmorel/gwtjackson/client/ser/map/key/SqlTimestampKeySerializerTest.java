package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlTimestampKeySerializer;
import com.github.nmorel.gwtjackson.client.utils.DateFormat;

/**
 * @author Nicolas Morel
 */
public class SqlTimestampKeySerializerTest extends AbstractKeySerializerTest<Timestamp> {

    @Override
    protected SqlTimestampKeySerializer createSerializer() {
        return SqlTimestampKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        Timestamp date = new Timestamp(getUTCTime(2012, 8, 18, 12, 45, 56, 543));
        String expected = DateFormat.format( date );
        assertSerialization(expected, date);
    }
}
