package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.utils.Base64;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for array of byte.
 *
 * @author Nicolas Morel
 */
public class PrimitiveByteArrayJsonDeserializer extends AbstractArrayJsonDeserializer<byte[]> {

    private static final PrimitiveByteArrayJsonDeserializer INSTANCE = new PrimitiveByteArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveByteArrayJsonDeserializer}
     */
    public static PrimitiveByteArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveByteArrayJsonDeserializer() { }

    @Override
    public byte[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        return Base64.decode( reader.nextString() ).getBytes();
    }
}
