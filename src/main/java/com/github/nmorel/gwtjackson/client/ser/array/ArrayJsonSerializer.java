package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array.
 *
 * @param <T> Type of the elements inside the array
 *
 * @author Nicolas Morel
 */
public class ArrayJsonSerializer<T> extends JsonSerializer<T[]> {

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the array.
     * @param <T> Type of the elements inside the array
     *
     * @return a new instance of {@link ArrayJsonSerializer}
     */
    public static <T> ArrayJsonSerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new ArrayJsonSerializer<T>( serializer );
    }

    private final JsonSerializer<T> serializer;

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the array.
     */
    protected ArrayJsonSerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull T[] values, JsonSerializationContext ctx ) throws IOException {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        if ( ctx.isWriteSingleElemArraysUnwrapped() && values.length == 1 ) {
            serializer.serialize( writer, values[0], ctx );
        } else {
            writer.beginArray();
            for ( T value : values ) {
                serializer.serialize( writer, value, ctx );
            }
            writer.endArray();
        }
    }
}
