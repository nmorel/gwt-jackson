package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class PolymorphismJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncoding()
    {
        PolymorphismTester.INSTANCE.testEncoding( createEncoder( PolymorphismTester.Person[].class ) );
    }

    @Test
    public void testDecoding()
    {
        PolymorphismTester.INSTANCE.testDecoding( createDecoder( PolymorphismTester.Person[].class ) );
    }
}
