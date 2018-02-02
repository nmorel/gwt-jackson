package com.github.nmorel.gwtjackson.jackson.client.ser;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.jackson.client.JacksonObjectMapper;

/**
 * Maps JsonGenerator methods to JsonWriter methods
 */
public class JacksonGeneratorImpl extends JsonGenerator {

    private JsonWriter writer;
    private JsonWriteContext context;

    private boolean isClosed;
    private ObjectCodec codec;

    public JacksonGeneratorImpl(JsonWriter writer) {
        this.writer = writer;
        context = JsonWriteContext.createRootContext(null);
        this.isClosed = false;
        this.codec = new JacksonObjectMapper();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        isClosed = true;
    }

    @Override
    public JsonGenerator disable(Feature feature) {
        // TODO: Should we throw an new UnsupportedOperationException() instead?
        return this;
    }

    @Override
    public JsonGenerator enable(Feature feature) {
        // TODO: Should we throw an new UnsupportedOperationException() instead?
        return this;
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public ObjectCodec getCodec() {
        return codec;
    }

    @Override
    public int getFeatureMask() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return context;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public boolean isEnabled(Feature feature) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec codec) {
        this.codec = codec;
        return this;
    }

    @Override
    public JsonGenerator setFeatureMask(int feature) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Version version() {
        // TODO Auto-generated method stub
        return Version.unknownVersion();
    }

    @Override
    public int writeBinary(Base64Variant base64convertor, InputStream input, int length) throws IOException {
        context.writeValue();
        StringBuilder out = new StringBuilder();
        byte[] buffer = new byte[1024];
        int read = 0;
        int totalRead = 0;
        while (length != 0 || (read = input.read(buffer, 0, length)) > -1) {
            byte[] written = Arrays.copyOf(buffer, read);
            out.append(base64convertor.encode(written));
            length -= read;
            totalRead += read;
        }

        if (length > 0) {
            throw new IOException("Unexpected End of Input");
        }

        writer.value(out.toString());
        return totalRead;
    }

    @Override
    public void writeBinary(Base64Variant base64convertor, byte[] data, int offset, int length) throws IOException {
        context.writeValue();
        StringBuilder out = new StringBuilder();
        byte[] toWrite = Arrays.copyOfRange(data, offset, offset + length);
        out.append(base64convertor.encode(toWrite));
        writer.value(out.toString());
    }

    @Override
    public void writeBoolean(boolean value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeEndArray() throws IOException {
        context = context.clearAndGetParent();
        writer.endArray();
    }

    @Override
    public void writeEndObject() throws IOException {
        context = context.clearAndGetParent();
        writer.endObject();
    }

    @Override
    public void writeFieldName(String fieldName) throws IOException {
        context.writeFieldName(fieldName);
        writer.name(fieldName);
    }

    @Override
    public void writeFieldName(SerializableString fieldName) throws IOException {
        context.writeValue();
        writer.name(fieldName.getValue());
    }

    @Override
    public void writeNull() throws IOException {
        context.writeValue();
        writer.nullValue();
    }

    @Override
    public void writeNumber(int value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeNumber(long value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeNumber(BigInteger value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeNumber(double value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeNumber(float value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeNumber(BigDecimal value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeNumber(String value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeObject(Object value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeRaw(String value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeRaw(char value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeRaw(String data, int offset, int length) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeRaw(char[] data, int offset, int length) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeRawUTF8String(byte[] data, int offset, int length) throws IOException {
        // TODO: See if this is possible in any way (Charset unsupported in GWT)
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeRawValue(String value) throws IOException {
        context.writeValue();
        writer.rawValue(value);
    }

    @Override
    public void writeRawValue(String data, int offset, int length) throws IOException {
        writeRawValue(data.substring(offset, offset + length));
    }

    @Override
    public void writeRawValue(char[] data, int offset, int length) throws IOException {
        writeRawValue(new String(data), offset, length);
    }

    @Override
    public void writeStartArray() throws IOException {
        context = context.createChildArrayContext();
        writer.beginArray();
    }

    @Override
    public void writeStartObject() throws IOException {
        context = context.createChildObjectContext();
        writer.beginObject();
    }

    @Override
    public void writeString(String value) throws IOException {
        context.writeValue();
        writer.value(value);
    }

    @Override
    public void writeString(SerializableString value) throws IOException {
        context.writeValue();
        writer.value(value.getValue());
    }

    @Override
    public void writeString(char[] data, int offset, int length) throws IOException {
        context.writeValue();
        writeString(new String(data).substring(offset, offset + length));
    }

    @Override
    public void writeTree(TreeNode value) throws IOException {
        context.writeValue();
        JsonParser parser = value.traverse();
        while (parser.nextToken() != null) {
            switch (parser.currentToken()) {
                case END_ARRAY:
                    writeEndArray();
                    break;
                case END_OBJECT:
                    writeEndObject();
                    break;
                case FIELD_NAME:
                    writeFieldName(parser.getCurrentName());
                    break;
                case NOT_AVAILABLE:
                    break;
                case START_ARRAY:
                    writeStartArray();
                    break;
                case START_OBJECT:
                    writeStartObject();
                    break;
                case VALUE_EMBEDDED_OBJECT:
                    // Shouldn't happen
                    break;
                case VALUE_FALSE:
                    writeBoolean(false);
                    break;
                case VALUE_NULL:
                    writeNull();
                    break;
                case VALUE_NUMBER_FLOAT:
                    writeNumber((Float) parser.getCurrentValue());
                    break;
                case VALUE_NUMBER_INT:
                    writeNumber((Integer) parser.getCurrentValue());
                    break;
                case VALUE_STRING:
                    writeString((String) parser.getCurrentValue());
                    break;
                case VALUE_TRUE:
                    writeBoolean(true);
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    public void writeUTF8String(byte[] data, int offset, int length) throws IOException {
        // TODO: See if this is possible in any way (Charset unsupported in GWT)
        throw new UnsupportedOperationException();
    }

}
