package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlTimestampKeySerializer;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

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
        String expected = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).format(date);
        assertSerialization(expected, date);
    }
}
