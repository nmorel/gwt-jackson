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

package com.github.nmorel.gwtjackson.jackson.model;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.shared.model.HelloWorldWrapper;

/**
 * Created by nicolasmorel on 23/05/2017.
 */
public class HelloWorldWrapperDeserializer extends JsonDeserializer<HelloWorldWrapper> {

    private static final HelloWorldWrapperDeserializer INSTANCE = new HelloWorldWrapperDeserializer();

    public static HelloWorldWrapperDeserializer getInstance() {
        return INSTANCE;
    }

    private HelloWorldWrapperDeserializer() { }

    @Override
    protected HelloWorldWrapper doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        return HelloWorldWrapper.fromString( reader.nextString() );
    }
}
