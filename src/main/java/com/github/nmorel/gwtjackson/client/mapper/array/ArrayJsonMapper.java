package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonMapper} implementation for array.
 *
 * @author Nicolas Morel
 */
public class ArrayJsonMapper<T> extends AbstractArrayJsonMapper<T[]>
{
    public interface ArrayCreator<T>
    {
        T[] create( int length );
    }

    private final JsonMapper<T> mapper;
    private final ArrayCreator<T> arrayCreator;

    /**
     * @param mapper {@link JsonMapper} used to map the objects inside the iterable.
     * @param arrayCreator {@link ArrayCreator} used to create a new array in decoding process
     */
    public ArrayJsonMapper( JsonMapper<T> mapper, ArrayCreator<T> arrayCreator )
    {
        if ( null == mapper )
        {
            throw new IllegalArgumentException( "mapper can't be null" );
        }
        this.mapper = mapper;
        this.arrayCreator = arrayCreator;
    }

    @Override
    public T[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        if ( null == arrayCreator )
        {
            throw ctx.traceError( "Can't decode an array without an arrayCreator" );
        }

        List<T> list = decodeList( reader, ctx, mapper );

        T[] result = arrayCreator.create( list.size() );
        int i = 0;
        for ( T value : list )
        {
            result[i++] = value;
        }
        return result;
    }

    @Override
    public void doEncode( JsonWriter writer, T[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( T value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
