package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base {@link JsonMapper} implementation for {@link Iterable}.
 *
 * @param <I> {@link Iterable} type
 * @param <T> Type of the elements inside the {@link Iterable}
 * @author Nicolas Morel
 */
public abstract class BaseIterableJsonMapper<I extends Iterable<T>, T> extends AbstractJsonMapper<I>
{
    protected final JsonMapper<T> mapper;

    /** @param mapper {@link JsonMapper} used to map the objects inside the {@link Iterable}. */
    public BaseIterableJsonMapper( JsonMapper<T> mapper )
    {
        if ( null == mapper )
        {
            throw new IllegalArgumentException( "mapper can't be null" );
        }
        this.mapper = mapper;
    }

    @Override
    public void doEncode( JsonWriter writer, I values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( T value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }

    @Override
    public void setBackReference( String referenceName, Object reference, I value, JsonDecodingContext ctx )
    {
        if ( null != value )
        {
            for ( T val : value )
            {
                mapper.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
