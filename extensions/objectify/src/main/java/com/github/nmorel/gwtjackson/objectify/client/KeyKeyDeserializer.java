package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.googlecode.objectify.Key;

public class KeyKeyDeserializer<T> extends KeyDeserializer<Key<T>> {

    public static <T> KeyKeyDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new KeyKeyDeserializer<T>( deserializer );
    }

    private final KeyJsonDeserializer<T> deserializer;

    private KeyKeyDeserializer(JsonDeserializer<T> deserializer) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer can't be null" );
        }
        this.deserializer = KeyJsonDeserializer.newInstance( deserializer );
    }

    @Override
    protected Key<T> doDeserialize( String key, JsonDeserializationContext ctx ) {
        JsonReader jsonReader = ctx.newJsonReader( key );
        return this.deserializer.deserialize( jsonReader, ctx );
    }
}
