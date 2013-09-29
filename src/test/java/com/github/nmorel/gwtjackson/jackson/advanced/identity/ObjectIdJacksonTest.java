package com.github.nmorel.gwtjackson.jackson.advanced.identity;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Company;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Wrapper;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdJacksonTest extends AbstractJacksonTest {

    private ObjectIdTester tester = ObjectIdTester.INSTANCE;

    @Test
    public void testColumnMetadata() {
        tester.testColumnMetadata( createMapper( Wrapper.class ) );
    }

    @Test
    public void testMixedRefsIssue188() {
        tester.testMixedRefsIssue188( createMapper( Company.class ) );
    }
}
