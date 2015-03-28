/*
 * Copyright 2015 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.mapper.AutoDetectionTester;
import com.github.nmorel.gwtjackson.shared.mapper.AutoDetectionTester.AutoDetectionBeanBuilder;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class AutoDetectionGwtTest extends GwtJacksonTestCase {

    public interface AutoDetectionBeanBuilderMapper extends ObjectMapper<AutoDetectionBeanBuilder>,
            ObjectMapperTester<AutoDetectionBeanBuilder> {

        static AutoDetectionBeanBuilderMapper INSTANCE = GWT.create( AutoDetectionBeanBuilderMapper.class );
    }

    public void test() {
        AutoDetectionTester.INSTANCE.test( createMapper( AutoDetectionBeanBuilderMapper.INSTANCE ) );
    }
}
