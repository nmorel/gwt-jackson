package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdNameAsWrapperObjectTester;
import org.junit.Ignore;
import org.junit.Test;

/** @author Nicolas Morel */
public class PolymorphismIdNameAsWrapperObjectJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncoding()
    {
        PolymorphismIdNameAsWrapperObjectTester.INSTANCE
            .testEncoding( createEncoder( PolymorphismIdNameAsWrapperObjectTester.Person[].class ) );
    }

    @Test
    @Ignore( "jackson can't event find the subtype, booooo" )
    public void testDecoding()
    {
        PolymorphismIdNameAsWrapperObjectTester.INSTANCE
            .testDecoding( createDecoder( PolymorphismIdNameAsWrapperObjectTester.Person[].class ) );
    }
}
