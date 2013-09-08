package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of long.
 *
 * @author Nicolas Morel
 */
public class PrimitiveLongArrayJsonMapper extends AbstractArrayJsonMapper<long[]>
{
    private final JsonMapper<Long> mapper;

    public PrimitiveLongArrayJsonMapper( JsonMapper<Long> mapper )
    {
        this.mapper = mapper;
    }

    @Override
    public long[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        List<Long> list = decodeList( reader, ctx, mapper );

        long[] result = new long[list.size()];
        int i = 0;
        for ( Long value : list )
        {
            if ( null != value )
            {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    @Override
    public void doEncode( JsonWriter writer, long[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( long value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
