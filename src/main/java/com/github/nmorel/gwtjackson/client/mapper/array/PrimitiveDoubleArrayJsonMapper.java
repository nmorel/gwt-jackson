package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of double.
 *
 * @author Nicolas Morel
 */
public class PrimitiveDoubleArrayJsonMapper extends AbstractArrayJsonMapper<double[]>
{
    private final JsonMapper<Double> mapper;

    public PrimitiveDoubleArrayJsonMapper( JsonMapper<Double> mapper )
    {
        this.mapper = mapper;
    }

    @Override
    public double[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        List<Double> list = decodeList( reader, ctx, mapper );

        double[] result = new double[list.size()];
        int i = 0;
        for ( Double value : list )
        {
            result[i++] = value;
        }
        return result;
    }

    @Override
    public void doEncode( JsonWriter writer, double[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginArray();
        for ( double value : values )
        {
            mapper.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
