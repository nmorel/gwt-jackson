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

package com.github.nmorel.gwtjackson.client.exception;

/**
 * Base exception for deserialization process
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class JsonDeserializationException extends JsonMappingException {

    /**
     * <p>Constructor for JsonDeserializationException.</p>
     */
    public JsonDeserializationException() {
    }

    /**
     * <p>Constructor for JsonDeserializationException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public JsonDeserializationException( String message ) {
        super( message );
    }

    /**
     * <p>Constructor for JsonDeserializationException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause a {@link java.lang.Throwable} object.
     */
    public JsonDeserializationException( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * <p>Constructor for JsonDeserializationException.</p>
     *
     * @param cause a {@link java.lang.Throwable} object.
     */
    public JsonDeserializationException( Throwable cause ) {
        super( cause );
    }
}
