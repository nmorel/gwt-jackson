package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PrivateAccessTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PrivateAccessJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializePrivateField() {
        PrivateAccessTester.INSTANCE.testSerializePrivateField( createEncoder( PrivateAccessTester.PrivateBean.class ) );
    }

    @Test
    public void testDeserializePrivateField() {
        PrivateAccessTester.INSTANCE.testDeserializePrivateField( createDecoder( PrivateAccessTester.PrivateBean.class ) );
    }
}
