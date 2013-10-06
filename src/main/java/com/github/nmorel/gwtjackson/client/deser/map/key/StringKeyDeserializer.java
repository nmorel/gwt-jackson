package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link KeyDeserializer} implementation for {@link String}.
 *
 * @author Nicolas Morel
 */
public final class StringKeyDeserializer extends KeyDeserializer<String> {

    private static final StringKeyDeserializer INSTANCE = new StringKeyDeserializer();

    /**
     * @return an instance of {@link StringKeyDeserializer}
     */
    public static StringKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private StringKeyDeserializer() { }

    @Override
    protected String doDeserialize( String key, JsonDeserializationContext ctx ) {
        return key;
    }
}
