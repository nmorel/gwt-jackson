package com.github.nmorel.gwtjackson.client;

import java.util.Date;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
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
        return AbstractTester.getUTCTime( year, month, day, hour, minute, second, milli );
    }

    protected Date getUTCDate( int year, int month, int day, int hour, int minute, int second, int milli )
    {
        return AbstractTester.getUTCDate( year, month, day, hour, minute, second, milli );
    }
}
