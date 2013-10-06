package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link String}.
 *
 * @author Nicolas Morel
 */
public final class StringKeySerializer extends KeySerializer<String> {

    private static final StringKeySerializer INSTANCE = new StringKeySerializer();

    /**
     * @return an instance of {@link StringKeySerializer}
     */
    public static StringKeySerializer getInstance() {
        return INSTANCE;
    }

    private StringKeySerializer() { }

    @Override
    protected String doSerialize( @Nonnull String value, JsonSerializationContext ctx ) {
        return value;
    }
}
