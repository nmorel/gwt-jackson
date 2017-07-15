package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.objectify.shared.KeyConstant;
import com.googlecode.objectify.Key;

public class KeyJsonDeserializer<T> extends JsonDeserializer<Key<T>> {

    private static final KeyJsonDeserializer INSTANCE = new KeyJsonDeserializer();

    public static <T> KeyJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return INSTANCE;
    }

    public static <T> KeyJsonDeserializer<T> getInstance() {
        return INSTANCE;
    }

    private KeyJsonDeserializer() {
    }

    @Override
    protected Key<T> doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        reader.beginObject();
        if ( !KeyConstant.RAW.equals( reader.nextName() ) ) {
            throw ctx.traceError( "Missing " + KeyConstant.RAW + " property.", reader );
        }
        com.google.appengine.api.datastore.Key raw = RawKeyJsonDeserializer.getInstance().deserialize( reader, ctx, params );
        reader.endObject();
        return Key.create( raw );
    }
}