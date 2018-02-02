package java.io;

public interface DataOutput {

    void writeBoolean(boolean b);

    void writeByte(byte b);

    void writeBytes(String s);

    void writeChar(char c);

    void writeChars(String s);

    void writeDouble(double d);

    void writeFloat(float f);

    void write(int b);

    void write(byte[] b);

    void write(byte[] b, int off, int len);

    void writeInt(int i);

    void writeLong(long l);

    void writeShort(short s);

    void writeUTF(String s);

    int skipBytes(int n);
}
