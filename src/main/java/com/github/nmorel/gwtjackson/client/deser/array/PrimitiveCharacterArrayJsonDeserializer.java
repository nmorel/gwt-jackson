package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for array of char.
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
    public char[] doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return reader.nextString().toCharArray();
    }
}
