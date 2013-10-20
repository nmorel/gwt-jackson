package com.github.nmorel.gwtjackson.jackson.annotations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.Bean;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.RootBeanWithEmpty;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.RootBeanWithNoAnnotation;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonRootNameJacksonTest extends AbstractJacksonTest {

    @Override
    public void setUp() {
        super.setUp();
        objectMapper.configure( SerializationFeature.WRAP_ROOT_VALUE, true );
        objectMapper.configure( DeserializationFeature.UNWRAP_ROOT_VALUE, true );
    }

    @Test
    public void testRootName() {
        JsonRootNameTester.INSTANCE.testRootName( createMapper( Bean.class ) );
    }

    @Test
    public void testRootNameEmpty() {
        JsonRootNameTester.INSTANCE.testRootNameEmpty( createMapper( RootBeanWithEmpty.class ) );
    }

    @Test
    public void testRootNameNoAnnotation() {
        JsonRootNameTester.INSTANCE.testRootNameNoAnnotation( createMapper( RootBeanWithNoAnnotation.class ) );
    }

    @Test
    public void testUnwrappingFailing() {
        JsonRootNameTester.INSTANCE.testUnwrappingFailing( createReader( Bean.class ) );
    }
}
