package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * Test for [JACKSON-429]
 */
public final class JsonIgnoreTypeTester extends AbstractTester {
    /*
    /**********************************************************
    /* Annotated helper classes
    /**********************************************************
     */

    @JsonIgnoreType(false)
    public static class NonIgnoredType {

        public int value;

        public IgnoredType ignored;
    }

    @JsonIgnoreType
    class IgnoredType { // note: non-static, can't be deserializer

        public IgnoredType( IgnoredType src ) {
        }
    }

    public static final JsonIgnoreTypeTester INSTANCE = new JsonIgnoreTypeTester();

    private JsonIgnoreTypeTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testDeserialize( ObjectReaderTester<NonIgnoredType> reader ) {
        NonIgnoredType bean = reader.read( "{\"value\":13}" );
        assertNotNull( bean );
        assertEquals( 13, bean.value );
        assertNull( bean.ignored );

        // And also ok to see something with that value; will just get ignored
        bean = reader.read( "{ \"ignored\":[1,2,{}], \"value\":9 }" );
        assertNotNull( bean );
        assertEquals( 9, bean.value );
        assertNull( bean.ignored );
    }

    public void testSerialize( ObjectWriterTester<NonIgnoredType> writer ) {
        NonIgnoredType bean = new NonIgnoredType();
        bean.value = 13;
        bean.ignored = new IgnoredType( null );

        assertEquals( "{\"value\":13}", writer.write( bean ) );
    }
}
