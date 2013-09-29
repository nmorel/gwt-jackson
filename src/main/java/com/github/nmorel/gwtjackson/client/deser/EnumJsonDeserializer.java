package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link Enum}.
 *
 * @param <E> Type of the enum
 *
 * @author Nicolas Morel
 */
public class EnumJsonDeserializer<E extends Enum<E>> extends JsonDeserializer<E> {

    /**
     * @param enumClass class of the enumeration
     * @param <E> Type of the enum
     *
     * @return a new instance of {@link EnumJsonDeserializer}
     */
    public static <E extends Enum<E>> EnumJsonDeserializer<E> newInstance( Class<E> enumClass ) {
        return new EnumJsonDeserializer<E>( enumClass );
    }

    private final Class<E> enumClass;

    /**
     * @param enumClass class of the enumeration
     */
    protected EnumJsonDeserializer( Class<E> enumClass ) {
        if ( null == enumClass ) {
            throw new IllegalArgumentException( "enumClass cannot be null" );
        }
        this.enumClass = enumClass;
    }

    @Override
    public E doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        return Enum.valueOf( enumClass, reader.nextString() );
    }
}
