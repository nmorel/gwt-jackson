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

/**
 * Base implementation of {@link ObjectWriter}. Extends {@link AbstractObjectMapper} to avoid code duplication, trying to read with this
 * writer will result in an {@link UnsupportedOperationException}.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractObjectWriter<T> extends AbstractObjectMapper<T> implements ObjectWriter<T> {

    public AbstractObjectWriter( String rootName ) {
        super( rootName );
    }

    @Override
    protected final JsonDeserializer<T> newDeserializer( JsonDeserializationContext ctx ) {
        throw new UnsupportedOperationException( "ObjectWriter doesn't support deserialization" );
    }
}
