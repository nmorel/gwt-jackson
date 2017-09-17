package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.googlecode.objectify.Ref;

public class RefKeyDeserializer<T> extends KeyDeserializer<Ref<T>> {

    public static <T> RefKeyDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new RefKeyDeserializer<T>( deserializer );
    }

    private final JsonDeserializer<T> deserializer;

    private RefKeyDeserializer(JsonDeserializer<T> deserializer) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer can't be null" );
        }
        this.deserializer = deserializer;
    }

    @Override
    protected Ref<T> doDeserialize( String key, JsonDeserializationContext ctx ) {
        JsonReader jsonReader = ctx.newJsonReader( key );
        return RefJsonDeserializer.<T>newInstance( this.deserializer ).deserialize( jsonReader, ctx );
    }
}
