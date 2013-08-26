package com.github.nmorel.gwtjackson.client;

import java.util.Date;

import com.google.gwt.junit.client.GWTTestCase;

/** @author Nicolas Morel */
public abstract class GwtJacksonTestCase extends GWTTestCase
{
    @Override
    public String getModuleName()
    {
        return "com.github.nmorel.gwtjackson.GwtJacksonTest";
    }

    @SuppressWarnings( "deprecation" )
    protected long getUTCTime( int year, int month, int day, int hour, int minute, int second, int milli )
    {
        return Date.UTC( year - 1900, month - 1, day, hour, minute, second ) + milli;
    }

    protected Date getUTCDate( int year, int month, int day, int hour, int minute, int second, int milli )
    {
        return new Date( getUTCTime( year, month, day, hour, minute, second, milli ) );
    }
}
