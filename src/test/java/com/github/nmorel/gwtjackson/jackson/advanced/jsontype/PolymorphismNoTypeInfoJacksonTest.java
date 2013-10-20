package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.PolymorphismNoTypeInfoTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PolymorphismNoTypeInfoJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        PolymorphismNoTypeInfoTester.INSTANCE.testSerialize( createWriter( PolymorphismNoTypeInfoTester.Person[].class ) );
    }

    @Test
    public void testDeserializeNonInstantiableBean() {
        PolymorphismNoTypeInfoTester.INSTANCE
            .testDeserializeNonInstantiableBean( createReader( PolymorphismNoTypeInfoTester.Person[].class ) );
    }

    @Test
    public void testDeserializeInstantiableBean() {
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        PolymorphismNoTypeInfoTester.INSTANCE
            .testDeserializeInstantiableBean( createReader( PolymorphismNoTypeInfoTester.Employee[].class ) );
    }
}
