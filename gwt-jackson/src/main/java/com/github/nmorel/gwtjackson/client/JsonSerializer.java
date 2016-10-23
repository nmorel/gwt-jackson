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
        if ( null != params.getInclude() ) {
            switch ( params.getInclude() ) {
                case ALWAYS:
                    if ( null == value ) {
                        serializeNullValue( writer, ctx, params );
                    } else {
                        doSerialize( writer, value, ctx, params );
                    }
                    return;
                case NON_DEFAULT:
                    if ( isDefault( value ) ) {
                        writer.cancelName();
                    } else {
                        doSerialize( writer, value, ctx, params );
                    }
                    return;
                case NON_EMPTY:
                    if ( isEmpty( value ) ) {
                        writer.cancelName();
                    } else {
                        doSerialize( writer, value, ctx, params );
                    }
                    return;
                case NON_NULL:
                    if ( null == value ) {
                        writer.cancelName();
                    } else {
                        doSerialize( writer, value, ctx, params );
                    }
                    return;
                case NON_ABSENT:
                    if ( isAbsent( value ) ) {
                        writer.cancelName();
                    } else {
                        doSerialize( writer, value, ctx, params );
                    }
                    return;
            }
        }

        if ( null == value ) {
            if ( ctx.isSerializeNulls() ) {
                serializeNullValue( writer, ctx, params );
            } else {
                writer.cancelName();
            }
        } else {
            doSerialize( writer, value, ctx, params );
        }
    }

    /**
     * Serialize the null value. This method allows children to override the default behaviour.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param ctx Context for the full serialization process
     * @param params Parameters for this serialization
     */
    protected void serializeNullValue( JsonWriter writer, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.nullValue();
    }

    /**
     * @return true if the value corresponds to the default one
     */
    protected boolean isDefault( T value ) {
        return isEmpty( value );
    }

    /**
     * @return true if the value is empty
     */
    protected boolean isEmpty( T value ) {
        return null == value;
    }

    /**
     * @return true if the value is absent
     */
    protected boolean isAbsent( T value ) {
        return null == value;
    }

    /**
     * Serializes a non-null object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     * @param params Parameters for this serialization
     */
    protected abstract void doSerialize( JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters
            params );
}
