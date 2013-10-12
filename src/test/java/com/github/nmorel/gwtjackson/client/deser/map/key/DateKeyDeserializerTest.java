package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.util.Date;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.DateKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class DateKeyDeserializerTest extends AbstractKeyDeserializerTest<Date> {

    @Override
    protected DateKeyDeserializer createDeserializer() {
        return DateKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Date( 1377543971773l ), "1377543971773" );
        assertDeserialization( getUTCDate( 2012, 8, 18, 15, 45, 56, 543 ), "2012-08-18T17:45:56.543+02:00" );
    }
}
