package com.github.nmorel.gwtjackson.client.deser.date;

import java.util.Date;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.DateJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class DateJsonDeserializerTest extends AbstractJsonDeserializerTest<Date> {

    @Override
    protected DateJsonDeserializer createDeserializer() {
        return DateJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Date( 1377543971773l ), "1377543971773" );
        assertEquals( getUTCDate( 2012, 8, 18, 15, 45, 56, 543 ), deserialize( "\"2012-08-18T17:45:56.543+02:00\"" ) );
    }
}
