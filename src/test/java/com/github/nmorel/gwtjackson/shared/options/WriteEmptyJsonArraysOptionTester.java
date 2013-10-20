package com.github.nmorel.gwtjackson.shared.options;

import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class WriteEmptyJsonArraysOptionTester extends AbstractTester {

    public static class EmptyListBean {

        public List<String> empty = new ArrayList<String>();
    }

    public static class EmptyArrayBean {

        public String[] empty = new String[0];
    }

    public static final WriteEmptyJsonArraysOptionTester INSTANCE = new WriteEmptyJsonArraysOptionTester();

    private WriteEmptyJsonArraysOptionTester() {
    }

    public void testWriteEmptyList( ObjectWriterTester<EmptyListBean> writer ) {
        assertEquals( "{\"empty\":[]}", writer.write( new EmptyListBean() ) );
    }

    public void testWriteEmptyArray( ObjectWriterTester<EmptyArrayBean> writer ) {
        assertEquals( "{\"empty\":[]}", writer.write( new EmptyArrayBean() ) );
    }

    public void testWriteNonEmptyList( ObjectWriterTester<EmptyListBean> writer ) {
        assertEquals( "{}", writer.write( new EmptyListBean() ) );
    }

    public void testWriteNonEmptyArray( ObjectWriterTester<EmptyArrayBean> writer ) {
        assertEquals( "{}", writer.write( new EmptyArrayBean() ) );
    }

}
