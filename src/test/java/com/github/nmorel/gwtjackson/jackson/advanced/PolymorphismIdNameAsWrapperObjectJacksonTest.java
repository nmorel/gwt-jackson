package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdNameAsWrapperObjectTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PolymorphismIdNameAsWrapperObjectJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        PolymorphismIdNameAsWrapperObjectTester.INSTANCE
            .testSerialize( createEncoder( PolymorphismIdNameAsWrapperObjectTester.Person[].class ) );
    }

    @Test
    @Ignore("jackson can't event find the subtype, booooo")
    public void testDeserialize() {
        PolymorphismIdNameAsWrapperObjectTester.INSTANCE
            .testDeserialize( createDecoder( PolymorphismIdNameAsWrapperObjectTester.Person[].class ) );
    }
}
