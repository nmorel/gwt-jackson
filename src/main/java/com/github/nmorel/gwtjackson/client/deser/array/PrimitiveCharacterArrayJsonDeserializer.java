package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for array of char.
 *
 * @author Nicolas Morel
 */
public class PrimitiveCharacterArrayJsonDeserializer extends AbstractArrayJsonDeserializer<char[]> {

    private static final PrimitiveCharacterArrayJsonDeserializer INSTANCE = new PrimitiveCharacterArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveCharacterArrayJsonDeserializer}
     */
    public static PrimitiveCharacterArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveCharacterArrayJsonDeserializer() { }

    @Override
    public char[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        return reader.nextString().toCharArray();
    }
}
