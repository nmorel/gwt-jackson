package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonMapper} for array.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractArrayJsonMapper<T> extends AbstractJsonMapper<T>
{
    protected <C> List<C> decodeList( JsonReader reader, JsonDecodingContext ctx, JsonMapper<C> mapper ) throws IOException
    {
        List<C> list = new ArrayList<C>();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() )
        {
            list.add( mapper.decode( reader, ctx ) );
        }
        reader.endArray();
        return list;
    }
}
