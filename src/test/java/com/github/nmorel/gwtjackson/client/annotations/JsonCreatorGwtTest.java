package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithConstructorAnnotated;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithDefaultConstructorPrivate;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithFactoryMethod;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithPrivateFactoryMethod;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithPropertiesOnlyPresentOnConstructor;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithoutDefaultConstructorAndNoAnnotation;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithoutDefaultConstructorAndPropertiesAnnotation;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonCreatorGwtTest extends GwtJacksonTestCase {

    public interface BeanWithDefaultConstructorPrivateMapper extends ObjectMapper<BeanWithDefaultConstructorPrivate>,
        JsonMapperTester<BeanWithDefaultConstructorPrivate> {

        static BeanWithDefaultConstructorPrivateMapper INSTANCE = GWT.create( BeanWithDefaultConstructorPrivateMapper.class );
    }

    public interface BeanWithoutDefaultConstructorAndNoAnnotationMapper extends
        ObjectMapper<BeanWithoutDefaultConstructorAndNoAnnotation>, JsonMapperTester<BeanWithoutDefaultConstructorAndNoAnnotation> {

        static BeanWithoutDefaultConstructorAndNoAnnotationMapper INSTANCE = GWT
            .create( BeanWithoutDefaultConstructorAndNoAnnotationMapper.class );
    }

    public interface BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper extends
        ObjectMapper<BeanWithoutDefaultConstructorAndPropertiesAnnotation>,
        JsonMapperTester<BeanWithoutDefaultConstructorAndPropertiesAnnotation> {

        static BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper INSTANCE = GWT
            .create( BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.class );
    }

    public interface BeanWithConstructorAnnotatedMapper extends ObjectMapper<BeanWithConstructorAnnotated>,
        JsonMapperTester<BeanWithConstructorAnnotated> {

        static BeanWithConstructorAnnotatedMapper INSTANCE = GWT.create( BeanWithConstructorAnnotatedMapper.class );
    }

    public interface BeanWithFactoryMethodMapper extends ObjectMapper<BeanWithFactoryMethod>, JsonMapperTester<BeanWithFactoryMethod> {

        static BeanWithFactoryMethodMapper INSTANCE = GWT.create( BeanWithFactoryMethodMapper.class );
    }

    public interface BeanWithPrivateFactoryMethodMapper extends ObjectMapper<BeanWithPrivateFactoryMethod>,
        JsonMapperTester<BeanWithPrivateFactoryMethod> {

        static BeanWithPrivateFactoryMethodMapper INSTANCE = GWT.create( BeanWithPrivateFactoryMethodMapper.class );
    }

    public interface BeanWithPropertiesOnlyPresentOnConstructorMapper extends ObjectMapper<BeanWithPropertiesOnlyPresentOnConstructor>,
        JsonMapperTester<BeanWithPropertiesOnlyPresentOnConstructor> {

        static BeanWithPropertiesOnlyPresentOnConstructorMapper INSTANCE = GWT
            .create( BeanWithPropertiesOnlyPresentOnConstructorMapper.class );
    }

    private JsonCreatorTester tester = JsonCreatorTester.INSTANCE;

    public void testEncodingBeanWithDefaultConstructorPrivate() {
        tester.testEncodingBeanWithDefaultConstructorPrivate( BeanWithDefaultConstructorPrivateMapper.INSTANCE );
    }

    public void testDecodingBeanWithDefaultConstructorPrivate() {
        tester.testDecodingBeanWithDefaultConstructorPrivate( BeanWithDefaultConstructorPrivateMapper.INSTANCE );
    }

    public void testEncodingBeanWithoutDefaultConstructorAndNoAnnotation() {
        tester.testEncodingBeanWithoutDefaultConstructorAndNoAnnotation( BeanWithoutDefaultConstructorAndNoAnnotationMapper.INSTANCE );
    }

    public void testDecodingBeanWithoutDefaultConstructorAndNoAnnotation() {
        tester.testDecodingBeanWithoutDefaultConstructorAndNoAnnotation( BeanWithoutDefaultConstructorAndNoAnnotationMapper.INSTANCE );
    }

    public void testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        tester
            .testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper
                .INSTANCE );
    }

    public void testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        tester
            .testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation( BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper
                .INSTANCE );
    }

    public void testDecodingBeanWithMissingRequiredPropertyInCreator() {
        tester.testDecodingBeanWithMissingRequiredPropertyInCreator( BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.INSTANCE );
    }

    public void testEncodingBeanWithConstructorAnnotated() {
        tester.testEncodingBeanWithConstructorAnnotated( BeanWithConstructorAnnotatedMapper.INSTANCE );
    }

    public void testDecodingBeanWithConstructorAnnotated() {
        tester.testDecodingBeanWithConstructorAnnotated( BeanWithConstructorAnnotatedMapper.INSTANCE );
    }

    public void testEncodingBeanWithFactoryMethod() {
        tester.testEncodingBeanWithFactoryMethod( BeanWithFactoryMethodMapper.INSTANCE );
    }

    public void testDecodingBeanWithFactoryMethod() {
        tester.testDecodingBeanWithFactoryMethod( BeanWithFactoryMethodMapper.INSTANCE );
    }

    public void testEncodingBeanWithPrivateFactoryMethod() {
        tester.testEncodingBeanWithPrivateFactoryMethod( BeanWithPrivateFactoryMethodMapper.INSTANCE );
    }

    public void testDecodingBeanWithPrivateFactoryMethod() {
        tester.testDecodingBeanWithPrivateFactoryMethod( BeanWithPrivateFactoryMethodMapper.INSTANCE );
    }

    public void testEncodingBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
            .testEncodingBeanWithPropertiesOnlyPresentOnConstructor( BeanWithPropertiesOnlyPresentOnConstructorMapper.INSTANCE );
    }

    public void testDecodingBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
            .testDecodingBeanWithPropertiesOnlyPresentOnConstructor( BeanWithPropertiesOnlyPresentOnConstructorMapper.INSTANCE );
    }
}
