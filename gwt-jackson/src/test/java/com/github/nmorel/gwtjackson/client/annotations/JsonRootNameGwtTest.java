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

package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.Bean;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.RootBeanWithEmpty;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.RootBeanWithNoAnnotation;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonRootNameGwtTest extends GwtJacksonTestCase {

    public interface BeanMapper extends ObjectMapper<Bean> {

        static BeanMapper INSTANCE = GWT.create( BeanMapper.class );
    }

    public interface RootBeanWithEmptyMapper extends ObjectMapper<RootBeanWithEmpty> {

        static RootBeanWithEmptyMapper INSTANCE = GWT.create( RootBeanWithEmptyMapper.class );
    }

    public interface RootBeanWithNoAnnotationMapper extends ObjectMapper<RootBeanWithNoAnnotation> {

        static RootBeanWithNoAnnotationMapper INSTANCE = GWT.create( RootBeanWithNoAnnotationMapper.class );
    }

    private JsonRootNameTester tester = JsonRootNameTester.INSTANCE;

    public void testRootName() {
        tester.testRootName( createRootMapper( BeanMapper.INSTANCE ) );
    }

    public void testRootNameEmpty() {
        tester.testRootNameEmpty( createRootMapper( RootBeanWithEmptyMapper.INSTANCE ) );
    }

    public void testRootNameNoAnnotation() {
        tester.testRootNameNoAnnotation( createRootMapper( RootBeanWithNoAnnotationMapper.INSTANCE ) );
    }

    public void testUnwrappingFailing() {
        tester.testUnwrappingFailing( createRootMapper( BeanMapper.INSTANCE ) );
    }

    protected <T> ObjectMapperTester<T> createRootMapper( final ObjectMapper<T> mapper ) {
        return createMapper( mapper, new JsonDeserializationContext.Builder().unwrapRootValue( true )
                .build(), new JsonSerializationContext.Builder().wrapRootValue( true ).build() );
    }
}
