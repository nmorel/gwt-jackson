package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.util.Date;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.DateKeySerializer;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

/**
 * @author Nicolas Morel
 */
public class DateKeySerializerTest extends AbstractKeySerializerTest<Date> {

    @Override
    protected DateKeySerializer createSerializer() {
        return DateKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        Date date = getUTCDate(2012, 8, 18, 12, 45, 56, 543);
        String expected = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601).format(date);
        assertSerialization(expected, date);
    }
}
