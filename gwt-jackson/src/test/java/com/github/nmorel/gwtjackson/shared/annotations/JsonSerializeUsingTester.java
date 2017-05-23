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
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.model.HelloWorldWrapper;

/**
 * @author Nicolas Morel
 */
public final class JsonSerializeUsingTester extends AbstractTester {

    public static class JsonSerializeUsingBean {
        private HelloWorldWrapper wrapper;

        public JsonSerializeUsingBean() {
        }

        public JsonSerializeUsingBean( HelloWorldWrapper wrapper ) {
            this.wrapper = wrapper;
        }

        public HelloWorldWrapper getWrapper() {
            return wrapper;
        }

        public void setWrapper( HelloWorldWrapper wrapper ) {
            this.wrapper = wrapper;
        }
    }

    public static final JsonSerializeUsingTester INSTANCE = new JsonSerializeUsingTester();

    private JsonSerializeUsingTester() {
    }

    public void testSerialize( ObjectWriterTester<JsonSerializeUsingBean> writer ) {
        JsonSerializeUsingBean input = new JsonSerializeUsingBean( new HelloWorldWrapper( "World" ) );

        String result = writer.write( input );

        assertEquals( "{\"wrapper\":\"Hello World!\"}", result );
    }

}
