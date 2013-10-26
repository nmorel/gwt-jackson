package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Dummy {@link JsonSerializer} that will just output raw values by calling toString() on value to serialize.
 *
 * @author Nicolas Morel
 */
public class RawValueJsonSerializer<T> extends JsonSerializer<T> {

    private static final RawValueJsonSerializer<?> INSTANCE = new RawValueJsonSerializer();

    /**
     * @return an instance of {@link RawValueJsonSerializer}
     */
    @SuppressWarnings( "unchecked" )
    public static <T> RawValueJsonSerializer<T> getInstance() {
        return (RawValueJsonSerializer<T>) INSTANCE;
    }

    private RawValueJsonSerializer() { }

    @Override
    protected void doSerialize( JsonWriter writer, @Nonnull Object value, JsonSerializationContext ctx ) throws IOException {
        writer.rawValue( value );
    }
}
