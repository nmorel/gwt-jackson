package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyConstant;
import com.google.appengine.api.datastore.Key;

public class RawKeyJsonSerializer extends JsonSerializer<Key> {

    private static final RawKeyJsonSerializer INSTANCE = new RawKeyJsonSerializer();

    public static RawKeyJsonSerializer getInstance() {
        return INSTANCE;
    }

    private RawKeyJsonSerializer() { }

    @Override
    protected void doSerialize( JsonWriter writer, Key key, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.beginObject();
        writer.name( RawKeyConstant.PARENT );
        serialize( writer, key.getParent(), ctx, params );
        writer.name( RawKeyConstant.KIND ).value( key.getKind() );
        writer.name( RawKeyConstant.ID ).value( key.getId() );
        writer.name( RawKeyConstant.NAME ).value( key.getName() );
        writer.endObject();
    }
}
