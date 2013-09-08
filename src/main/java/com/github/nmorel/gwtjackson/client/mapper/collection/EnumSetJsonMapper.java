package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.EnumSet;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link EnumSet}.
 *
 * @param <E> Type of the enumeration inside the {@link EnumSet}
 * @author Nicolas Morel
 */
public class EnumSetJsonMapper<E extends Enum<E>> extends BaseSetJsonMapper<EnumSet<E>, E>
{
    private final Class<E> enumClass;

    /**
     * @param enumClass class of the enumeration
     * @param mapper {@link com.github.nmorel.gwtjackson.client.mapper.EnumJsonMapper} used to map the enums inside the {@link EnumSet}.
     */
    public EnumSetJsonMapper( Class<E> enumClass, JsonMapper<E> mapper )
    {
        super( mapper );
        this.enumClass = enumClass;
    }

    @Override
    protected EnumSet<E> newCollection()
    {
        return EnumSet.noneOf( enumClass );
    }
}
