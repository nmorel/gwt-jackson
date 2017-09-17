package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.googlecode.objectify.Key;

public class KeyKeySerializer<T> extends KeySerializer<Key<T>> {

    public static <T> KeyKeySerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new KeyKeySerializer<>( serializer );
    }

    private final KeyJsonSerializer<T> serializer;

    private KeyKeySerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = KeyJsonSerializer.newInstance( serializer );
    }

    @Override
    protected String doSerialize( Key<T> value, JsonSerializationContext ctx ) {
        JsonWriter jsonWriter = ctx.newJsonWriter();
        this.serializer.serialize( jsonWriter, value, ctx );
        jsonWriter.close();
        return jsonWriter.getOutput();
    }
}
