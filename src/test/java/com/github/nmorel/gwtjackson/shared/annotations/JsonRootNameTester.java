package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;

/**
 * Unit tests dealing with handling of "root element wrapping",
 * including configuration of root name to use.
 */
public final class JsonRootNameTester extends AbstractTester {

    @JsonRootName( "rudy" )
    public static class Bean {

        public int a = 3;
    }

    @JsonRootName( "" )
    public static class RootBeanWithEmpty {

        public int a = 2;
    }

    public static class RootBeanWithNoAnnotation {

        public int a = 4;
    }

    public static final JsonRootNameTester INSTANCE = new JsonRootNameTester();

    private JsonRootNameTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testRootName( ObjectMapperTester<Bean> mapper ) {
        String json = mapper.write( new Bean() );
        assertEquals( "{\"rudy\":{\"a\":3}}", json );
        Bean bean = mapper.read( json );
        assertNotNull( bean );
        assertEquals( 3, bean.a );
    }

    public void testRootNameEmpty( ObjectMapperTester<RootBeanWithEmpty> mapper ) {
        String json = mapper.write( new RootBeanWithEmpty() );
        assertEquals( "{\"RootBeanWithEmpty\":{\"a\":2}}", json );
        RootBeanWithEmpty bean = mapper.read( json );
        assertNotNull( bean );
        assertEquals( 2, bean.a );
    }

    public void testRootNameNoAnnotation( ObjectMapperTester<RootBeanWithNoAnnotation> mapper ) {
        String json = mapper.write( new RootBeanWithNoAnnotation() );
        assertEquals( "{\"RootBeanWithNoAnnotation\":{\"a\":4}}", json );
        RootBeanWithNoAnnotation bean = mapper.read( json );
        assertNotNull( bean );
        assertEquals( 4, bean.a );
    }

    public void testUnwrappingFailing( ObjectReaderTester<Bean> reader ) {
        try { // must not have extra wrapping
            reader.read( "{\"a\":4}" );
            fail( "Should have failed" );
        } catch ( JsonDeserializationException e ) {
            // should fail
        }

        Bean bean = reader.read( "{\"rudy\":{\"a\":4}}" );
        assertNotNull( bean );
        assertEquals( 4, bean.a );
    }
}
