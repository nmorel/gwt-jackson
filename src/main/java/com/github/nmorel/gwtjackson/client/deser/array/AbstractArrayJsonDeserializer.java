package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} for array.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractArrayJsonDeserializer<T> extends JsonDeserializer<T> {

    protected <C> List<C> decodeList( JsonReader reader, JsonDecodingContext ctx, JsonDeserializer<C> deserializer ) throws IOException {
        List<C> list = new ArrayList<C>();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            list.add( deserializer.decode( reader, ctx ) );
        }
        reader.endArray();
        return list;
    }
}
