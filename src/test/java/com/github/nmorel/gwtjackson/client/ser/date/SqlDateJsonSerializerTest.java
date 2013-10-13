package com.github.nmorel.gwtjackson.client.ser.date;

import java.sql.Date;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.BaseDateJsonSerializer.SqlDateJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class SqlDateJsonSerializerTest extends AbstractJsonSerializerTest<java.sql.Date> {

    @Override
    protected SqlDateJsonSerializer createSerializer() {
        return SqlDateJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "\"2012-08-18\"", new Date( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) ) );
    }
}
