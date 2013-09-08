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
 * @param <C> {@link Collection} type
 * @param <T> Type of the elements inside the {@link Collection}
 * @author Nicolas Morel
 */
public abstract class BaseCollectionJsonMapper<C extends Collection<T>, T> extends BaseIterableJsonMapper<C, T>
{
    /** @param mapper {@link JsonMapper} used to map the objects inside the {@link Collection}. */
    public BaseCollectionJsonMapper( JsonMapper<T> mapper )
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
            T element = mapper.decode( reader, ctx );
            if ( isNullValueAllowed() || null != element )
            {
                result.add( element );
            }
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

    /** @return true if the collection accepts null value */
    protected boolean isNullValueAllowed()
    {
        return true;
    }
}
