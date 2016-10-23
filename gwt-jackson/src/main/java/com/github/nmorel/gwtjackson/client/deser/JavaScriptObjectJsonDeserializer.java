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

package com.github.nmorel.gwtjackson.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Default {@link JsonDeserializer} implementation for {@link JavaScriptObject}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class JavaScriptObjectJsonDeserializer<T extends JavaScriptObject> extends JsonDeserializer<T> {

    private static final JavaScriptObjectJsonDeserializer INSTANCE = new JavaScriptObjectJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link JavaScriptObjectJsonDeserializer}
     */
    public static JavaScriptObjectJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private JavaScriptObjectJsonDeserializer() { }

    /** {@inheritDoc} */
    @Override
    public T doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        return reader.nextJavaScriptObject( ctx.isUseSafeEval() ).cast();
    }
}
