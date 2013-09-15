package com.github.nmorel.gwtjackson.client;

import java.util.Date;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.google.gwt.junit.client.GWTTestCase;

/** @author Nicolas Morel */
public abstract class GwtJacksonTestCase extends GWTTestCase
{
    @Override
    public String getModuleName()
    {
        return "com.github.nmorel.gwtjackson.GwtJacksonTest";
    }

    @SuppressWarnings("deprecation")
    protected long getUTCTime( int year, int month, int day, int hour, int minute, int second, int milli )
    {
        return AbstractTester.getUTCTime( year, month, day, hour, minute, second, milli );
    }

    protected Date getUTCDate( int year, int month, int day, int hour, int minute, int second, int milli )
    {
        return AbstractTester.getUTCDate( year, month, day, hour, minute, second, milli );
    }

    protected <T> JsonMapperTester<T> createMapper( final JsonMapper<T> mapper )
    {
        return new JsonMapperTester<T>()
        {
            @Override
            public T decode( String input )
            {
                return mapper.decode( input );
            }

            @Override
            public String encode( T input )
            {
                return mapper.encode( input );
            }
        };
    }

    protected <T> JsonEncoderTester<T> createEncoder( final JsonMapper<T> mapper )
    {
        return createMapper( mapper );
    }

    protected <T> JsonDecoderTester<T> createDecoder( final JsonMapper<T> mapper )
    {
        return createMapper( mapper );
    }
}
