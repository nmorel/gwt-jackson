package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester;
import com.google.gwt.core.client.GWT;
import org.junit.Test;

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

    @Test
    public void testDecodingBeanWithDefaultConstructorPrivate()
    {
        tester.testDecodingBeanWithDefaultConstructorPrivate( createDecoder( BeanWithDefaultConstructorPrivateMapper.INSTANCE ) );
    }

    @Test
    public void testEncodingBeanWithoutDefaultConstructorAndNoAnnotation()
    {
        tester
            .testEncodingBeanWithoutDefaultConstructorAndNoAnnotation( createEncoder( BeanWithoutDefaultConstructorAndNoAnnotationMapper
                .INSTANCE ) );
    }

    @Test
    public void testDecodingBeanWithoutDefaultConstructorAndNoAnnotation()
    {
        tester
            .testDecodingBeanWithoutDefaultConstructorAndNoAnnotation( createDecoder( BeanWithoutDefaultConstructorAndNoAnnotationMapper
                .INSTANCE ) );
    }

    @Test
    public void testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation()
    {
        tester
            .testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( createEncoder(
                BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.INSTANCE ) );
    }

    @Test
    public void testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation()
    {
        tester
            .testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( createDecoder(
                BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.INSTANCE ) );
    }

    @Test
    public void testEncodingBeanWithConstructorAnnotated()
    {
        tester.testEncodingBeanWithConstructorAnnotated( createEncoder( BeanWithConstructorAnnotatedMapper.INSTANCE ) );
    }

    @Test
    public void testDecodingBeanWithConstructorAnnotated()
    {
        tester.testDecodingBeanWithConstructorAnnotated( createDecoder( BeanWithConstructorAnnotatedMapper.INSTANCE ) );
    }

    @Test
    public void testEncodingBeanWithFactoryMethod()
    {
        tester.testEncodingBeanWithFactoryMethod( createEncoder( BeanWithFactoryMethodMapper.INSTANCE ) );
    }

    @Test
    public void testDecodingBeanWithFactoryMethod()
    {
        tester.testDecodingBeanWithFactoryMethod( createDecoder( BeanWithFactoryMethodMapper.INSTANCE ) );
    }

    @Test
    public void testEncodingBeanWithPrivateFactoryMethod()
    {
        tester.testEncodingBeanWithPrivateFactoryMethod( createEncoder( BeanWithPrivateFactoryMethodMapper.INSTANCE ) );
    }

    @Test
    public void testDecodingBeanWithPrivateFactoryMethod()
    {
        tester.testDecodingBeanWithPrivateFactoryMethod( createDecoder( BeanWithPrivateFactoryMethodMapper.INSTANCE ) );
    }

    @Test
    public void testEncodingBeanWithPropertiesOnlyPresentOnConstructor()
    {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithPropertiesOnlyPresentOnConstructor( createEncoder( BeanWithPropertiesOnlyPresentOnConstructorMapper
                .INSTANCE ) );
    }

    @Test
    public void testDecodingBeanWithPropertiesOnlyPresentOnConstructor()
    {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithPropertiesOnlyPresentOnConstructor( createDecoder( BeanWithPropertiesOnlyPresentOnConstructorMapper.INSTANCE ) );
    }
}
