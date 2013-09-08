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
public abstract class AbstractBeanJsonMapper<T, B extends AbstractBeanJsonMapper.InstanceBuilder<T>> extends AbstractJsonMapper<T>
{
    public static interface InstanceBuilder<T>
    {
        T build();
    }

    public static interface DecoderProperty<T, B extends AbstractBeanJsonMapper.InstanceBuilder<T>>
    {
        void decode( JsonReader reader, B builder, JsonDecodingContext ctx ) throws IOException;
    }

    public static interface EncoderProperty<T>
    {
        void encode( JsonWriter writer, T bean, JsonEncodingContext ctx ) throws IOException;
    }

    private Map<String, DecoderProperty<T, B>> decoders;
    private Map<String, EncoderProperty<T>> encoders;

    protected void initDecoders()
    {
        if ( null == decoders )
        {
            decoders = new LinkedHashMap<String, DecoderProperty<T, B>>();
            initDecoders( decoders );
        }
    }

    protected abstract void initDecoders( Map<String, DecoderProperty<T, B>> decoders );

    protected void initEncoders()
    {
        if ( null == encoders )
        {
            encoders = new LinkedHashMap<String, EncoderProperty<T>>();
            initEncoders( encoders );
        }
    }

    protected abstract void initEncoders( Map<String, EncoderProperty<T>> encoders );

    protected abstract B newInstanceBuilder( JsonDecodingContext ctx );

    @Override
    public T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        reader.beginObject();
        T result = decodeObject( reader, ctx );
        reader.endObject();
        return result;
    }

    public final T decodeObject( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        initDecoders();

        B builder = newInstanceBuilder( ctx );

        while ( JsonToken.NAME.equals( reader.peek() ) )
        {
            String name = reader.nextName();
            if ( JsonToken.NULL.equals( reader.peek() ) )
            {
                reader.skipValue();
                continue;
            }

            DecoderProperty<T, B> property = decoders.get( name );
            if ( null == property )
            {
                // TODO add a configuration to tell if we skip or throw an unknown property
                reader.skipValue();
            }
            else
            {
                property.decode( reader, builder, ctx );
            }
        }

        return builder.build();
    }

    @Override
    public void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginObject();
        encodeObject( writer, value, ctx );
        writer.endObject();
    }

    public final void encodeObject( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        initEncoders();

        for ( Map.Entry<String, EncoderProperty<T>> entry : encoders.entrySet() )
        {
            writer.name( entry.getKey() );
            entry.getValue().encode( writer, value, ctx );
        }
    }
}
