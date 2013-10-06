package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;

/**
 * Default {@link KeyDeserializer} implementation for {@link Enum}.
 *
 * @param <E> Type of the enum
 *
 * @author Nicolas Morel
 */
public final class EnumKeyDeserializer<E extends Enum<E>> extends KeyDeserializer<E> {

    /**
     * @param enumClass class of the enumeration
     * @param <E> Type of the enum
     *
     * @return a new instance of {@link EnumKeyDeserializer}
     */
    public static <E extends Enum<E>> EnumKeyDeserializer<E> newInstance( Class<E> enumClass ) {
        return new EnumKeyDeserializer<E>( enumClass );
    }

    private final Class<E> enumClass;

    /**
     * @param enumClass class of the enumeration
     */
    private EnumKeyDeserializer( Class<E> enumClass ) {
        if ( null == enumClass ) {
            throw new IllegalArgumentException( "enumClass cannot be null" );
        }
        this.enumClass = enumClass;
    }

    @Override
    protected E doDeserialize( String key, JsonDeserializationContext ctx ) {
        return Enum.valueOf( enumClass, key );
    }
}
