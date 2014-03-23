/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.deser.array.dd;

import java.util.AbstractCollection;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for 2D array.
 *
 * @author Nicolas Morel
 */
public class Array2dJsonDeserializer<T> extends AbstractArray2dJsonDeserializer<T[][]> {

    public interface Array2dCreator<T> {

        T[][] create( int first, int second );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the array.
     * @param arrayCreator {@link Array2dCreator} used to create a new array
     * @param <T> Type of the elements inside the {@link AbstractCollection}
     *
     * @return a new instance of {@link Array2dJsonDeserializer}
     */
    public static <T> Array2dJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer, Array2dCreator<T> arrayCreator ) {
        return new Array2dJsonDeserializer<T>( deserializer, arrayCreator );
    }

    private final JsonDeserializer<T> deserializer;

    private final Array2dCreator<T> array2dCreator;

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the array.
     * @param array2dCreator {@link Array2dCreator} used to create a new array
     */
    protected Array2dJsonDeserializer( JsonDeserializer<T> deserializer, Array2dCreator<T> array2dCreator ) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer cannot be null" );
        }
        if ( null == array2dCreator ) {
            throw new IllegalArgumentException( "Cannot deserialize an array without an array2dCreator" );
        }
        this.deserializer = deserializer;
        this.array2dCreator = array2dCreator;
    }

    @Override
    protected T[][] doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        List<List<T>> list = deserializeIntoList( reader, ctx, deserializer, params );

        if ( list.isEmpty() ) {
            return array2dCreator.create( 0, 0 );
        }

        List<T> firstList = list.get( 0 );
        if ( firstList.isEmpty() ) {
            return array2dCreator.create( list.size(), 0 );
        }

        T[][] array = array2dCreator.create( list.size(), firstList.size() );

        int i = 0;
        for ( List<T> innerList : list ) {
            array[i] = innerList.toArray( array[i] );
            i++;
        }
        return array;
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T[][] value, JsonDeserializationContext ctx ) {
        if ( null != value && value.length > 0 ) {
            for ( T[] array : value ) {
                for ( T val : array ) {
                    deserializer.setBackReference( referenceName, reference, val, ctx );
                }
            }
        }
    }
}
