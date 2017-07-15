package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.objectify.shared.RefConstant;
import com.googlecode.objectify.Ref;

public class RefJsonSerializer<T> extends JsonSerializer<Ref<T>> {

    public static <T> RefJsonSerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new RefJsonSerializer<>( serializer );
    }

    private final JsonSerializer<T> serializer;

    private RefJsonSerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    protected void doSerialize( JsonWriter writer, Ref<T> ref, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.beginObject();
        writer.name( RefConstant.KEY );
        KeyJsonSerializer.getInstance().serialize( writer, ref.getKey(), ctx, params );
        writer.name( RefConstant.VALUE );
        serializer.serialize( writer, ref.getValue(), ctx, params );
        writer.endObject();
    }
}