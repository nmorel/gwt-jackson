package java.io;

import java.nio.CharBuffer;

public abstract class Reader {

    public Reader() {
    }

    public Reader(Object lock) {
    }

    public int read(CharBuffer target) throws IOException {
        char[] buf = new char[target.remaining()];
        int out = read(buf, 0, buf.length);
        target.put(buf);
        return out;
    }

    public int read() throws IOException {
        char[] buf = new char[1];
        int isValid = read(buf, 0, 1);
        if (isValid != -1) {
            return buf[0];
        } else {
            return -1;
        }
    }

    public int read(char[] buf) throws IOException {
        return read(buf, 0, buf.length);
    }

    public long skip(long toSkip) throws IOException {
        final int BUF_SIZE = 4096;
        char[] buf = new char[BUF_SIZE];
        long numSkipped = 0;
        do {
            int numRead = read(buf, 0, Math.min((int) toSkip, BUF_SIZE));
            if (numRead == -1) {
                break;
            }
            toSkip -= numRead;
            numSkipped += numRead;
        } while (true);

        return numSkipped;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int readAheadLimit)
            throws IOException {
        throw new IOException("Mark not supported");
    }

    public void reset()
            throws IOException {
        throw new IOException("Reset not supported");
    }

    public boolean ready() throws IOException {
        return false;
    }

    public abstract int read(char[] cbuf, int off, int len) throws IOException;

    public abstract void close() throws IOException;
}
