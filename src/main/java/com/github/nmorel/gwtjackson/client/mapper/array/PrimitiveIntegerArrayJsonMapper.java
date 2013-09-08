package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of int.
 *
 * @author Nicolas Morel
 */
public class PrimitiveIntegerArrayJsonMapper extends AbstractArrayJsonMapper<int[]>
{
    private final JsonMapper<Integer> mapper;

    public PrimitiveIntegerArrayJsonMapper( JsonMapper<Integer> mapper )
    {
        this.mapper = mapper;
    }

    @Override
    public int[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        List<Integer> list = decodeList( reader, ctx, mapper );

        int[] result = new int[list.size()];
        int i = 0;
        for ( Integer value : list )
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
    public void doEncode( JsonWriter writer, int[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( int value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
