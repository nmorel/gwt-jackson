package com.github.nmorel.gwtjackson.jackson.rebind;

import com.github.nmorel.gwtjackson.client.AbstractConfiguration;
import com.github.nmorel.gwtjackson.jackson.client.ClientPersonDeserializer;
import com.github.nmorel.gwtjackson.jackson.client.ClientPersonSerializer;
import com.github.nmorel.gwtjackson.jackson.shared.Person;

public class GwtJacksonConfiguration extends AbstractConfiguration {

    @Override
    protected void configure() {
        type(Person.class).serializer(ClientPersonSerializer.class).deserializer(ClientPersonDeserializer.class);
    }

}
