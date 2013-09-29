package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for {@link Iterable}.
 *
 * @param <T> Type of the elements inside the {@link Iterable}
 *
 * @author Nicolas Morel
 */
public class IterableJsonSerializer<I extends Iterable<T>, T> extends JsonSerializer<I> {

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the {@link Iterable}.
     * @param <T> Type of the elements inside the {@link Iterable}
     *
     * @return a new instance of {@link IterableJsonSerializer}
     */
    public static <I extends Iterable<T>, T> IterableJsonSerializer<I, T> newInstance( JsonSerializer<T> serializer ) {
        return new IterableJsonSerializer<I, T>( serializer );
    }

    protected final JsonSerializer<T> serializer;

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the {@link Iterable}.
     */
    protected IterableJsonSerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull I values, JsonSerializationContext ctx ) throws IOException {
        writer.beginArray();
        for ( T value : values ) {
            serializer.serialize( writer, value, ctx );
        }
        writer.endArray();
    }
}
