package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTypeTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonIgnoreTypeGwtTest extends GwtJacksonTestCase
{
    public interface JsonIgnoreTypeMapper extends JsonMapper<JsonIgnoreTypeTester.NonIgnoredType>
    {
        static JsonIgnoreTypeMapper INSTANCE = GWT.create( JsonIgnoreTypeMapper.class );
    }

    private JsonIgnoreTypeTester tester = JsonIgnoreTypeTester.INSTANCE;

    public void testEncode()
    {
        tester.testEncode( createEncoder( JsonIgnoreTypeMapper.INSTANCE ) );
    }

    public void testDecode()
    {
        tester.testDecode( createDecoder( JsonIgnoreTypeMapper.INSTANCE ) );
    }
}
