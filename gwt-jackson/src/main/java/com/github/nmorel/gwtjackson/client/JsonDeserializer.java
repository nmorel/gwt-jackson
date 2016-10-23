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
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base class for all the deserializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class JsonDeserializer<T> {

    /**
     * Deserializes a JSON input into an object.
     *
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full deserialization process
     * @return the deserialized object
     * @throws com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException if an error occurs during the deserialization
     */
    public T deserialize( JsonReader reader, JsonDeserializationContext ctx ) throws JsonDeserializationException {
        return deserialize( reader, ctx, JsonDeserializerParameters.DEFAULT );
    }

    /**
     * Deserializes a JSON input into an object.
     *
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full deserialization process
     * @param params Parameters for this deserialization
     * @return the deserialized object
     * @throws com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException if an error occurs during the deserialization
     */
    public T deserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
            JsonDeserializationException {
        if ( JsonToken.NULL.equals( reader.peek() ) ) {
            return deserializeNullValue( reader, ctx, params );
        }
        return doDeserialize( reader, ctx, params );
    }

    /**
     * Deserialize the null value. This method allows children to override the default behaviour.
     *
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full deserialization process
     * @param params Parameters for this deserialization
     * @return the deserialized object
     */
    protected T deserializeNullValue( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        reader.skipValue();
        return null;
    }

    /**
     * Deserializes a non-null JSON input into an object.
     *
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full deserialization process
     * @param params Parameters for this deserialization
     * @return the deserialized object
     */
    protected abstract T doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params );

    /**
     * Set the back reference.
     *
     * @param referenceName name of the reference
     * @param referenceName name of the reference
     * @param referenceName name of the reference
     * @param referenceName name of the reference
     * @param referenceName name of the reference
     * @param referenceName name of the reference
     * @param reference reference to set
     * @param value value to set the reference to.
     * @param ctx Context for the full deserialization process
     * @see com.fasterxml.jackson.annotation.JsonBackReference
     */
    public void setBackReference( String referenceName, Object reference, T value, JsonDeserializationContext ctx ) {
        throw new JsonDeserializationException( "Cannot set a back reference to the type managed by this deserializer" );
    }
}
