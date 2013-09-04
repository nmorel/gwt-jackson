package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsWrapperArrayTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class PolymorphismIdClassAsWrapperArrayJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncoding()
    {
        PolymorphismIdClassAsWrapperArrayTester.INSTANCE
            .testEncoding( createEncoder( PolymorphismIdClassAsWrapperArrayTester.Person[].class ) );
    }

    @Test
    public void testDecoding()
    {
        PolymorphismIdClassAsWrapperArrayTester.INSTANCE
            .testDecoding( createDecoder( PolymorphismIdClassAsWrapperArrayTester.Person[].class ) );
    }
}
