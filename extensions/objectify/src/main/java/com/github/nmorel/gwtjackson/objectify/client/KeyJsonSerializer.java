package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.objectify.shared.KeyConstant;
import com.googlecode.objectify.Key;

public class KeyJsonSerializer<T> extends JsonSerializer<Key<T>> {

    private static final KeyJsonSerializer INSTANCE = new KeyJsonSerializer();

    public static <T> KeyJsonSerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return INSTANCE;
    }

    public static KeyJsonSerializer getInstance() {
        return INSTANCE;
    }

    private KeyJsonSerializer() {
    }

    @Override
    protected void doSerialize( JsonWriter writer, Key<T> key, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.beginObject();
        writer.name( KeyConstant.RAW );
        RawKeyJsonSerializer.getInstance().serialize( writer, key.getRaw(), ctx, params );
        writer.endObject();
    }
}