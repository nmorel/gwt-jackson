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
        // TODO check if we can't use javascript array instead and cast them into java array. at least for primitive array
        // cf https://code.google.com/p/gwt-in-the-air/source/browse/trunk/src/net/ltgt/gwt/jscollections/client/JsArrays.java
        List<C> list = new ArrayList<C>();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            list.add( deserializer.deserialize( reader, ctx ) );
        }
        reader.endArray();
        return list;
    }
}
