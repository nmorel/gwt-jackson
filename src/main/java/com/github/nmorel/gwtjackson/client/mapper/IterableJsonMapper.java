package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonMapper} implementation for {@link Iterable}. The decoding process returns an {@link ArrayList}.
 *
 * @author Nicolas Morel
 */
public class IterableJsonMapper<I extends Iterable<T>, T> extends AbstractJsonMapper<I>
{
    private final JsonMapper<T> mapper;

    /** @param mapper {@link JsonMapper} used to map the objects inside the iterable. */
    public IterableJsonMapper( JsonMapper<T> mapper )
    {
        if ( null == mapper )
        {
            throw new IllegalArgumentException( "mapper can't be null" );
        }
        this.mapper = mapper;
    }

    @Override
    public I doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        Collection<T> result = newCollection();

        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() )
        {
            result.add( mapper.decode( reader, ctx ) );
        }
        reader.endArray();

        return (I) result;
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

    /**
     * Instantiates a new collection for decoding process.
     *
     * @return the new collection
     */
    protected Collection<T> newCollection()
    {
        return new ArrayList<T>();
    }
}
