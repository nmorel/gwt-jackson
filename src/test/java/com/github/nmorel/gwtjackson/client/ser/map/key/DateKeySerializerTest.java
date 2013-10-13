package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.util.Date;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.DateKeySerializer;
import com.github.nmorel.gwtjackson.client.utils.DateFormat;

/**
 * @author Nicolas Morel
 */
public class DateKeySerializerTest extends AbstractKeySerializerTest<Date> {

    @Override
    protected DateKeySerializer createSerializer() {
        return DateKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        Date date = getUTCDate( 2012, 8, 18, 12, 45, 56, 543 );
        String expected = DateFormat.format( date );
        assertSerialization( expected, date );
    }
}
