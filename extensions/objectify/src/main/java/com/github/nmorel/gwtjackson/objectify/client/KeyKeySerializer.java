package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.googlecode.objectify.Key;

public class KeyKeySerializer<T> extends KeySerializer<Key<T>> {

    private static final KeyKeySerializer INSTANCE = new KeyKeySerializer();

    public static KeyKeySerializer getInstance() {
        return INSTANCE;
    }

    private KeyKeySerializer() { }

    @Override
    protected String doSerialize( Key<T> value, JsonSerializationContext ctx ) {
        JsonWriter jsonWriter = ctx.newJsonWriter();
        KeyJsonSerializer.getInstance().serialize( jsonWriter, value, ctx );
        jsonWriter.close();
        return jsonWriter.getOutput();
    }
}