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

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base class for all the serializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 */
public abstract class JsonSerializer<T> {

    /**
     * Serializes an object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     *
     * @throws JsonSerializationException if an error occurs during the serialization
     */
    public void serialize( JsonWriter writer, T value, JsonSerializationContext ctx ) throws JsonSerializationException {
        serialize( writer, value, ctx, JsonSerializerParameters.DEFAULT );
    }

    /**
     * Serializes an object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     * @param params Parameters for this serialization
     *
     * @throws JsonSerializationException if an error occurs during the serialization
     */
    public void serialize( JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params ) throws
        JsonSerializationException {
        try {
            if ( null == value ) {
                serializeNullValue( writer, ctx, params );
                return;
            }
            doSerialize( writer, value, ctx, params );
        } catch ( IOException e ) {
            throw ctx.traceError( value, e, writer );
        } catch ( JsonSerializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e, writer );
        }
    }

    /**
     * Serialize the null value. This method allows children to override the default behaviour.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param ctx Context for the full serialization process
     * @param params Parameters for this serialization
     *
     * @throws IOException if an error occurs while writing the output
     */
    protected void serializeNullValue( JsonWriter writer, JsonSerializationContext ctx, JsonSerializerParameters params ) throws
        IOException {
        writer.nullValue();
    }

    /**
     * Serializes a non-null object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     * @param params Parameters for this serialization
     *
     * @throws IOException if an error occurs while writing the output
     */
    protected abstract void doSerialize( JsonWriter writer, @Nonnull T value, JsonSerializationContext ctx,
                                         JsonSerializerParameters params ) throws IOException;
}
