package com.github.nmorel.gwtjackson.client.ser.date;

import java.sql.Date;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.DateJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class SqlDateJsonSerializerTest extends AbstractJsonSerializerTest<java.sql.Date> {

    @Override
    protected DateJsonSerializer<java.sql.Date> createSerializer() {
        return DateJsonSerializer.getSqlDateInstance();
    }

    public void testEncodeValue() {
        assertSerialization( "\"2012-08-18\"", new Date( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) ) );
    }
}
