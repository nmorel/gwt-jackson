package com.github.nmorel.gwtjackson.shared.options;

import java.util.Arrays;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * @author Nicolas Morel
 */
public final class CharArrayOptionTester extends AbstractTester {

    public static final CharArrayOptionTester INSTANCE = new CharArrayOptionTester();

    private CharArrayOptionTester() {
    }

    public void testCharArraysAsString( ObjectMapperTester<char[]> mapper ) {
        char[] chars = new char[]{'a', 'b', 'c'};

        String json = mapper.write( chars );
        assertEquals( "\"abc\"", json );

        assertTrue( Arrays.equals( chars, mapper.read( json ) ) );
    }

    public void testCharArraysAsArray( ObjectMapperTester<char[]> mapper ) {
        char[] chars = new char[]{'a', 'b', 'c'};

        String json = mapper.write( chars );
        assertEquals( "[\"a\",\"b\",\"c\"]", json );

        assertTrue( Arrays.equals( chars, mapper.read( json ) ) );
    }

}
