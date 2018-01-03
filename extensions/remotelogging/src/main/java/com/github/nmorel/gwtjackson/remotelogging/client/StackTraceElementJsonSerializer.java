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

package com.github.nmorel.gwtjackson.remotelogging.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

public class StackTraceElementJsonSerializer extends JsonSerializer<StackTraceElement> {

    private static final StackTraceElementJsonSerializer INSTANCE = new StackTraceElementJsonSerializer();

    private static final String METHOD_NAME = "methodName";

    private static final String FILE_NAME = "fileName";

    private static final String LINE_NUMBER = "lineNumber";

    private static final String CLASS_NAME = "className";

    public static StackTraceElementJsonSerializer getInstance() {
        return INSTANCE;
    }

    private StackTraceElementJsonSerializer() { }

    @Override
    protected void doSerialize( JsonWriter writer, StackTraceElement stackTraceElement, JsonSerializationContext ctx,
                                JsonSerializerParameters params ) {
        writer.beginObject();
        if ( stackTraceElement.getMethodName() != null || ctx.isSerializeNulls() ) {
            writer.name( METHOD_NAME );
            writer.value( stackTraceElement.getMethodName() );
        }
        if ( stackTraceElement.getFileName() != null || ctx.isSerializeNulls() ) {
            writer.name( FILE_NAME );
            writer.value( stackTraceElement.getFileName() );
        }
        writer.name( LINE_NUMBER );
        writer.value( stackTraceElement.getLineNumber() );
        if ( stackTraceElement.getClassName() != null || ctx.isSerializeNulls() ) {
            writer.name( CLASS_NAME );
            writer.value( stackTraceElement.getClassName() );
        }
        writer.endObject();
    }
}