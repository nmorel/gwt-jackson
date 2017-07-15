package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

public abstract class GwtJacksonObjectifyTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    protected JsonSerializationContext createNonNullContext() {
        return JsonSerializationContext.builder().serializeNulls( false ).build();
    }
}
