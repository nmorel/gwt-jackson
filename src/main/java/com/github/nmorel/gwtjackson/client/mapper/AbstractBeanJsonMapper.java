package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonMapper} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonMapper<T> extends AbstractJsonMapper<T>
{
    public static interface DecoderProperty<T>
    {
        void decode( JsonReader reader, T bean, JsonDecodingContext ctx ) throws IOException;
    }

    public static interface EncoderProperty<T>
    {
        void encode( JsonWriter writer, T bean, JsonEncodingContext ctx ) throws IOException;
    }

    private Map<String, DecoderProperty<T>> decoders;
    private Map<String, EncoderProperty<T>> encoders;

    protected void initDecoders()
    {
        if ( null == decoders )
        {
            decoders = new LinkedHashMap<String, DecoderProperty<T>>();
            initDecoders( decoders );
        }
    }

    protected abstract void initDecoders( Map<String, DecoderProperty<T>> decoders );

    protected void initEncoders()
    {
        if ( null == encoders )
        {
            encoders = new LinkedHashMap<String, EncoderProperty<T>>();
            initEncoders( encoders );
        }
    }

    protected abstract void initEncoders( Map<String, EncoderProperty<T>> encoders );

    protected abstract T newInstance();

    @Override
    public T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        initDecoders();

        T result = newInstance();

        reader.beginObject();

        while ( JsonToken.NAME.equals( reader.peek() ) )
        {
            String name = reader.nextName();
            if ( JsonToken.NULL.equals( reader.peek() ) )
            {
                reader.skipValue();
                continue;
            }

            DecoderProperty<T> property = decoders.get( name );
            if ( null == property )
            {
                // TODO add a configuration to tell if we skip or throw an unknown property
                reader.skipValue();
            }
            else
            {
                property.decode( reader, result, ctx );
            }
        }

        reader.endObject();

        return result;
    }

    @Override
    public void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        initEncoders();

        writer.beginObject();
        for ( Map.Entry<String, EncoderProperty<T>> entry : encoders.entrySet() )
        {
            writer.name( entry.getKey() );
            entry.getValue().encode( writer, value, ctx );
        }
        writer.endObject();
    }
}
