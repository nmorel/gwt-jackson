package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.InheritanceTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class InheritanceJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializePrivateField() {
        InheritanceTester.INSTANCE.testSerialize( createEncoder( InheritanceTester.ChildBean.class ) );
    }

    @Test
    public void testDeserializePrivateField() {
        InheritanceTester.INSTANCE.testDeserialize( createDecoder( InheritanceTester.ChildBean.class ) );
    }
}
