package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Enum}.
 *
 * @author Nicolas Morel
 */
public class EnumJsonMapper<E extends Enum<E>> extends AbstractJsonMapper<E>
{
    private final Class<E> enumClass;

    /** @param enumClass class of the enumeration */
    public EnumJsonMapper( Class<E> enumClass )
    {
        if ( null == enumClass )
        {
            throw new IllegalArgumentException( "enumClass can't be null" );
        }
        this.enumClass = enumClass;
    }

    @Override
    public E doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return Enum.valueOf( enumClass, reader.nextString() );
    }

    @Override
    public void doEncode( JsonWriter writer, E value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.name() );
    }
}
