package com.github.nmorel.gwtjackson.client;

import com.google.gwt.junit.client.GWTTestCase;

/** @author Nicolas Morel */
public abstract class GwtJacksonTestCase extends GWTTestCase
{
    @Override
    public String getModuleName()
    {
        return "com.github.nmorel.gwtjackson.GwtJacksonTest";
    }
}
