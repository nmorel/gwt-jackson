package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for {@link Enum}.
 *
 * @author Nicolas Morel
 */
public class EnumJsonSerializer<E extends Enum<E>> extends JsonSerializer<E> {

    private static final EnumJsonSerializer<?> INSTANCE = new EnumJsonSerializer();

    /**
     * @return an instance of {@link EnumJsonSerializer}
     */
    @SuppressWarnings( "unchecked" )
    public static <E extends Enum<E>> EnumJsonSerializer<E> getInstance() {
        return (EnumJsonSerializer<E>) INSTANCE;
    }

    private EnumJsonSerializer() { }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull E value, JsonSerializationContext ctx ) throws IOException {
        writer.value( value.name() );
    }
}
