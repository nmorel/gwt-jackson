package java.io;

public class StringReader extends Reader {

    public CharArrayReader deglate;

    public StringReader(String content) {
        deglate = new CharArrayReader(content.toCharArray(), 0, content.length());
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return deglate.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        deglate.close();
    }

    @Override
    public boolean ready() throws IOException {
        return deglate.ready();
    }

}
