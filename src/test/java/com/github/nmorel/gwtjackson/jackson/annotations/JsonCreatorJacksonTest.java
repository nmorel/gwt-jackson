package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonCreatorJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeBeanWithDefaultConstructorPrivate() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithDefaultConstructorPrivate( createEncoder( JsonCreatorTester.BeanWithDefaultConstructorPrivate.class ) );
    }

    @Test
    public void testDeserializeBeanWithDefaultConstructorPrivate() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithDefaultConstructorPrivate( createDecoder( JsonCreatorTester.BeanWithDefaultConstructorPrivate.class ) );
    }

    @Test
    public void testSerializeBeanWithoutDefaultConstructorAndNoAnnotation() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithoutDefaultConstructorAndNoAnnotation( createEncoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndNoAnnotation.class ) );
    }

    @Test
    public void testDeserializeBeanWithoutDefaultConstructorAndNoAnnotation() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithoutDefaultConstructorAndNoAnnotation( createDecoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndNoAnnotation.class ) );
    }

    @Test
    public void testSerializeBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithoutDefaultConstructorAndPropertiesAnnotation( createEncoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    public void testDeserializeBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithoutDefaultConstructorAndPropertiesAnnotation( createDecoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    @Ignore("jackson doesn't support it yet")
    public void testDeserializeBeanWithMissingRequiredPropertyInCreator() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithMissingRequiredPropertyInCreator( createDecoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    public void testSerializeBeanWithConstructorAnnotated() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithConstructorAnnotated( createEncoder( JsonCreatorTester.BeanWithConstructorAnnotated.class ) );
    }

    @Test
    public void testDeserializeBeanWithConstructorAnnotated() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithConstructorAnnotated( createDecoder( JsonCreatorTester.BeanWithConstructorAnnotated.class ) );
    }

    @Test
    public void testSerializeBeanWithFactoryMethod() {
        JsonCreatorTester.INSTANCE.testSerializeBeanWithFactoryMethod( createEncoder( JsonCreatorTester.BeanWithFactoryMethod.class ) );
    }

    @Test
    public void testDeserializeBeanWithFactoryMethod() {
        JsonCreatorTester.INSTANCE.testDeserializeBeanWithFactoryMethod( createDecoder( JsonCreatorTester.BeanWithFactoryMethod.class ) );
    }

    @Test
    public void testSerializeBeanWithPrivateFactoryMethod() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithPrivateFactoryMethod( createEncoder( JsonCreatorTester.BeanWithPrivateFactoryMethod.class ) );
    }

    @Test
    public void testDeserializeBeanWithPrivateFactoryMethod() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithPrivateFactoryMethod( createDecoder( JsonCreatorTester.BeanWithPrivateFactoryMethod.class ) );
    }

    @Test
    public void testSerializeBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithPropertiesOnlyPresentOnConstructor( createEncoder( JsonCreatorTester
                .BeanWithPropertiesOnlyPresentOnConstructor.class ) );
    }

    @Test
    public void testDeserializeBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithPropertiesOnlyPresentOnConstructor( createDecoder( JsonCreatorTester.BeanWithPropertiesOnlyPresentOnConstructor.class ) );
    }
}
