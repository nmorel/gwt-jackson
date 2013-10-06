package com.github.nmorel.gwtjackson.client.deser.map;

import java.io.IOException;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base {@link JsonDeserializer} implementation for {@link Map}.
 *
 * @param <M> Type of the {@link Map}
 * @param <K> Type of the keys inside the {@link Map}
 * @param <V> Type of the values inside the {@link Map}
 *
 * @author Nicolas Morel
 */
public abstract class BaseMapJsonDeserializer<M extends Map<K, V>, K, V> extends JsonDeserializer<M> {

    /**
     * {@link KeyDeserializer} used to deserialize the keys.
     */
    protected final KeyDeserializer<K> keyDeserializer;

    /**
     * {@link JsonDeserializer} used to deserialize the values.
     */
    protected final JsonDeserializer<V> valueDeserializer;

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    protected BaseMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        if ( null == keyDeserializer ) {
            throw new IllegalArgumentException( "keyDeserializer cannot be null" );
        }
        if ( null == valueDeserializer ) {
            throw new IllegalArgumentException( "valueDeserializer cannot be null" );
        }
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
    }

    @Override
    public M doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        M result = newMap();

        reader.beginObject();
        while ( JsonToken.END_OBJECT != reader.peek() ) {
            String name = reader.nextName();
            K key = keyDeserializer.deserialize( name, ctx );
            V value = valueDeserializer.deserialize( reader, ctx );
            result.put( key, value );
        }
        reader.endObject();

        return result;
    }

    /**
     * Instantiates a new map for deserialization process.
     *
     * @return the new map
     */
    protected abstract M newMap();

    @Override
    public void setBackReference( String referenceName, Object reference, M value, JsonDeserializationContext ctx ) {
        if ( null != value ) {
            for ( V val : value.values() ) {
                valueDeserializer.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
