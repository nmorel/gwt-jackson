package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.sql.Date;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlDateKeySerializer;
import com.github.nmorel.gwtjackson.client.utils.DateFormat;

/**
 * @author Nicolas Morel
 */
public class SqlDateKeySerializerTest extends AbstractKeySerializerTest<Date> {

    @Override
    protected SqlDateKeySerializer createSerializer() {
        return SqlDateKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        Date date = new Date( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        String expected = DateFormat.format( date );
        assertSerialization( expected, date );
    }
}
