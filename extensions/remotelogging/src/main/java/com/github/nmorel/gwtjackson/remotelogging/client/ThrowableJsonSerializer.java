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

public class ThrowableJsonSerializer extends JsonSerializer<Throwable> {

    private static final ThrowableJsonSerializer INSTANCE = new ThrowableJsonSerializer();

    private static final String JSON_TYPE_INFO = "@class";

    private static final String CAUSE = "cause";

    private static final String STACK_TRACE = "stackTrace";

    private static final String MESSAGE = "message";

    private static final String LOCALIZED_MESSAGE = "localizedMessage";

    public static ThrowableJsonSerializer getInstance() {
        return INSTANCE;
    }

    private ThrowableJsonSerializer() { }

    @Override
    protected void doSerialize( JsonWriter writer, Throwable throwable, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.beginObject();
        writer.name( JSON_TYPE_INFO );
        writer.value( throwable.getClass().getName() );

        writer.name( CAUSE );
        if ( throwable.getCause() != null ) {
            serialize( writer, throwable.getCause(), ctx, params );
        } else {
            writer.nullValue();
        }

        writer.name( STACK_TRACE );
        writer.beginArray();
        for ( StackTraceElement stackTraceElement : throwable.getStackTrace() ) {
            StackTraceElementJsonSerializer.getInstance().doSerialize( writer, stackTraceElement, ctx, params );
        }
        writer.endArray();

        writer.name( MESSAGE );
        writer.value( throwable.getMessage() );

        writer.name( LOCALIZED_MESSAGE );
        writer.value( throwable.getLocalizedMessage() );

        writer.endObject();
    }
}