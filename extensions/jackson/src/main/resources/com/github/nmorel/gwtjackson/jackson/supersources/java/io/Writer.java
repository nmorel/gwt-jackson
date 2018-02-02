package java.io;

public abstract class Writer {

    public Writer() {
    }

    public Writer(Object lock) {
    }

    public Writer append(char c) throws IOException {
        write(new char[]{c}, 0, 1);
        return this;
    }

    public Writer append(CharSequence seq) throws IOException {
        return append(seq, 0, seq.length());
    }

    public Writer append(CharSequence seq, int start, int end) throws IOException {
        char[] buf = new char[end - start];
        for (int i = start; i < end; i++) {
            buf[i] = seq.charAt(i);
        }
        write(buf, 0, end - start);
        return this;
    }

    public void write(String str) throws IOException {
        append(str);
    }

    public void write(String str, int off, int len) throws IOException {
        append(str, off, off + len);
    }

    public void write(int chr) throws IOException {
        append((char) chr);
    }

    public void write(char[] cbuf) throws IOException {
        write(cbuf, 0, cbuf.length);
    }

    public abstract void close() throws IOException;

    public abstract void flush() throws IOException;

    public abstract void write(char[] cbuf, int off, int len) throws IOException;

}
