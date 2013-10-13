package com.github.nmorel.gwtjackson.client.deser.date;

import java.sql.Date;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.SqlDateJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlDateJsonDeserializerTest extends AbstractJsonDeserializerTest<Date> {

    @Override
    protected SqlDateJsonDeserializer createDeserializer() {
        return SqlDateJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Date( 1377543971773l ), "1377543971773" );
        assertEquals( getUTCTime( 2012, 8, 18, 0, 0, 0, 0 ), deserialize( "\"2012-08-18\"" ).getTime() );
    }
}
