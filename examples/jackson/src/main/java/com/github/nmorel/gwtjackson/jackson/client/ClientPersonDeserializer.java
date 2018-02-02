package com.github.nmorel.gwtjackson.jackson.client;

import com.github.nmorel.gwtjackson.jackson.client.deser.JacksonJsonDeserializer;
import com.github.nmorel.gwtjackson.jackson.shared.Person;
import com.github.nmorel.gwtjackson.jackson.shared.PersonDeserializer;

public class ClientPersonDeserializer extends JacksonJsonDeserializer<Person> {

    public ClientPersonDeserializer() {
        super(new PersonDeserializer());
    }

}
