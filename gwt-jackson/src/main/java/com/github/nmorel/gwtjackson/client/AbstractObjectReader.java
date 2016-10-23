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
 * Base implementation of {@link ObjectReader}. Extends {@link AbstractObjectMapper} to avoid code duplication, trying to write with this
 * reader will result in an {@link UnsupportedOperationException}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractObjectReader<T> extends AbstractObjectMapper<T> implements ObjectReader<T> {

    /**
     * <p>Constructor for AbstractObjectReader.</p>
     *
     * @param rootName a {@link java.lang.String} object.
     */
    public AbstractObjectReader( String rootName ) {
        super( rootName );
    }

    /** {@inheritDoc} */
    @Override
    protected final JsonSerializer<?> newSerializer() {
        throw new UnsupportedOperationException( "ObjectReader doesn't support serialization" );
    }
}
