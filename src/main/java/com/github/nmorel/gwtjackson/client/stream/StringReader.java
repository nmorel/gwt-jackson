package com.github.nmorel.gwtjackson.client.stream;

import java.io.IOException;

/**
 * @author Nicolas Morel
 */
public class StringReader {

    private final String in;

    private final int length;

    private int next;

    public StringReader( String in ) {
        if ( in == null ) {
            throw new NullPointerException( "in == null" );
        }
        this.in = in;
        this.length = in.length();
        this.next = 0;
    }

    public String getInput() {
        return in;
    }

    /**
     * Reads characters into a portion of an array.
     *
     * @param cbuf Destination buffer
     * @param off Offset at which to start writing characters
     * @param len Maximum number of characters to read
     *
     * @return The number of characters read, or -1 if the end of the
     *         stream has been reached
     * @throws IOException If an I/O error occurs
     */
    public int read( char cbuf[], int off, int len ) throws IOException {
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
