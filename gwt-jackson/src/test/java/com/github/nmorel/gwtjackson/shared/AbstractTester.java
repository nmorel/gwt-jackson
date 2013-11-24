/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import junit.framework.Assert;

/**
 * Extends {@link Assert} because GWT does not support {@link org.junit.Assert}
 *
 * @author Nicolas Morel
 */
public abstract class AbstractTester extends Assert {

    /*
    /**********************************************************
    /* Shared helper classes
    /**********************************************************
     */

    /**
     * Simple wrapper around boolean types, usually to test value
     * conversions or wrapping
     */
    public static class BooleanWrapper {

        public Boolean b;

        @JsonCreator
        public BooleanWrapper( Boolean value ) { b = value; }

        @JsonValue
        public Boolean value() { return b; }
    }

    public static class IntWrapper {

        public int i;

        public IntWrapper() { }

        public IntWrapper( int value ) { i = value; }
    }

    /**
     * Simple wrapper around String type, usually to test value
     * conversions or wrapping
     */
    public static class StringWrapper {

        public String str;

        public StringWrapper() { }

        public StringWrapper( String value ) {
            str = value;
        }
    }

    public static class ObjectWrapper {

        @JsonCreator
        static ObjectWrapper jsonValue( final Object object ) {
            return new ObjectWrapper( object );
        }

        private final Object object;

        protected ObjectWrapper( final Object object ) {
            this.object = object;
        }

        public Object getObject() { return object; }
    }

    public static class ListWrapper<T> {

        public List<T> list;

        public ListWrapper( T... values ) {
            list = new ArrayList<T>();
            for ( T value : values ) {
                list.add( value );
            }
        }
    }

    public static class MapWrapper<K, V> {

        public Map<K, V> map;

        public MapWrapper( Map<K, V> m ) {
            map = m;
        }
    }

    public static class ArrayWrapper<T> {

        public T[] array;

        public ArrayWrapper( T[] v ) {
            array = v;
        }
    }

    @SuppressWarnings("deprecation")
    public static long getUTCTime( int year, int month, int day, int hour, int minute, int second, int milli ) {
        return Date.UTC( year - 1900, month - 1, day, hour, minute, second ) + milli;
    }

    public static Date getUTCDate( int year, int month, int day, int hour, int minute, int second, int milli ) {
        return new Date( getUTCTime( year, month, day, hour, minute, second, milli ) );
    }
}
