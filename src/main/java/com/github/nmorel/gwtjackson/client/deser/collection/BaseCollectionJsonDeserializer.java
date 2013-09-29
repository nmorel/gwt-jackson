package com.github.nmorel.gwtjackson.client.deser.collection;

import java.io.IOException;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.Collection}.
 *
 * @param <C> {@link java.util.Collection} type
 * @param <T> Type of the elements inside the {@link java.util.Collection}
 *
 * @author Nicolas Morel
 */
public abstract class BaseCollectionJsonDeserializer<C extends Collection<T>, T> extends BaseIterableJsonDeserializer<C, T> {

    /**
     * @param deserializer {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} used to map the objects inside the {@link java
     * .util.Collection}.
     */
    public BaseCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    public C doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        C result = newCollection();

        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            T element = deserializer.decode( reader, ctx );
            if ( isNullValueAllowed() || null != element ) {
                result.add( element );
            }
        }
        reader.endArray();

        return result;
    }

    /**
     * Instantiates a new collection for decoding process.
     *
     * @return the new collection
     */
    protected abstract C newCollection();

    /**
     * @return true if the collection accepts null value
     */
    protected boolean isNullValueAllowed() {
        return true;
    }

    @Override
    public void setBackReference( String referenceName, Object reference, C value, JsonDecodingContext ctx ) {
        if ( null != value && !value.isEmpty() ) {
            for ( T val : value ) {
                deserializer.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
