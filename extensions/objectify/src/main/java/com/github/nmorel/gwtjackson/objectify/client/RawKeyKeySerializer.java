package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.appengine.api.datastore.Key;

public class RawKeyKeySerializer extends KeySerializer<Key> {

    private static final RawKeyKeySerializer INSTANCE = new RawKeyKeySerializer();

    public static RawKeyKeySerializer getInstance() {
        return INSTANCE;
    }

    private RawKeyKeySerializer() { }

    @Override
    protected String doSerialize( Key value, JsonSerializationContext ctx ) {
        JsonWriter jsonWriter = ctx.newJsonWriter();
        RawKeyJsonSerializer.getInstance().serialize( jsonWriter, value, ctx );
        jsonWriter.close();
        return jsonWriter.getOutput();
    }
}