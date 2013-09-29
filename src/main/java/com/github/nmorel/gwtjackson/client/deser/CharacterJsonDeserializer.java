package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonDeserializer} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 */
public class CharacterJsonDeserializer extends JsonDeserializer<Character> {

    private static final CharacterJsonDeserializer INSTANCE = new CharacterJsonDeserializer();

    /**
     * @return an instance of {@link CharacterJsonDeserializer}
     */
    public static CharacterJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private CharacterJsonDeserializer() { }

    @Override
    public Character doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
            return (char) reader.nextInt();
        } else {
            String value = reader.nextString();
            if ( value.isEmpty() ) {
                return null;
            }
            return value.charAt( 0 );
        }
    }
}
