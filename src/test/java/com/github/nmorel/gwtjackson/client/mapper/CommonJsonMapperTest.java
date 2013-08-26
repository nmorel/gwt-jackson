package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/** @author Nicolas Morel */
public class CommonJsonMapperTest extends GwtJacksonTestCase
{

    public void testDecodeUnexpectedException()
    {
        JsonMapper<String> mapper = new AbstractJsonMapper<String>()
        {
            @Override
            protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
            {
                throw new UnsupportedOperationException();
            }

            @Override
            protected void doEncode( JsonWriter writer, String value, JsonEncodingContext ctx ) throws IOException
            {
            }
        };
        try
        {
            mapper.decode( "\"fail\"" );
            fail();
        }
        catch ( JsonDecodingException e )
        {
            assertTrue( e.getCause() instanceof UnsupportedOperationException );
        }
    }

    public void testDecodeIOException()
    {
        JsonMapper<String> mapper = new AbstractJsonMapper<String>()
        {
            @Override
            protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
            {
                throw new IOException();
            }

            @Override
            protected void doEncode( JsonWriter writer, String value, JsonEncodingContext ctx ) throws IOException
            {
            }
        };
        try
        {
            mapper.decode( "\"fail\"" );
            fail();
        }
        catch ( JsonDecodingException e )
        {
            assertTrue( e.getCause() instanceof IOException );
        }
    }

    public void testDecodeDecodingException()
    {
        final JsonDecodingException jsonDecodingException = new JsonDecodingException();
        JsonMapper<String> mapper = new AbstractJsonMapper<String>()
        {
            @Override
            protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
            {
                throw jsonDecodingException;
            }

            @Override
            protected void doEncode( JsonWriter writer, String value, JsonEncodingContext ctx ) throws IOException
            {
            }
        };
        try
        {
            mapper.decode( "\"fail\"" );
            fail();
        }
        catch ( JsonDecodingException e )
        {
            assertSame( jsonDecodingException, e );
        }
    }

    public void testEncodeUnexpectedException()
    {
        JsonMapper<String> mapper = new AbstractJsonMapper<String>()
        {
            @Override
            protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
            {
                return null;
            }

            @Override
            protected void doEncode( JsonWriter writer, String value, JsonEncodingContext ctx ) throws IOException
            {
                throw new NullPointerException();
            }
        };
        try
        {
            mapper.encode( "fail" );
            fail();
        }
        catch ( JsonEncodingException e )
        {
            assertTrue( e.getCause() instanceof NullPointerException );
        }
    }

    public void testEncodeIOException()
    {
        JsonMapper<String> mapper = new AbstractJsonMapper<String>()
        {
            @Override
            protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
            {
                return null;
            }

            @Override
            protected void doEncode( JsonWriter writer, String value, JsonEncodingContext ctx ) throws IOException
            {
                throw new IOException();
            }
        };
        try
        {
            mapper.encode( "fail" );
            fail();
        }
        catch ( JsonEncodingException e )
        {
            assertTrue( e.getCause() instanceof IOException );
        }
    }

    public void testEncodeEncodingException()
    {
        final JsonEncodingException jsonEncodingException = new JsonEncodingException();
        JsonMapper<String> mapper = new AbstractJsonMapper<String>()
        {
            @Override
            protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
            {
                return null;
            }

            @Override
            protected void doEncode( JsonWriter writer, String value, JsonEncodingContext ctx ) throws IOException
            {
                throw jsonEncodingException;
            }
        };
        try
        {
            mapper.encode( "fail" );
            fail();
        }
        catch ( JsonEncodingException e )
        {
            assertSame( jsonEncodingException, e );
        }
    }
}
