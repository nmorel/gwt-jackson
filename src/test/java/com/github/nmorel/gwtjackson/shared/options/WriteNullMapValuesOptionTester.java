package com.github.nmorel.gwtjackson.shared.options;

import java.util.HashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class WriteNullMapValuesOptionTester extends AbstractTester {

    public static final WriteNullMapValuesOptionTester INSTANCE = new WriteNullMapValuesOptionTester();

    private WriteNullMapValuesOptionTester() {
    }

    public void testWriteNullValues( ObjectWriterTester<Map<String, String>> writer ) {
        Map<String, String> map = new HashMap<String, String>();
        map.put( "a", null );
        assertEquals( "{\"a\":null}", writer.write( map ) );
    }

    public void testWriteNonNullValues( ObjectWriterTester<Map<String, String>> writer ) {
        Map<String, String> map = new HashMap<String, String>();
        map.put( "a", null );
        assertEquals( "{}", writer.write( map ) );
    }

}
