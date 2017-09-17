package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.googlecode.objectify.Ref;

public class RefKeyDeserializer<T> extends KeyDeserializer<Ref<T>> {

    private static final RefKeyDeserializer INSTANCE = new RefKeyDeserializer();

    public static RefKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private RefKeyDeserializer() { }

    @Override
    protected Ref<T> doDeserialize( String key, JsonDeserializationContext ctx ) {
        JsonReader jsonReader = ctx.newJsonReader( key );
        return RefJsonDeserializer.<T>newInstance( null ).deserialize( jsonReader, ctx );
    }
}