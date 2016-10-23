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
 * Base exception for serialization process
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class JsonSerializationException extends JsonMappingException {

    /**
     * <p>Constructor for JsonSerializationException.</p>
     */
    public JsonSerializationException() {
    }

    /**
     * <p>Constructor for JsonSerializationException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public JsonSerializationException( String message ) {
        super( message );
    }

    /**
     * <p>Constructor for JsonSerializationException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause a {@link java.lang.Throwable} object.
     */
    public JsonSerializationException( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * <p>Constructor for JsonSerializationException.</p>
     *
     * @param cause a {@link java.lang.Throwable} object.
     */
    public JsonSerializationException( Throwable cause ) {
        super( cause );
    }
}
