package com.github.nmorel.gwtjackson.client.ser.date;

import java.util.Date;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.BaseDateJsonSerializer.DateJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class DateJsonSerializerTest extends AbstractJsonSerializerTest<Date> {

    @Override
    protected DateJsonSerializer createSerializer() {
        return DateJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        Date date = getUTCDate( 2012, 8, 18, 12, 45, 56, 543 );
        assertSerialization( "" + date.getTime(), date );
    }
}
