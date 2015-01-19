package com.github.nmorel.gwtjackson.hello.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.reinert.requestor.Json;

@Json
public class GreetingRequest {

    private String name;

    public GreetingRequest( @JsonProperty("name") String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
