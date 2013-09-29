package com.github.nmorel.gwtjackson.client.deser.date;

import java.sql.Time;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.DateJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimeJsonDeserializerTest extends AbstractJsonDeserializerTest<Time> {

    @Override
    protected JsonDeserializer<Time> createDeserializer() {
        return DateJsonDeserializer.getSqlTimeInstance();
    }

    @Override
    public void testDecodeValue() {
        assertDeserialization( new Time( 1377543971773l ), "1377543971773" );
        Time time = new Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) );
        assertEquals( time.toString(), deserialize( "\"" + time.toString() + "\"" ).toString() );
    }
}
