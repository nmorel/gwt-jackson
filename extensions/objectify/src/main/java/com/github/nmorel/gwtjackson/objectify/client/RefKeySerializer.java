package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.googlecode.objectify.Ref;

public class RefKeySerializer<T> extends KeySerializer<Ref<T>> {

    public static <T> RefKeySerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new RefKeySerializer<>( serializer );
    }

    private final JsonSerializer<T> serializer;

    private RefKeySerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    protected String doSerialize( Ref<T> value, JsonSerializationContext ctx ) {
        JsonWriter jsonWriter = ctx.newJsonWriter();
        RefJsonSerializer.newInstance( serializer ).serialize( jsonWriter, value, ctx );
        jsonWriter.close();
        return jsonWriter.getOutput();
    }
}
