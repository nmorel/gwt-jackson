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
                .testSerializeBeanWithDefaultConstructorPrivate( createWriter( JsonCreatorTester.BeanWithDefaultConstructorPrivate.class
                ) );
    }

    @Test
    public void testDeserializeBeanWithDefaultConstructorPrivate() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithDefaultConstructorPrivate( createReader( JsonCreatorTester.BeanWithDefaultConstructorPrivate
                        .class ) );
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
    @Ignore( "jackson doesn't support it yet" )
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

    @Test
    public void testDeserializeBeanWithBooleanFactoryDelegation() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithBooleanFactoryDelegation( createReader( JsonCreatorTester.BeanWithBooleanFactoryDelegation.class
                ) );
    }

    @Test
    public void testDeserializeBeanWithBooleanConstructorDelegation() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithBooleanConstructorDelegation( createReader( JsonCreatorTester
                        .BeanWithBooleanConstructorDelegation.class ) );
    }

    @Test
    public void testBeanWithBooleanConstructorDelegationAndTypeInfo() {
        JsonCreatorTester.INSTANCE
                .testBeanWithBooleanConstructorDelegationAndTypeInfo( createMapper( JsonCreatorTester
                        .BeanWithBooleanConstructorDelegationAndTypeInfo.class ) );
    }

    @Test
    public void testDeserializeBeanWithObjectConstructorDelegation() {
        JsonCreatorTester.INSTANCE
                .testDeserializeBeanWithObjectConstructorDelegation( createReader( JsonCreatorTester
                        .BeanWithObjectConstructorDelegation.class ) );
    }
}
