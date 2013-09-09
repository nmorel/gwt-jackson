package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdMinimalClassAsWrapperArrayTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class PolymorphismIdMinimalClassAsWrapperArrayJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncoding()
    {
        PolymorphismIdMinimalClassAsWrapperArrayTester.INSTANCE
            .testEncoding( createEncoder( PolymorphismIdMinimalClassAsWrapperArrayTester.Person[].class ) );
    }

    @Test
    public void testDecoding()
    {
        PolymorphismIdMinimalClassAsWrapperArrayTester.INSTANCE
            .testDecoding( createDecoder( PolymorphismIdMinimalClassAsWrapperArrayTester.Person[].class ) );
    }
}
