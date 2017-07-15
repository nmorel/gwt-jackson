package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.objectify.shared.RefConstant;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.DeadRef;

public class RefJsonDeserializer<T> extends JsonDeserializer<Ref<T>> {

    public static <T> RefJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new RefJsonDeserializer<>( deserializer );
    }

    private final JsonDeserializer<T> deserializer;

    private RefJsonDeserializer( JsonDeserializer<T> deserializer ) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer can't be null" );
        }
        this.deserializer = deserializer;
    }

    @Override
    protected Ref<T> doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        reader.beginObject();
        if ( !RefConstant.KEY.equals( reader.nextName() ) ) {
            throw ctx.traceError( "Missing " + RefConstant.KEY + " property.", reader );
        }
        Key<T> key = KeyJsonDeserializer.<T>getInstance().deserialize( reader, ctx, params );
        if ( !RefConstant.VALUE.equals( reader.nextName() ) ) {
            throw ctx.traceError( "Missing " + RefConstant.VALUE + " property.", reader );
        }
        T value = deserializer.deserialize( reader, ctx, params );
        reader.endObject();
        return new DeadRef<>( key, value );
    }
}