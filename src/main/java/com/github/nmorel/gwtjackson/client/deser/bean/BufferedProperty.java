package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Class used to store a property value in order to process it later
 *
 * @author Nicolas Morel
 */
public class BufferedProperty<T, B extends InstanceBuilder<T>, V> {

    private final BeanPropertyDeserializer<T, B, V> deserializer;

    private final String propertyValue;

    public BufferedProperty( BeanPropertyDeserializer<T, B, V> deserializer, String propertyValue ) {
        this.deserializer = deserializer;
        this.propertyValue = propertyValue;
    }

    public V flush( B builder, JsonDeserializationContext ctx ) {
        JsonReader reader = new JsonReader( propertyValue );
        // has to be lenient
        reader.setLenient( true );
        return deserializer.deserialize( reader, builder, ctx );
    }
}
