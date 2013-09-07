package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of boolean.
 *
 * @author Nicolas Morel
 */
public class PrimitiveBooleanArrayJsonMapper extends AbstractArrayJsonMapper<boolean[]>
{
    private final JsonMapper<Boolean> mapper;

    public PrimitiveBooleanArrayJsonMapper( JsonMapper<Boolean> mapper )
    {
        this.mapper = mapper;
    }

    @Override
    public boolean[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        List<Boolean> list = decodeList( reader, ctx, mapper );

        boolean[] result = new boolean[list.size()];
        int i = 0;
        for ( Boolean value : list )
        {
            result[i++] = value;
        }
        return result;
    }

    @Override
    public void doEncode( JsonWriter writer, boolean[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( boolean value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
