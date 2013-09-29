package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for array.
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
    public T[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        List<T> list = decodeList( reader, ctx, deserializer );

        T[] result = arrayCreator.create( list.size() );
        int i = 0;
        for ( T value : list ) {
            result[i++] = value;
        }
        return result;
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T[] value, JsonDecodingContext ctx ) {
        if ( null != value && value.length > 0 ) {
            for ( T val : value ) {
                deserializer.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
