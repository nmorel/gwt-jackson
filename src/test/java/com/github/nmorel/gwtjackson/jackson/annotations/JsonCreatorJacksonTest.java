package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class JsonCreatorJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncodingBeanWithDefaultConstructorPrivate()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithDefaultConstructorPrivate( createEncoder( JsonCreatorTester.BeanWithDefaultConstructorPrivate.class ) );
    }

    @Test
    public void testDecodingBeanWithDefaultConstructorPrivate()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithDefaultConstructorPrivate( createDecoder( JsonCreatorTester.BeanWithDefaultConstructorPrivate.class ) );
    }

    @Test
    public void testEncodingBeanWithoutDefaultConstructorAndNoAnnotation()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithoutDefaultConstructorAndNoAnnotation( createEncoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndNoAnnotation.class ) );
    }

    @Test
    public void testDecodingBeanWithoutDefaultConstructorAndNoAnnotation()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithoutDefaultConstructorAndNoAnnotation( createDecoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndNoAnnotation.class ) );
    }

    @Test
    public void testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( createEncoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    public void testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( createDecoder( JsonCreatorTester
                .BeanWithoutDefaultConstructorAndPropertiesAnnotation.class ) );
    }

    @Test
    public void testEncodingBeanWithConstructorAnnotated()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithConstructorAnnotated( createEncoder( JsonCreatorTester.BeanWithConstructorAnnotated.class ) );
    }

    @Test
    public void testDecodingBeanWithConstructorAnnotated()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithConstructorAnnotated( createDecoder( JsonCreatorTester.BeanWithConstructorAnnotated.class ) );
    }

    @Test
    public void testEncodingBeanWithFactoryMethod()
    {
        JsonCreatorTester.INSTANCE.testEncodingBeanWithFactoryMethod( createEncoder( JsonCreatorTester.BeanWithFactoryMethod.class ) );
    }

    @Test
    public void testDecodingBeanWithFactoryMethod()
    {
        JsonCreatorTester.INSTANCE.testDecodingBeanWithFactoryMethod( createDecoder( JsonCreatorTester.BeanWithFactoryMethod.class ) );
    }

    @Test
    public void testEncodingBeanWithPrivateFactoryMethod()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithPrivateFactoryMethod( createEncoder( JsonCreatorTester.BeanWithPrivateFactoryMethod.class ) );
    }

    @Test
    public void testDecodingBeanWithPrivateFactoryMethod()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithPrivateFactoryMethod( createDecoder( JsonCreatorTester.BeanWithPrivateFactoryMethod.class ) );
    }

    @Test
    public void testEncodingBeanWithPropertiesOnlyPresentOnConstructor()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithPropertiesOnlyPresentOnConstructor( createEncoder( JsonCreatorTester
                .BeanWithPropertiesOnlyPresentOnConstructor.class ) );
    }

    @Test
    public void testDecodingBeanWithPropertiesOnlyPresentOnConstructor  ()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithPropertiesOnlyPresentOnConstructor( createDecoder( JsonCreatorTester.BeanWithPropertiesOnlyPresentOnConstructor.class ) );
    }
}
