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
            .testSerializeBeanWithDefaultConstructorPrivate( createWriter( JsonCreatorTester.BeanWithDefaultConstructorPrivate.class ) );
    }

    @Test
    public void testDeserializeBeanWithDefaultConstructorPrivate() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithDefaultConstructorPrivate( createReader( JsonCreatorTester.BeanWithDefaultConstructorPrivate.class ) );
    }

    @Test
    public void testSerializeBeanWithoutDefaultConstructorAndNoAnnotation() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithoutDefaultConstructorAndNoAnnotation( createWriter( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndNoAnnotation.class ) );
    }

    @Test
    public void testDeserializeBeanWithoutDefaultConstructorAndNoAnnotation() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithoutDefaultConstructorAndNoAnnotation( createReader( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndNoAnnotation.class ) );
    }

    @Test
    public void testSerializeBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithoutDefaultConstructorAndPropertiesAnnotation( createWriter( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    public void testDeserializeBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithoutDefaultConstructorAndPropertiesAnnotation( createReader( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    @Ignore("jackson doesn't support it yet")
    public void testDeserializeBeanWithMissingRequiredPropertyInCreator() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithMissingRequiredPropertyInCreator( createReader( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    public void testSerializeBeanWithConstructorAnnotated() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithConstructorAnnotated( createWriter( JsonCreatorTester.BeanWithConstructorAnnotated.class ) );
    }

    @Test
    public void testDeserializeBeanWithConstructorAnnotated() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithConstructorAnnotated( createReader( JsonCreatorTester.BeanWithConstructorAnnotated.class ) );
    }

    @Test
    public void testSerializeBeanWithFactoryMethod() {
        JsonCreatorTester.INSTANCE.testSerializeBeanWithFactoryMethod( createWriter( JsonCreatorTester.BeanWithFactoryMethod.class ) );
    }

    @Test
    public void testDeserializeBeanWithFactoryMethod() {
        JsonCreatorTester.INSTANCE.testDeserializeBeanWithFactoryMethod( createReader( JsonCreatorTester.BeanWithFactoryMethod.class ) );
    }

    @Test
    public void testSerializeBeanWithPrivateFactoryMethod() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithPrivateFactoryMethod( createWriter( JsonCreatorTester.BeanWithPrivateFactoryMethod.class ) );
    }

    @Test
    public void testDeserializeBeanWithPrivateFactoryMethod() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithPrivateFactoryMethod( createReader( JsonCreatorTester.BeanWithPrivateFactoryMethod.class ) );
    }

    @Test
    public void testSerializeBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
            .testSerializeBeanWithPropertiesOnlyPresentOnConstructor( createWriter( JsonCreatorTester
                .BeanWithPropertiesOnlyPresentOnConstructor.class ) );
    }

    @Test
    public void testDeserializeBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
            .testDeserializeBeanWithPropertiesOnlyPresentOnConstructor( createReader( JsonCreatorTester
                .BeanWithPropertiesOnlyPresentOnConstructor.class ) );
    }
}
