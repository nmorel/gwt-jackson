package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Contains the id of a bean and a serializer to facilitate the serialization of the next instances of the object.
 *
 * @author Nicolas Morel
 */
public class ObjectIdSerializer<I> {

    private final I id;

    private final JsonSerializer<I> serializer;

    public ObjectIdSerializer( I id, JsonSerializer<I> serializer ) {
        this.id = id;
        this.serializer = serializer;
    }

    public void serializeId( JsonWriter writer, JsonSerializationContext ctx ) {
        serializer.serialize( writer, id, ctx );
    }
}
