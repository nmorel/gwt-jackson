/*
 * Copyright 2017 Nicolas Morel
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
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonSerializeUsingTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonSerializeUsingTester.JsonSerializeUsingBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonSerializeUsingGwtTest extends GwtJacksonTestCase {

    public interface JsonSerializeUsingBeanMapper extends ObjectWriter<JsonSerializeUsingBean>,
            ObjectWriterTester<JsonSerializeUsingBean> {

        static JsonSerializeUsingBeanMapper INSTANCE = GWT.create( JsonSerializeUsingBeanMapper.class );
    }

    public void testSerialize() {
        JsonSerializeUsingTester.INSTANCE.testSerialize( JsonSerializeUsingBeanMapper.INSTANCE );
    }
}
