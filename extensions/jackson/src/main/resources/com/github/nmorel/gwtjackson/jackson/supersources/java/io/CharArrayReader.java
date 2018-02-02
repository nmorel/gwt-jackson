package java.io;

public class CharArrayReader extends Reader {

    private char[] data;
    private int offset;
    private int length;

    public CharArrayReader(char[] data, int offset, int len) {
        this.data = data;
        this.offset = offset;
        this.length = len;
    }

    @Override
    public boolean ready() throws IOException {
        return true;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (length <= 0) {
            return -1;
        }

        int numRead = Math.min(length, len);
        for (int i = 0; i < Math.min(length, len); i++) {
            cbuf[i + off] = data[i + offset];
        }
        offset += numRead;
        len -= numRead;

        return numRead;
    }

    @Override
    public void close() throws IOException {
    }

}
