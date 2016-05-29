package com.github.nmorel.gwtjackson.hello.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GreetingRequest {

    private String name;

    public GreetingRequest( @JsonProperty("name") String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
