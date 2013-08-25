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
    public static interface Property<T>
    {
        void decode( JsonReader reader, T bean, JsonDecodingContext ctx ) throws IOException;

        void encode( JsonWriter writer, T bean, JsonEncodingContext ctx ) throws IOException;
    }

    private Map<String, Property<T>> properties;

    protected void initProperties()
    {
        if ( null == properties )
        {
            properties = new LinkedHashMap<String, Property<T>>();
            initProperties( properties );
        }
    }

    protected abstract void initProperties( Map<String, Property<T>> properties );

    protected abstract T newInstance();

    @Override
    public T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        initProperties();

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

            Property<T> property = properties.get( name );
            if ( null == property )
            {
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
        initProperties();

        writer.beginObject();
        for ( Map.Entry<String, Property<T>> entry : properties.entrySet() )
        {
            writer.name( entry.getKey() );
            entry.getValue().encode( writer, value, ctx );
        }
        writer.endObject();
    }
}
