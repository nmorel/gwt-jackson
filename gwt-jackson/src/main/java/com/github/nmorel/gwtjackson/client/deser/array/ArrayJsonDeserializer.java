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

package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for array.
 *
 * @author Nicolas Morel
 */
public class ArrayJsonDeserializer<T> extends AbstractArrayJsonDeserializer<T[]> {

    public interface ArrayCreator<T> {

        T[] create( int length );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the array.
     * @param arrayCreator {@link ArrayCreator} used to create a new array
     * @param <T> Type of the elements inside the {@link AbstractCollection}
     *
     * @return a new instance of {@link ArrayJsonDeserializer}
     */
    public static <T> ArrayJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer, ArrayCreator<T> arrayCreator ) {
        return new ArrayJsonDeserializer<T>( deserializer, arrayCreator );
    }

    private final JsonDeserializer<T> deserializer;

    private final ArrayCreator<T> arrayCreator;

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the array.
     * @param arrayCreator {@link ArrayCreator} used to create a new array
     */
    protected ArrayJsonDeserializer( JsonDeserializer<T> deserializer, ArrayCreator<T> arrayCreator ) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer cannot be null" );
        }
        if ( null == arrayCreator ) {
            throw new IllegalArgumentException( "Cannot deserialize an array without an arrayCreator" );
        }
        this.deserializer = deserializer;
        this.arrayCreator = arrayCreator;
    }

    @Override
    public T[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
            IOException {
        List<T> list = deserializeIntoList( reader, ctx, deserializer, params );
        return list.toArray( arrayCreator.create( list.size() ) );
    }

    @Override
    protected T[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws
            IOException {
        T[] result = arrayCreator.create( 1 );
        result[0] = deserializer.deserialize( reader, ctx, params );
        return result;
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T[] value, JsonDeserializationContext ctx ) {
        if ( null != value && value.length > 0 ) {
            for ( T val : value ) {
                deserializer.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
