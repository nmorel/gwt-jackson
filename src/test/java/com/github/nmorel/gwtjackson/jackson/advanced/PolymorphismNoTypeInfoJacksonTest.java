package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismNoTypeInfoTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class PolymorphismNoTypeInfoJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncoding()
    {
        PolymorphismNoTypeInfoTester.INSTANCE.testEncoding( createEncoder( PolymorphismNoTypeInfoTester.Person[].class ) );
    }

    @Test
    public void testDecodingNonInstantiableBean()
    {
        PolymorphismNoTypeInfoTester.INSTANCE.testDecodingNonInstantiableBean( createDecoder( PolymorphismNoTypeInfoTester.Person[].class ) );
    }

    @Test
    public void testDecodingInstantiableBean()
    {
        PolymorphismNoTypeInfoTester.INSTANCE.testDecodingInstantiableBean( createDecoder( PolymorphismNoTypeInfoTester.Employee[].class ) );
    }
}
