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

package com.github.nmorel.gwtjackson.client.ser;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Default {@link JsonSerializer} implementation for {@link JavaScriptObject}.
 *
 * @author Nicolas Morel
 */
public class JavaScriptObjectJsonSerializer<T extends JavaScriptObject> extends JsonSerializer<T> {

    private static final JavaScriptObjectJsonSerializer INSTANCE = new JavaScriptObjectJsonSerializer();

    /**
     * @return an instance of {@link JavaScriptObjectJsonSerializer}
     */
    public static JavaScriptObjectJsonSerializer getInstance() {
        return INSTANCE;
    }

    private JavaScriptObjectJsonSerializer() { }

    @Override
    public void doSerialize( JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.value( value );
    }
}
