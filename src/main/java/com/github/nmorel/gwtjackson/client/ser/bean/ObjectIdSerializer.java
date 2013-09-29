package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public class ObjectIdSerializer<I> {

    private final I id;

    private final JsonSerializer<I> serializer;

    public ObjectIdSerializer( I id, JsonSerializer<I> serializer ) {
        this.id = id;
        this.serializer = serializer;
    }

    public void serializeId( JsonWriter writer, JsonEncodingContext ctx ) {
        serializer.encode( writer, id, ctx );
    }
}
