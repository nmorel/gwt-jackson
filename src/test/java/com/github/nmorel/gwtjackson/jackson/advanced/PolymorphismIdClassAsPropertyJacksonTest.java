package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class PolymorphismIdClassAsPropertyJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncoding()
    {
        PolymorphismIdClassAsPropertyTester.INSTANCE.testEncoding( createEncoder( PolymorphismIdClassAsPropertyTester.Person[].class ) );
    }

    @Test
    public void testDecoding()
    {
        PolymorphismIdClassAsPropertyTester.INSTANCE.testDecoding( createDecoder( PolymorphismIdClassAsPropertyTester.Person[].class ) );
    }
}
