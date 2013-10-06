package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link KeyDeserializer} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 */
public final class CharacterKeyDeserializer extends KeyDeserializer<Character> {

    private static final CharacterKeyDeserializer INSTANCE = new CharacterKeyDeserializer();

    /**
     * @return an instance of {@link CharacterKeyDeserializer}
     */
    public static CharacterKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private CharacterKeyDeserializer() { }

    @Override
    protected Character doDeserialize( String key, JsonDeserializationContext ctx ) {
        return key.charAt( 0 );
    }
}
