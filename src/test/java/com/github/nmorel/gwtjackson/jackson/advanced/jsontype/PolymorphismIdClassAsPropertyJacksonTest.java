package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.PolymorphismIdClassAsPropertyTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PolymorphismIdClassAsPropertyJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        PolymorphismIdClassAsPropertyTester.INSTANCE.testSerialize( createWriter( PolymorphismIdClassAsPropertyTester.Person[].class ) );
    }

    @Test
    public void testDeserialize() {
        PolymorphismIdClassAsPropertyTester.INSTANCE.testDeserialize( createReader( PolymorphismIdClassAsPropertyTester.Person[].class ) );
    }
}
