package com.github.nmorel.gwtjackson.jackson.client;

import com.github.nmorel.gwtjackson.jackson.client.ser.JacksonJsonSerializer;
import com.github.nmorel.gwtjackson.jackson.shared.Person;
import com.github.nmorel.gwtjackson.jackson.shared.PersonSerializer;

public class ClientPersonSerializer extends JacksonJsonSerializer<Person> {

    public ClientPersonSerializer() {
        super(new PersonSerializer());
    }

}
