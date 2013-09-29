package com.github.nmorel.gwtjackson.jackson.advanced;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismNoTypeInfoTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PolymorphismNoTypeInfoJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        PolymorphismNoTypeInfoTester.INSTANCE.testSerialize( createEncoder( PolymorphismNoTypeInfoTester.Person[].class ) );
    }

    @Test
    public void testDeserializeNonInstantiableBean() {
        PolymorphismNoTypeInfoTester.INSTANCE
            .testDeserializeNonInstantiableBean( createDecoder( PolymorphismNoTypeInfoTester.Person[].class ) );
    }

    @Test
    public void testDeserializeInstantiableBean() {
        PolymorphismNoTypeInfoTester.INSTANCE
            .testDeserializeInstantiableBean( createDecoder( PolymorphismNoTypeInfoTester.Employee[].class ) );
    }
}
