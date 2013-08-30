package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.PrivateAccessTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class PrivateAccessJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncodingPrivateField()
    {
        PrivateAccessTester.INSTANCE.testEncodingPrivateField( createEncoder( PrivateAccessTester.PrivateBean.class ) );
    }

    @Test
    public void testDecodingPrivateField()
    {
        PrivateAccessTester.INSTANCE.testDecodingPrivateField( createDecoder( PrivateAccessTester.PrivateBean.class ) );
    }
}
