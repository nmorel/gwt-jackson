package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.io.IOException;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base {@link JsonMapper} implementation for {@link Collection}.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractCollectionJsonMapper<C extends Collection<T>, T> extends AbstractIterableJsonMapper<C, T>
{
    /** @param mapper {@link JsonMapper} used to map the objects inside the iterable. */
    public AbstractCollectionJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    public C doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        C result = newCollection();

        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() )
        {
            result.add( mapper.decode( reader, ctx ) );
        }
        reader.endArray();

        return result;
    }

    /**
     * Instantiates a new collection for decoding process.
     *
     * @return the new collection
     */
    protected abstract C newCollection();
}
