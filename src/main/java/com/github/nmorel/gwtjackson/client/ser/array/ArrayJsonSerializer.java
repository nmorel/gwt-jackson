package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonSerializer} implementation for array.
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
    public void doEncode( JsonWriter writer, @Nonnull T[] values, JsonEncodingContext ctx ) throws IOException {
        writer.beginArray();
        for ( T value : values ) {
            serializer.encode( writer, value, ctx );
        }
        writer.endArray();
    }
}
