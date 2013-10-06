package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 */
public final class CharacterKeySerializer extends KeySerializer<Character> {

    private static final CharacterKeySerializer INSTANCE = new CharacterKeySerializer();

    /**
     * @return an instance of {@link CharacterKeySerializer}
     */
    public static CharacterKeySerializer getInstance() {
        return INSTANCE;
    }

    private CharacterKeySerializer() { }

    @Override
    protected String doSerialize( @Nonnull Character value, JsonSerializationContext ctx ) {
        return value.toString();
    }
}
