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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.StringReader;

/**
 * Context for the deserialization process.
 *
 * @author Nicolas Morel
 */
public class JsonDeserializationContext extends JsonMappingContext {

    public static class Builder {

        private boolean failOnUnknownProperties = true;

        private boolean unwrapRootValue = false;

        private boolean acceptSingleValueAsArray = false;

        /**
         * Determines whether encountering of unknown
         * properties (ones that do not map to a property, and there is
         * no "any setter" or handler that can handle it)
         * should result in a failure (by throwing a
         * {@link JsonDeserializationException}) or not.
         * This setting only takes effect after all other handling
         * methods for unknown properties have been tried, and
         * property remains unhandled.
         * <p/>
         * Feature is enabled by default (meaning that a
         * {@link JsonDeserializationException} will be thrown if an unknown property
         * is encountered).
         */
        public Builder failOnUnknownProperties( boolean failOnUnknownProperties ) {
            this.failOnUnknownProperties = failOnUnknownProperties;
            return this;
        }

        /**
         * Feature to allow "unwrapping" root-level JSON value, to match setting of
         * {@link JsonSerializationContext.Builder#wrapRootValue(boolean)} used for serialization.
         * Will verify that the root JSON value is a JSON Object, and that it has
         * a single property with expected root name. If not, a
         * {@link JsonDeserializationException} is thrown; otherwise value of the wrapped property
         * will be deserialized as if it was the root value.
         * <p/>
         * Feature is disabled by default.
         */
        public Builder unwrapRootValue( boolean unwrapRootValue ) {
            this.unwrapRootValue = unwrapRootValue;
            return this;
        }

        /**
         * Feature that determines whether it is acceptable to coerce non-array
         * (in JSON) values to work with Java collection (arrays, java.util.Collection)
         * types. If enabled, collection deserializers will try to handle non-array
         * values as if they had "implicit" surrounding JSON array.
         * This feature is meant to be used for compatibility/interoperability reasons,
         * to work with packages (such as XML-to-JSON converters) that leave out JSON
         * array in cases where there is just a single element in array.
         * <p/>
         * Feature is disabled by default.
         */
        public Builder acceptSingleValueAsArray( boolean acceptSingleValueAsArray ) {
            this.acceptSingleValueAsArray = acceptSingleValueAsArray;
            return this;
        }

        public JsonDeserializationContext build() {
            return new JsonDeserializationContext( failOnUnknownProperties, unwrapRootValue, acceptSingleValueAsArray );
        }
    }

    private static final Logger logger = Logger.getLogger( "JsonDeserialization" );

    private Map<IdKey, Object> idToObject;

    /*
     * Deserialization options
     */
    private final boolean failOnUnknownProperties;

    private final boolean unwrapRootValue;

    private final boolean acceptSingleValueAsArray;

    private JsonDeserializationContext( boolean failOnUnknownProperties, boolean unwrapRootValue, boolean acceptSingleValueAsArray ) {
        this.failOnUnknownProperties = failOnUnknownProperties;
        this.unwrapRootValue = unwrapRootValue;
        this.acceptSingleValueAsArray = acceptSingleValueAsArray;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * @see Builder#failOnUnknownProperties(boolean)
     */
    public boolean isFailOnUnknownProperties() {
        return failOnUnknownProperties;
    }

    /**
     * @see Builder#unwrapRootValue(boolean)
     */
    public boolean isUnwrapRootValue() {
        return unwrapRootValue;
    }

    /**
     * @see Builder#acceptSingleValueAsArray(boolean)
     */
    public boolean isAcceptSingleValueAsArray() {
        return acceptSingleValueAsArray;
    }

    public JsonReader newJsonReader( String input ) {
        JsonReader reader = new JsonReader( new StringReader( input ) );
        reader.setLenient( true );
        return reader;
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param message error message
     *
     * @return a {@link JsonDeserializationException} with the given message
     */
    public JsonDeserializationException traceError( String message ) {
        return traceError( message, null );
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param message error message
     * @param reader current reader
     *
     * @return a {@link JsonDeserializationException} with the given message
     */
    public JsonDeserializationException traceError( String message, JsonReader reader ) {
        getLogger().log( Level.SEVERE, message );
        traceReaderInfo( reader );
        return new JsonDeserializationException( message );
    }

    /**
     * Trace an error and returns a corresponding exception.
     *
     * @param cause cause of the error
     *
     * @return a {@link JsonDeserializationException} with the given cause
     */
    public JsonDeserializationException traceError( Exception cause ) {
        return traceError( cause, null );
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param cause cause of the error
     * @param reader current reader
     *
     * @return a {@link JsonDeserializationException} with the given cause
     */
    public JsonDeserializationException traceError( Exception cause, JsonReader reader ) {
        getLogger().log( Level.SEVERE, "Error during deserialization", cause );
        traceReaderInfo( reader );
        return new JsonDeserializationException( cause );
    }

    /**
     * Trace the current reader state
     */
    private void traceReaderInfo( JsonReader reader ) {
        if ( null != reader && getLogger().isLoggable( Level.INFO ) ) {
            getLogger().log( Level.INFO, "Error at line " + reader.getLineNumber() + " and column " + reader
                    .getColumnNumber() + " of input <" + reader.getInput() + ">" );
        }
    }

    public void addObjectId( IdKey id, Object instance ) {
        if ( null == idToObject ) {
            idToObject = new HashMap<IdKey, Object>();
        }
        idToObject.put( id, instance );
    }

    public Object getObjectWithId( IdKey id ) {
        if ( null != idToObject ) {
            return idToObject.get( id );
        }
        return null;
    }
}
