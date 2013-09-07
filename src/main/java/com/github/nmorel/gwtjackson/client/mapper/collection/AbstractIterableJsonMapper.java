package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
/**
 * Base {@link JsonMapper} implementation for {@link Iterable}.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractIterableJsonMapper<I extends Iterable<T>, T> extends AbstractJsonMapper<I>
{
    protected final JsonMapper<T> mapper;

    /** @param mapper {@link JsonMapper} used to map the objects inside the iterable. */
    public AbstractIterableJsonMapper( JsonMapper<T> mapper )
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
}
