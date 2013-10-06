package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;

/**
 * Default {@link KeyDeserializer} implementation for {@link Boolean}.
 *
 * @author Nicolas Morel
 */
public final class BooleanKeyDeserializer extends KeyDeserializer<Boolean> {

    private static final BooleanKeyDeserializer INSTANCE = new BooleanKeyDeserializer();

    /**
     * @return an instance of {@link BooleanKeyDeserializer}
     */
    public static BooleanKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private BooleanKeyDeserializer() { }

    @Override
    protected Boolean doDeserialize( String key, JsonDeserializationContext ctx ) {
        return Boolean.valueOf( key );
    }
}
