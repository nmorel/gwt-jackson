package com.github.nmorel.gwtjackson.client.ser.date;

import java.util.Date;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.DateJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class DateJsonSerializerTest extends AbstractJsonSerializerTest<Date> {

    @Override
    protected DateJsonSerializer<Date> createSerializer() {
        return DateJsonSerializer.getDateInstance();
    }

    public void testEncodeValue() {
        Date date = getUTCDate( 2012, 8, 18, 12, 45, 56, 543 );
        assertSerialization( "" + date.getTime(), date );
    }
}
