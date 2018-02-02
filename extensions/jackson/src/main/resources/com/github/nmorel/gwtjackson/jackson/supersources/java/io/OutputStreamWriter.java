package java.io;

import java.util.Arrays;

public class OutputStreamWriter extends Writer {

    private OutputStream outputStream;
    private String charsetName;

    public OutputStreamWriter(OutputStream outputStream, String charsetName) {
        this.outputStream = outputStream;
        this.charsetName = charsetName;
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] charstoWrite = Arrays.copyOfRange(cbuf, off, off + len);
        byte[] bytesToWrite = new String(charstoWrite).getBytes(charsetName);
        outputStream.write(bytesToWrite);
    }

}
