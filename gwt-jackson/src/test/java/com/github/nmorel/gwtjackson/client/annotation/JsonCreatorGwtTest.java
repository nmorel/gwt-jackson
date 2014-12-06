/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.annotation;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithBooleanConstructorDelegation;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithBooleanConstructorDelegationAndTypeInfo;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithBooleanFactoryDelegation;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithConstructorAnnotated;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithDefaultConstructorPrivate;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithFactoryMethod;
import com.github.nmorel.gwtjackson.shared.annotations.JsonCreatorTester.BeanWithObjectConstructorDelegation;
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
            ObjectMapperTester<BeanWithDefaultConstructorPrivate> {

        static BeanWithDefaultConstructorPrivateMapper INSTANCE = GWT.create( BeanWithDefaultConstructorPrivateMapper.class );
    }

    public interface BeanWithoutDefaultConstructorAndNoAnnotationMapper extends
            ObjectMapper<BeanWithoutDefaultConstructorAndNoAnnotation>, ObjectMapperTester<BeanWithoutDefaultConstructorAndNoAnnotation> {

        static BeanWithoutDefaultConstructorAndNoAnnotationMapper INSTANCE = GWT
                .create( BeanWithoutDefaultConstructorAndNoAnnotationMapper.class );
    }

    public interface BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper extends
            ObjectMapper<BeanWithoutDefaultConstructorAndPropertiesAnnotation>,
            ObjectMapperTester<BeanWithoutDefaultConstructorAndPropertiesAnnotation> {

        static BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper INSTANCE = GWT
                .create( BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.class );
    }

    public interface BeanWithConstructorAnnotatedMapper extends ObjectMapper<BeanWithConstructorAnnotated>,
            ObjectMapperTester<BeanWithConstructorAnnotated> {

        static BeanWithConstructorAnnotatedMapper INSTANCE = GWT.create( BeanWithConstructorAnnotatedMapper.class );
    }

    public interface BeanWithFactoryMethodMapper extends ObjectMapper<BeanWithFactoryMethod>, ObjectMapperTester<BeanWithFactoryMethod> {

        static BeanWithFactoryMethodMapper INSTANCE = GWT.create( BeanWithFactoryMethodMapper.class );
    }

    public interface BeanWithPrivateFactoryMethodMapper extends ObjectMapper<BeanWithPrivateFactoryMethod>,
            ObjectMapperTester<BeanWithPrivateFactoryMethod> {

        static BeanWithPrivateFactoryMethodMapper INSTANCE = GWT.create( BeanWithPrivateFactoryMethodMapper.class );
    }

    public interface BeanWithPropertiesOnlyPresentOnConstructorMapper extends ObjectMapper<BeanWithPropertiesOnlyPresentOnConstructor>,
            ObjectMapperTester<BeanWithPropertiesOnlyPresentOnConstructor> {

        static BeanWithPropertiesOnlyPresentOnConstructorMapper INSTANCE = GWT
                .create( BeanWithPropertiesOnlyPresentOnConstructorMapper.class );
    }

    private JsonCreatorTester tester = JsonCreatorTester.INSTANCE;

    public void testSerializeBeanWithDefaultConstructorPrivate() {
        tester.testSerializeBeanWithDefaultConstructorPrivate( BeanWithDefaultConstructorPrivateMapper.INSTANCE );
    }

    public void testDeserializeBeanWithDefaultConstructorPrivate() {
        tester.testDeserializeBeanWithDefaultConstructorPrivate( BeanWithDefaultConstructorPrivateMapper.INSTANCE );
    }

    public void testSerializeBeanWithoutDefaultConstructorAndNoAnnotation() {
        tester.testSerializeBeanWithoutDefaultConstructorAndNoAnnotation( BeanWithoutDefaultConstructorAndNoAnnotationMapper.INSTANCE );
    }

    public void testDeserializeBeanWithoutDefaultConstructorAndNoAnnotation() {
        tester.testDeserializeBeanWithoutDefaultConstructorAndNoAnnotation( BeanWithoutDefaultConstructorAndNoAnnotationMapper.INSTANCE );
    }

    public void testSerializeBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        tester.testSerializeBeanWithoutDefaultConstructorAndPropertiesAnnotation(
                BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.INSTANCE );
    }

    public void testDeserializeBeanWithoutDefaultConstructorAndPropertiesAnnotation() {
        tester.testDeserializeBeanWithoutDefaultConstructorAndPropertiesAnnotation(
                BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper.INSTANCE );
    }

    public void testDeserializeBeanWithMissingRequiredPropertyInCreator() {
        tester.testDeserializeBeanWithMissingRequiredPropertyInCreator( BeanWithoutDefaultConstructorAndPropertiesAnnotationMapper
                .INSTANCE );
    }

    public void testSerializeBeanWithConstructorAnnotated() {
        tester.testSerializeBeanWithConstructorAnnotated( BeanWithConstructorAnnotatedMapper.INSTANCE );
    }

    public void testDeserializeBeanWithConstructorAnnotated() {
        tester.testDeserializeBeanWithConstructorAnnotated( BeanWithConstructorAnnotatedMapper.INSTANCE );
    }

    public void testSerializeBeanWithFactoryMethod() {
        tester.testSerializeBeanWithFactoryMethod( BeanWithFactoryMethodMapper.INSTANCE );
    }

    public void testDeserializeBeanWithFactoryMethod() {
        tester.testDeserializeBeanWithFactoryMethod( BeanWithFactoryMethodMapper.INSTANCE );
    }

    public void testSerializeBeanWithPrivateFactoryMethod() {
        tester.testSerializeBeanWithPrivateFactoryMethod( BeanWithPrivateFactoryMethodMapper.INSTANCE );
    }

    public void testDeserializeBeanWithPrivateFactoryMethod() {
        tester.testDeserializeBeanWithPrivateFactoryMethod( BeanWithPrivateFactoryMethodMapper.INSTANCE );
    }

    public void testSerializeBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
                .testSerializeBeanWithPropertiesOnlyPresentOnConstructor( BeanWithPropertiesOnlyPresentOnConstructorMapper.INSTANCE );
    }

    public void testDeserializeBeanWithPropertiesOnlyPresentOnConstructor() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithPropertiesOnlyPresentOnConstructor( BeanWithPropertiesOnlyPresentOnConstructorMapper.INSTANCE );
    }

    /* ################################ */

    public interface BeanWithBooleanFactoryDelegationReader extends ObjectReader<BeanWithBooleanFactoryDelegation>, ObjectReaderTester<BeanWithBooleanFactoryDelegation> {

        static BeanWithBooleanFactoryDelegationReader INSTANCE = GWT
                .create( BeanWithBooleanFactoryDelegationReader.class );
    }

    public void testDeserializeBeanWithBooleanFactoryDelegation() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithBooleanFactoryDelegation( BeanWithBooleanFactoryDelegationReader.INSTANCE );
    }

    /* ################################ */

    public interface BeanWithBooleanConstructorDelegationReader extends ObjectReader<BeanWithBooleanConstructorDelegation>,
            ObjectReaderTester<BeanWithBooleanConstructorDelegation> {

        static BeanWithBooleanConstructorDelegationReader INSTANCE = GWT
                .create( BeanWithBooleanConstructorDelegationReader.class );
    }

    public void testDeserializeBeanWithBooleanConstructorDelegation() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithBooleanConstructorDelegation( BeanWithBooleanConstructorDelegationReader.INSTANCE );
    }

    /* ################################ */

    public interface BeanWithBooleanConstructorDelegationAndTypeInfoMapper extends ObjectMapper<BeanWithBooleanConstructorDelegationAndTypeInfo>,
            ObjectMapperTester<BeanWithBooleanConstructorDelegationAndTypeInfo> {

        static BeanWithBooleanConstructorDelegationAndTypeInfoMapper INSTANCE = GWT
                .create( BeanWithBooleanConstructorDelegationAndTypeInfoMapper.class );
    }

    public void testBeanWithBooleanConstructorDelegationAndTypeInfo() {
        JsonCreatorTester.INSTANCE
                .testBeanWithBooleanConstructorDelegationAndTypeInfo( BeanWithBooleanConstructorDelegationAndTypeInfoMapper.INSTANCE );
    }

    /* ################################ */

    public interface BeanWithObjectConstructorDelegationReader extends ObjectReader<BeanWithObjectConstructorDelegation>,
            ObjectReaderTester<BeanWithObjectConstructorDelegation> {

        static BeanWithObjectConstructorDelegationReader INSTANCE = GWT
                .create( BeanWithObjectConstructorDelegationReader.class );
    }

    public void testDeserializeBeanWithObjectConstructorDelegation() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithObjectConstructorDelegation( BeanWithObjectConstructorDelegationReader.INSTANCE );
    }
}
