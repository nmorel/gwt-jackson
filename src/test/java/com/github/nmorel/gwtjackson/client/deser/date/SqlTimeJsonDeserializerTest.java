package com.github.nmorel.gwtjackson.client.deser.date;

import java.sql.Time;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.SqlTimeJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimeJsonDeserializerTest extends AbstractJsonDeserializerTest<Time> {

    @Override
    protected SqlTimeJsonDeserializer createDeserializer() {
        return SqlTimeJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Time( 1377543971773l ), "1377543971773" );
        Time time = new Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) );
        assertEquals( time.toString(), deserialize( "\"" + time.toString() + "\"" ).toString() );
    }
}
