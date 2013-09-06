package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonCreatorGwtTest extends GwtJacksonTestCase
{
    public interface BeanWithDefaultConstructorPrivateMapper extends JsonMapper<JsonCreatorTester.BeanWithDefaultConstructorPrivate>
    {
        static BeanWithDefaultConstructorPrivateMapper INSTANCE = GWT.create( BeanWithDefaultConstructorPrivateMapper.class );
    }

    public interface BeanWithoutDefaultConstructorAndNoAnnotationMapper extends JsonMapper<JsonCreatorTester
        .BeanWithoutDefaultConstructorAndNoAnnotation>
    {
        static BeanWithoutDefaultConstructorAndNoAnnotationMapper INSTANCE = GWT
            .create( BeanWithoutDefaultConstructorAndNoAnnotationMapper.class );
    }

    public interface BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper extends JsonMapper<JsonCreatorTester
        .BeanWithoutDefaultConstructorAndPropertiesAnnotation>
    {
        static BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper INSTANCE = GWT
            .create( BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.class );
    }

    public interface BeanWithConstructorAnnotatedMapper extends JsonMapper<JsonCreatorTester.BeanWithConstructorAnnotated>
    {
        static BeanWithConstructorAnnotatedMapper INSTANCE = GWT.create( BeanWithConstructorAnnotatedMapper.class );
    }

    public interface BeanWithFactoryMethodMapper extends JsonMapper<JsonCreatorTester.BeanWithFactoryMethod>
    {
        static BeanWithFactoryMethodMapper INSTANCE = GWT.create( BeanWithFactoryMethodMapper.class );
    }

    public interface BeanWithPrivateFactoryMethodMapper extends JsonMapper<JsonCreatorTester.BeanWithPrivateFactoryMethod>
    {
        static BeanWithPrivateFactoryMethodMapper INSTANCE = GWT.create( BeanWithPrivateFactoryMethodMapper.class );
    }

    public interface BeanWithPropertiesOnlyPresentOnConstructorMapper extends JsonMapper<JsonCreatorTester
        .BeanWithPropertiesOnlyPresentOnConstructor>
    {
        static BeanWithPropertiesOnlyPresentOnConstructorMapper INSTANCE = GWT
            .create( BeanWithPropertiesOnlyPresentOnConstructorMapper.class );
    }

    private JsonCreatorTester tester = JsonCreatorTester.INSTANCE;

    public void testEncodingBeanWithDefaultConstructorPrivate()
    {
        tester.testEncodingBeanWithDefaultConstructorPrivate( createEncoder( BeanWithDefaultConstructorPrivateMapper.INSTANCE ) );
    }

    public void testDecodingBeanWithDefaultConstructorPrivate()
    {
        tester.testDecodingBeanWithDefaultConstructorPrivate( createDecoder( BeanWithDefaultConstructorPrivateMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithoutDefaultConstructorAndNoAnnotation()
    {
        tester
            .testEncodingBeanWithoutDefaultConstructorAndNoAnnotation( createEncoder( BeanWithoutDefaultConstructorAndNoAnnotationMapper
                .INSTANCE ) );
    }

    public void testDecodingBeanWithoutDefaultConstructorAndNoAnnotation()
    {
        tester
            .testDecodingBeanWithoutDefaultConstructorAndNoAnnotation( createDecoder( BeanWithoutDefaultConstructorAndNoAnnotationMapper
                .INSTANCE ) );
    }

    public void testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation()
    {
        tester
            .testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( createEncoder(
                BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.INSTANCE ) );
    }

    public void testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation()
    {
        tester
            .testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( createDecoder(
                BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithConstructorAnnotated()
    {
        tester.testEncodingBeanWithConstructorAnnotated( createEncoder( BeanWithConstructorAnnotatedMapper.INSTANCE ) );
    }

    public void testDecodingBeanWithConstructorAnnotated()
    {
        tester.testDecodingBeanWithConstructorAnnotated( createDecoder( BeanWithConstructorAnnotatedMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithFactoryMethod()
    {
        tester.testEncodingBeanWithFactoryMethod( createEncoder( BeanWithFactoryMethodMapper.INSTANCE ) );
    }

    public void testDecodingBeanWithFactoryMethod()
    {
        tester.testDecodingBeanWithFactoryMethod( createDecoder( BeanWithFactoryMethodMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithPrivateFactoryMethod()
    {
        tester.testEncodingBeanWithPrivateFactoryMethod( createEncoder( BeanWithPrivateFactoryMethodMapper.INSTANCE ) );
    }

    public void testDecodingBeanWithPrivateFactoryMethod()
    {
        tester.testDecodingBeanWithPrivateFactoryMethod( createDecoder( BeanWithPrivateFactoryMethodMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithPropertiesOnlyPresentOnConstructor()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithPropertiesOnlyPresentOnConstructor( createEncoder( BeanWithPropertiesOnlyPresentOnConstructorMapper
                .INSTANCE ) );
    }

    public void testDecodingBeanWithPropertiesOnlyPresentOnConstructor()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithPropertiesOnlyPresentOnConstructor( createDecoder( BeanWithPropertiesOnlyPresentOnConstructorMapper.INSTANCE ) );
    }
}
