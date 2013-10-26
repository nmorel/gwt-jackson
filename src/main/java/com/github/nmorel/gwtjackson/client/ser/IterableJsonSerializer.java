package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Iterator;

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
        Iterator<T> iterator = values.iterator();

        if ( !iterator.hasNext() ) {
            if ( ctx.isWriteEmptyJsonArrays() ) {
                writer.beginArray();
                writer.endArray();
            } else {
                writer.cancelName();
            }
            return;
        }

        if ( ctx.isWriteSingleElemArraysUnwrapped() ) {

            T first = iterator.next();

            if ( iterator.hasNext() ) {
                // there is more than one element, we write the array normally
                writer.beginArray();
                serializer.serialize( writer, first, ctx );
                while ( iterator.hasNext() ) {
                    serializer.serialize( writer, iterator.next(), ctx );
                }
                writer.endArray();
            } else {
                // there is only one element, we write it directly
                serializer.serialize( writer, first, ctx );
            }

        } else {
            writer.beginArray();
            while ( iterator.hasNext() ) {
                serializer.serialize( writer, iterator.next(), ctx );
            }
            writer.endArray();
        }
    }
}
