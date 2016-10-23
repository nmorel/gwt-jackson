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

package com.github.nmorel.gwtjackson.client.stream.impl;

/**
 * <p>StringReader class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class StringReader {

    private final String in;

    private final int length;

    private int next;

    /**
     * <p>Constructor for StringReader.</p>
     *
     * @param in a {@link java.lang.String} object.
     */
    public StringReader( String in ) {
        if ( in == null ) {
            throw new NullPointerException( "in == null" );
        }
        this.in = in;
        this.length = in.length();
        this.next = 0;
    }

    /**
     * <p>getInput</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getInput() {
        return in;
    }

    /**
     * Reads characters into a portion of an array.
     *
     * @param cbuf Destination buffer
     * @param off Offset at which to start writing characters
     * @param len Maximum number of characters to read
     * @return The number of characters read, or -1 if the end of the
     * stream has been reached
     */
    public int read( char cbuf[], int off, int len ) {
        if ( (off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0) ) {
            throw new IndexOutOfBoundsException();
        } else if ( len == 0 ) {
            return 0;
        }
        if ( next >= length ) {
            return -1;
        }
        int n = Math.min( length - next, len );
        in.getChars( next, next + n, cbuf, off );
        next += n;
        return n;
    }
}
