package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of short.
 *
 * @author Nicolas Morel
 */
public class PrimitiveShortArrayJsonMapper extends AbstractArrayJsonMapper<short[]>
{
    private final JsonMapper<Short> mapper;

    public PrimitiveShortArrayJsonMapper( JsonMapper<Short> mapper )
    {
        this.mapper = mapper;
    }

    @Override
    public short[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        List<Short> list = decodeList( reader, ctx, mapper );

        short[] result = new short[list.size()];
        int i = 0;
        for ( Short value : list )
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
    public void doEncode( JsonWriter writer, short[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( short value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
