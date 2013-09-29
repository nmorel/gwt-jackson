package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link Boolean}.
 *
 * @author Nicolas Morel
 */
public class BooleanJsonDeserializer extends JsonDeserializer<Boolean> {

    private static final BooleanJsonDeserializer INSTANCE = new BooleanJsonDeserializer();

    /**
     * @return an instance of {@link BooleanJsonDeserializer}
     */
    public static BooleanJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private BooleanJsonDeserializer() { }

    @Override
    public Boolean doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        JsonToken token = reader.peek();
        if ( JsonToken.BOOLEAN.equals( token ) ) {
            return reader.nextBoolean();
        } else if ( JsonToken.STRING.equals( token ) ) {
            return Boolean.valueOf( reader.nextString() );
        } else if ( JsonToken.NUMBER.equals( token ) ) {
            return reader.nextInt() == 1;
        } else {
            return null;
        }
    }
}
