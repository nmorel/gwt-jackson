package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link JsonDeserializer} for array.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractArrayJsonDeserializer<T> extends JsonDeserializer<T> {

    @Override
    public T doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( JsonToken.BEGIN_ARRAY == reader.peek() ) {
            return doDeserializeArray( reader, ctx );
        } else {
            return doDeserializeNonArray( reader, ctx );
        }
    }

    protected abstract T doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException;

    protected T doDeserializeNonArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( ctx.isAcceptSingleValueAsArray() ) {
            return doDeserializeSingleArray( reader, ctx );
        } else {
            throw ctx.traceError( "Cannot deserialize an array out of " + reader.peek() + " token", reader );
        }
    }

    protected abstract T doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException;

    /**
     * Deserializes the array into a {@link List}. We need the length of the array before creating it.
     *
     * @param reader reader
     * @param ctx context of the deserialization process
     * @param deserializer deserializer for element inside the array
     * @param <C> type of the element inside the array
     *
     * @return a list containing all the elements of the array
     * @throws IOException if an error occurs while reading the array
     */
    protected <C> List<C> deserializeIntoList( JsonReader reader, JsonDeserializationContext ctx,
                                               JsonDeserializer<C> deserializer ) throws IOException {
        List<C> list = new ArrayList<C>();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            list.add( deserializer.deserialize( reader, ctx ) );
        }
        reader.endArray();
        return list;
    }
}
