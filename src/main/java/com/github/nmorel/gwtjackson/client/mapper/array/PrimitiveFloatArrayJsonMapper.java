package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of float.
 *
 * @author Nicolas Morel
 */
public class PrimitiveFloatArrayJsonMapper extends AbstractArrayJsonMapper<float[]>
{
    private final JsonMapper<Float> mapper;

    public PrimitiveFloatArrayJsonMapper( JsonMapper<Float> mapper )
    {
        this.mapper = mapper;
    }

    @Override
    public float[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        List<Float> list = decodeList( reader, ctx, mapper );

        float[] result = new float[list.size()];
        int i = 0;
        for ( Float value : list )
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
    public void doEncode( JsonWriter writer, float[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( float value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
