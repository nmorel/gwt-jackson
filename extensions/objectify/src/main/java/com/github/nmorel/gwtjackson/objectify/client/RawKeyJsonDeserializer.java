package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyConstant;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class RawKeyJsonDeserializer extends JsonDeserializer<Key> {

    private static final RawKeyJsonDeserializer INSTANCE = new RawKeyJsonDeserializer();

    public static RawKeyJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private RawKeyJsonDeserializer() { }

    @Override
    protected Key doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        reader.beginObject();
        if ( !RawKeyConstant.PARENT.equals( reader.nextName() ) ) {
            throw ctx.traceError( "Missing " + RawKeyConstant.PARENT + " property.", reader );
        }
        Key parent = deserialize( reader, ctx );
        if ( !RawKeyConstant.KIND.equals( reader.nextName() ) ) {
            throw ctx.traceError( "Missing " + RawKeyConstant.KIND + " property.", reader );
        }
        String kind = reader.nextString();
        if ( !RawKeyConstant.ID.equals( reader.nextName() ) ) {
            throw ctx.traceError( "Missing " + RawKeyConstant.ID + " property.", reader );
        }
        Long id = null;
        try {
            id = reader.nextLong();
        } catch ( Exception e ) {
            reader.nextNull();
        }
        if ( !RawKeyConstant.NAME.equals( reader.nextName() ) ) {
            throw ctx.traceError( "Missing " + RawKeyConstant.NAME + " property.", reader );
        }
        String name = null;
        try {
            name = reader.nextString();
        } catch ( Exception e ) {
            reader.nextNull();
        }
        reader.endObject();

        if ( id != 0 ) {
            return KeyFactory.createKey( parent, kind, id );
        }

        return KeyFactory.createKey( parent, kind, name );
    }
}
