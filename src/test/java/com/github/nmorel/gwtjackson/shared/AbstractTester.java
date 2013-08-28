package com.github.nmorel.gwtjackson.shared;

import java.util.Date;

/** @author Nicolas Morel */
public class AbstractTester
{
    @SuppressWarnings( "deprecation" )
    public static long getUTCTime( int year, int month, int day, int hour, int minute, int second, int milli )
    {
        return Date.UTC( year - 1900, month - 1, day, hour, minute, second ) + milli;
    }

    public static Date getUTCDate( int year, int month, int day, int hour, int minute, int second, int milli )
    {
        return new Date( getUTCTime( year, month, day, hour, minute, second, milli ) );
    }
}
