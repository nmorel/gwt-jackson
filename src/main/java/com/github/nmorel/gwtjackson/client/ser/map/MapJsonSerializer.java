package com.github.nmorel.gwtjackson.client.ser.map;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for {@link Map}.
 *
 * @param <M> Type of the {@link Map}
 * @param <K> Type of the keys inside the {@link Map}
 * @param <V> Type of the values inside the {@link Map}
 *
 * @author Nicolas Morel
 */
public class MapJsonSerializer<M extends Map<K, V>, K, V> extends JsonSerializer<M> {

    /**
     * @param keySerializer {@link KeySerializer} used to serialize the keys.
     * @param valueSerializer {@link JsonSerializer} used to serialize the values.
     * @param <M> Type of the {@link Map}
     * @param <K> Type of the keys inside the {@link Map}
     * @param <V> Type of the values inside the {@link Map}
     *
     * @return a new instance of {@link MapJsonSerializer}
     */
    public static <M extends Map<K, V>, K, V> MapJsonSerializer<M, K, V> newInstance( KeySerializer<K> keySerializer,
                                                                                      JsonSerializer<V> valueSerializer ) {
        return new MapJsonSerializer<M, K, V>( keySerializer, valueSerializer );
    }

    protected final KeySerializer<K> keySerializer;

    protected final JsonSerializer<V> valueSerializer;

    /**
     * @param keySerializer {@link KeySerializer} used to serialize the keys.
     * @param valueSerializer {@link JsonSerializer} used to serialize the values.
     */
    protected MapJsonSerializer( KeySerializer<K> keySerializer, JsonSerializer<V> valueSerializer ) {
        if ( null == keySerializer ) {
            throw new IllegalArgumentException( "keySerializer cannot be null" );
        }
        if ( null == valueSerializer ) {
            throw new IllegalArgumentException( "valueSerializer cannot be null" );
        }
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull M values, JsonSerializationContext ctx ) throws IOException {
        writer.beginObject();

        if ( ctx.isWriteNullMapValues() ) {

            for ( Entry<K, V> entry : values.entrySet() ) {
                writer.name( keySerializer.serialize( entry.getKey(), ctx ) );
                valueSerializer.serialize( writer, entry.getValue(), ctx );
            }

        } else {

            for ( Entry<K, V> entry : values.entrySet() ) {
                if ( null != entry.getValue() ) {
                    writer.name( keySerializer.serialize( entry.getKey(), ctx ) );
                    valueSerializer.serialize( writer, entry.getValue(), ctx );
                }
            }

        }

        writer.endObject();
    }
}
