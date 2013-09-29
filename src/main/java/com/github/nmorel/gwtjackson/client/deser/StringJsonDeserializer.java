package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link String}.
 *
 * @author Nicolas Morel
 */
public class StringJsonDeserializer extends JsonDeserializer<String> {

    private static final StringJsonDeserializer INSTANCE = new StringJsonDeserializer();

    /**
     * @return an instance of {@link StringJsonDeserializer}
     */
    public static StringJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private StringJsonDeserializer() { }

    @Override
    public String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        return reader.nextString();
    }
}
