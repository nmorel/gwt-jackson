package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.PolymorphismIdMinimalClassAsWrapperArrayTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PolymorphismIdMinimalClassAsWrapperArrayJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        PolymorphismIdMinimalClassAsWrapperArrayTester.INSTANCE
            .testSerialize( createEncoder( PolymorphismIdMinimalClassAsWrapperArrayTester.Person[].class ) );
    }

    @Test
    public void testDeserialize() {
        PolymorphismIdMinimalClassAsWrapperArrayTester.INSTANCE
            .testDeserialize( createDecoder( PolymorphismIdMinimalClassAsWrapperArrayTester.Person[].class ) );
    }
}
