package com.github.nmorel.gwtjackson.jackson.advanced.identity;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithEqualsTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithEqualsTester.Foo;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdWithEqualsJacksonTest extends AbstractJacksonTest {

    private ObjectIdWithEqualsTester tester = ObjectIdWithEqualsTester.INSTANCE;

    @Override
    public void setUp() {
        super.setUp();
        //objectMapper.enable( com.fasterxml.jackson.databind.SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID );
    }

    @Test
    @Ignore( "Ignored because the options to use equality is only available since 2.3.0" )
    public void testSimpleEquals() {
        tester.testSimpleEquals( createMapper( Foo.class ) );
    }
}
