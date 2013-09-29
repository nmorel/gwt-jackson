package com.github.nmorel.gwtjackson.shared.advanced.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * Test from jackson-databind and adapted for the project
 */
public final class ObjectIdWithPolymorphicTester extends AbstractTester {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
    @JsonPropertyOrder(alphabetic = true)
    public static abstract class Base {

        public int value;

        public Base next;

        public Base() {
            this( 0 );
        }

        public Base( int v ) {
            value = v;
        }
    }

    public static class Impl extends Base {

        public int extra;

        public Impl() {
            this( 0, 0 );
        }

        public Impl( int v, int e ) {
            super( v );
            extra = e;
        }
    }

    /*
    /**********************************************************
    /* Constantes
    /**********************************************************
     */
    public static final ObjectIdWithPolymorphicTester INSTANCE = new ObjectIdWithPolymorphicTester();

    private ObjectIdWithPolymorphicTester() {
    }

    /*
    /*****************************************************
    /* Unit tests for polymorphic type handling
    /*****************************************************
     */

    public void testPolymorphicRoundtrip( ObjectMapperTester<Base> mapper ) {
        // create simple 2 node loop:
        Impl in1 = new Impl( 123, 456 );
        in1.next = new Impl( 111, 222 );
        in1.next.next = in1;

        String json = mapper.write( in1 );
        assertEquals( "{\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithPolymorphicTester$Impl\",\"id\":1," +
            "\"extra\":456,\"next\":{\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.identity" +
            ".ObjectIdWithPolymorphicTester$Impl\",\"id\":2,\"extra\":222,\"next\":1,\"value\":111},\"value\":123}", json );

        // then bring back...
        Base result0 = mapper.read( json );
        assertNotNull( result0 );
        assertSame( Impl.class, result0.getClass() );
        Impl result = (Impl) result0;
        assertEquals( 123, result.value );
        assertEquals( 456, result.extra );
        Impl result2 = (Impl) result.next;
        assertEquals( 111, result2.value );
        assertEquals( 222, result2.extra );
        assertSame( result, result2.next );
    }
}
