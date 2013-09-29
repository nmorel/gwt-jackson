package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PolymorphismIdClassAsPropertyJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        PolymorphismIdClassAsPropertyTester.INSTANCE.testSerialize( createEncoder( PolymorphismIdClassAsPropertyTester.Person[].class ) );
    }

    @Test
    public void testDeserialize() {
        PolymorphismIdClassAsPropertyTester.INSTANCE.testDeserialize( createDecoder( PolymorphismIdClassAsPropertyTester.Person[].class ) );
    }
}
