package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.sql.Date;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlDateKeySerializer;

/**
 * @author Nicolas Morel
 */
public class SqlDateKeySerializerTest extends AbstractKeySerializerTest<Date> {

    @Override
    protected SqlDateKeySerializer createSerializer() {
        return SqlDateKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization("2012-08-18", new Date(getUTCTime(2012, 8, 18, 12, 45, 56, 543)));
    }
}
