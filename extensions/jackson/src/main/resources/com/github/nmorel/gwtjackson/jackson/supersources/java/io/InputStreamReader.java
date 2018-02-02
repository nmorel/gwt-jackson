package java.io;

import java.util.Arrays;

public class InputStreamReader extends Reader {

    private InputStream stream;
    String charset;

    public InputStreamReader(InputStream stream) {
        this(stream, "UTF-8");
    }

    public InputStreamReader(InputStream stream, String charset) {
        this.stream = stream;
        this.charset = charset;
    }

    public String getEncoding() {
        return "UTF-8";
    }

    @Override
    public boolean ready() throws IOException {
        return stream.available() != 0;
    }

    @Override
    public int read(char[] cbuf, int offset, int len) throws IOException {
        final int BYTES_PER_CHAR = 2;
        byte[] data = new byte[len * BYTES_PER_CHAR];
        int numOfBytesRead = stream.read(data, 0, len * BYTES_PER_CHAR);
        if (numOfBytesRead < 0) {
            return -1;
        }
        byte[] readData = Arrays.copyOf(data, numOfBytesRead);
        char[] readChars = new String(readData, charset).toCharArray();
        for (int i = 0; i < readChars.length; i++) {
            cbuf[i + offset] = readChars[i];
        }
        return readChars.length;
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
