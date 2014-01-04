/*
 * Copyright 2013 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link ObjectMapper}. It delegates the serialization/deserialization to a serializer/deserializer.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractObjectMapper<T> implements ObjectMapper<T> {

    private final String rootName;

    protected AbstractObjectMapper( String rootName ) {
        this.rootName = rootName;
    }

    @Override
    public T read( String in ) throws JsonDeserializationException {
        return read( in, new JsonDeserializationContext.Builder().build() );
    }

    @Override
    public T read( String in, JsonDeserializationContext ctx ) throws JsonDeserializationException {
        JsonReader reader = ctx.newJsonReader( in );

        try {

            if ( ctx.isUnwrapRootValue() ) {

                if ( JsonToken.BEGIN_OBJECT != reader.peek() ) {
                    throw ctx.traceError( "Unwrap root value is enabled but the input is not a JSON Object", reader );
                }
                reader.beginObject();
                if ( JsonToken.END_OBJECT == reader.peek() ) {
                    throw ctx.traceError( "Unwrap root value is enabled but the JSON Object is empty", reader );
                }
                String name = reader.nextName();
                if ( !name.equals( rootName ) ) {
                    throw ctx.traceError( "Unwrap root value is enabled but the name '" + name + "' don't match the expected rootName " +
                            "'" + rootName + "'", reader );
                }
                T result = newDeserializer( ctx ).deserialize( reader, ctx );
                reader.endObject();
                return result;

            } else {

                return newDeserializer( ctx ).deserialize( reader, ctx );

            }

        } catch ( JsonDeserializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( e, reader );
        }
    }

    /**
     * Instantiates a new deserializer
     *
     * @param ctx Context of the current deserialization process
     *
     * @return a new deserializer
     */
    protected abstract JsonDeserializer<T> newDeserializer( JsonDeserializationContext ctx );

    @Override
    public String write( T value ) throws JsonSerializationException {
        return write( value, new JsonSerializationContext.Builder().build() );
    }

    @Override
    public String write( T value, JsonSerializationContext ctx ) throws JsonSerializationException {
        JsonWriter writer = ctx.newJsonWriter();
        try {
            if ( ctx.isWrapRootValue() ) {
                writer.beginObject();
                writer.name( rootName );
                newSerializer( ctx ).serialize( writer, value, ctx );
                writer.endObject();
            } else {
                newSerializer( ctx ).serialize( writer, value, ctx );
            }
            return writer.getOutput();
        } catch ( JsonSerializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e, writer );
        }
    }

    /**
     * Instantiates a new serializer
     *
     * @param ctx Context of the current serialization process
     *
     * @return a new serializer
     */
    protected abstract JsonSerializer<T> newSerializer( JsonSerializationContext ctx );
}
