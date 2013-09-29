package com.github.nmorel.gwtjackson.client.deser.collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link Iterable}. The decoding process returns an
 * {@link java.util.ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link Iterable}
 *
 * @author Nicolas Morel
 */
public class IterableJsonDeserializer<T> extends BaseIterableJsonDeserializer<Iterable<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Iterable}.
     * @param <T> Type of the elements inside the {@link Iterable}
     *
     * @return a new instance of {@link IterableJsonDeserializer}
     */
    public static <T> IterableJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new IterableJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Iterable}.
     */
    private IterableJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    public Iterable<T> doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        Collection<T> result = new ArrayList<T>();

        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            result.add( deserializer.decode( reader, ctx ) );
        }
        reader.endArray();

        return result;
    }
}
