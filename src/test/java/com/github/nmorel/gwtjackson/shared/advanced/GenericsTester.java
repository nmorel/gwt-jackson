package com.github.nmorel.gwtjackson.shared.advanced;

import java.util.Arrays;
import java.util.List;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class GenericsTester extends AbstractTester {

    public static class GenericType<T> {

        public T genericValue;

        public List<T> listWithGenericValues;

        public String name;
    }

    public static final GenericsTester INSTANCE = new GenericsTester();

    private GenericsTester() {
    }

    public void testSerializeString( ObjectWriterTester<GenericType<String>> writer ) {
        GenericType<String> bean = new GenericType<String>();
        bean.genericValue = "value";
        bean.listWithGenericValues = Arrays.asList( "Hello", "World" );
        bean.name = "generic";

        String expected = "{" +
            "\"genericValue\":\"value\"," +
            "\"listWithGenericValues\":[\"Hello\",\"World\"]," +
            "\"name\":\"generic\"" +
            "}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeString( ObjectReaderTester<GenericType<String>> reader ) {
        String input = "{" +
            "\"genericValue\":\"value\"," +
            "\"listWithGenericValues\":[\"Hello\",\"World\"]," +
            "\"name\":\"generic\"" +
            "}";

        GenericType<String> bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( "value", bean.genericValue );
        assertEquals( Arrays.asList( "Hello", "World" ), bean.listWithGenericValues );
        assertEquals( "generic", bean.name );
    }

}
