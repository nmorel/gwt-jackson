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

package com.github.nmorel.gwtjackson.shared.annotations;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.model.HelloWorldWrapper;

/**
 * @author Nicolas Morel
 */
public final class JsonDeserializeUsingTester extends AbstractTester {

    public static class JsonDeserializeUsingBean {
        private HelloWorldWrapper wrapper;

        public JsonDeserializeUsingBean() {
        }

        public JsonDeserializeUsingBean( HelloWorldWrapper wrapper ) {
            this.wrapper = wrapper;
        }

        public HelloWorldWrapper getWrapper() {
            return wrapper;
        }

        public void setWrapper( HelloWorldWrapper wrapper ) {
            this.wrapper = wrapper;
        }
    }

    public static final JsonDeserializeUsingTester INSTANCE = new JsonDeserializeUsingTester();

    private JsonDeserializeUsingTester() {
    }

    public void testDeserialize( ObjectReaderTester<JsonDeserializeUsingBean> reader ) {
        String input = "{\"wrapper\":\"Hello World!\"}";

        JsonDeserializeUsingBean result = reader.read( input );

        assertEquals( "World", result.getWrapper().getName() );
        assertEquals( "Hello World!", result.getWrapper().toString() );
    }

}
