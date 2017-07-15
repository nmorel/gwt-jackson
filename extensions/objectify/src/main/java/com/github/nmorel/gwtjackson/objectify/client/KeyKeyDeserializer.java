package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.googlecode.objectify.Key;

public class KeyKeyDeserializer<T> extends KeyDeserializer<Key<T>> {

    private static final KeyKeyDeserializer INSTANCE = new KeyKeyDeserializer();

    public static KeyKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private KeyKeyDeserializer() { }

    @Override
    protected Key<T> doDeserialize( String key, JsonDeserializationContext ctx ) {
        JsonReader jsonReader = ctx.newJsonReader( key );
        return KeyJsonDeserializer.<T>getInstance().deserialize( jsonReader, ctx );
    }
}