package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link Boolean}.
 *
 * @author Nicolas Morel
 */
public final class BooleanKeySerializer extends KeySerializer<Boolean> {

    private static final BooleanKeySerializer INSTANCE = new BooleanKeySerializer();

    /**
     * @return an instance of {@link BooleanKeySerializer}
     */
    public static BooleanKeySerializer getInstance() {
        return INSTANCE;
    }

    private BooleanKeySerializer() { }

    @Override
    protected String doSerialize( @Nonnull Boolean value, JsonSerializationContext ctx ) {
        return value.toString();
    }
}
