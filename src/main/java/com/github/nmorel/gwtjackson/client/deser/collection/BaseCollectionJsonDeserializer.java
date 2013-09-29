package com.github.nmorel.gwtjackson.client.deser.collection;

import java.io.IOException;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base {@link JsonDeserializer} implementation for {@link Collection}.
 *
 * @param <C> {@link Collection} type
 * @param <T> Type of the elements inside the {@link Collection}
 *
 * @author Nicolas Morel
 */
public abstract class BaseCollectionJsonDeserializer<C extends Collection<T>, T> extends BaseIterableJsonDeserializer<C, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link Collection}.
     */
    public BaseCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    public C doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        C result = newCollection();

        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            T element = deserializer.deserialize( reader, ctx );
            if ( isNullValueAllowed() || null != element ) {
                result.add( element );
            }
        }
        reader.endArray();

        return result;
    }

    /**
     * Instantiates a new collection for deserialization process.
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
    public void setBackReference( String referenceName, Object reference, C value, JsonDeserializationContext ctx ) {
        if ( null != value && !value.isEmpty() ) {
            for ( T val : value ) {
                deserializer.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
