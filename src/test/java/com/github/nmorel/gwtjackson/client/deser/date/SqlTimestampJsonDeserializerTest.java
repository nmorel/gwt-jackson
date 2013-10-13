package com.github.nmorel.gwtjackson.client.deser.date;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.SqlTimestampJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimestampJsonDeserializerTest extends AbstractJsonDeserializerTest<Timestamp> {

    @Override
    protected SqlTimestampJsonDeserializer createDeserializer() {
        return SqlTimestampJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Timestamp( 1377543971773l ), "1377543971773" );
        // can't do better without timezone
        assertNotNull( deserialize( "\"2012-08-18 17:45:56.543\"" ) );
    }
}
