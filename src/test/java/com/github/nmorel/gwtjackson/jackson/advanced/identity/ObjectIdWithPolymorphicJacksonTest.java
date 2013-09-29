package com.github.nmorel.gwtjackson.jackson.advanced.identity;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithPolymorphicTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithPolymorphicTester.Base;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdWithPolymorphicJacksonTest extends AbstractJacksonTest {

    private ObjectIdWithPolymorphicTester tester = ObjectIdWithPolymorphicTester.INSTANCE;

    @Test
    public void testPolymorphicRoundtrip() {
        tester.testPolymorphicRoundtrip( createMapper( Base.class ) );
    }
}
