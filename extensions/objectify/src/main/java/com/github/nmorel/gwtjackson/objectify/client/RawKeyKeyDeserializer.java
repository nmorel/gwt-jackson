package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.appengine.api.datastore.Key;

public class RawKeyKeyDeserializer extends KeyDeserializer<Key> {

    private static final RawKeyKeyDeserializer INSTANCE = new RawKeyKeyDeserializer();

    public static RawKeyKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private RawKeyKeyDeserializer() { }

    @Override
    protected Key doDeserialize( String key, JsonDeserializationContext ctx ) {
        JsonReader jsonReader = ctx.newJsonReader( key );
        return RawKeyJsonDeserializer.getInstance().deserialize( jsonReader, ctx );
    }
}