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

package com.github.nmorel.gwtjackson.shared.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nmorel.gwtjackson.jackson.model.HelloWorldWrapperDeserializer;
import com.github.nmorel.gwtjackson.jackson.model.HelloWorldWrapperSerializer;

/**
 * Created by nicolasmorel on 23/05/2017.
 */
@JsonSerialize( using = HelloWorldWrapperSerializer.class )
@JsonDeserialize( using = HelloWorldWrapperDeserializer.class )
public class HelloWorldWrapper {

    public static HelloWorldWrapper fromString( String value ) {
        return new HelloWorldWrapper( value.substring( prefix.length(), value.length() - suffix.length() ) );
    }

    private static String prefix = "Hello ";

    private static String suffix = "!";

    private String name;

    public HelloWorldWrapper( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return prefix + name + suffix;
    }
}
