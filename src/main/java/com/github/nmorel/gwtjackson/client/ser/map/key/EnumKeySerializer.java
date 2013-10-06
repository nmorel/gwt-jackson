package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link Enum}.
 *
 * @author Nicolas Morel
 */
public final class EnumKeySerializer<E extends Enum<E>> extends KeySerializer<E> {

    private static final EnumKeySerializer<?> INSTANCE = new EnumKeySerializer();

    /**
     * @return an instance of {@link EnumKeySerializer}
     */
    @SuppressWarnings( "unchecked" )
    public static <E extends Enum<E>> EnumKeySerializer<E> getInstance() {
        return (EnumKeySerializer<E>) INSTANCE;
    }

    private EnumKeySerializer() { }

    @Override
    protected String doSerialize( @Nonnull E value, JsonSerializationContext ctx ) {
        return value.name();
    }
}
