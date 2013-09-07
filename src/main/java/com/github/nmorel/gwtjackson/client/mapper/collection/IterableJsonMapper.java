package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonMapper} implementation for {@link Iterable}. The decoding process returns an {@link ArrayList}.
 *
 * @author Nicolas Morel
 */
public class IterableJsonMapper<T> extends AbstractIterableJsonMapper<Iterable<T>, T>
{
    /** @param mapper {@link JsonMapper} used to map the objects inside the iterable. */
    public IterableJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    public Iterable<T> doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        Collection<T> result = new ArrayList<T>();

        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() )
        {
            result.add( mapper.decode( reader, ctx ) );
        }
        reader.endArray();

        return result;
    }
}
