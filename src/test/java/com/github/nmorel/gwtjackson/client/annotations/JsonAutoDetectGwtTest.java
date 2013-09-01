package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonAutoDetectGwtTest extends GwtJacksonTestCase
{
    public interface JsonAutoDetectMapper extends JsonMapper<JsonAutoDetectTester.BeanOne>
    {
        static JsonAutoDetectMapper INSTANCE = GWT.create( JsonAutoDetectMapper.class );
    }

    private JsonAutoDetectTester tester = JsonAutoDetectTester.INSTANCE;

    public void testEncodingAutoDetection()
    {
        tester.testEncodingAutoDetection( createEncoder( JsonAutoDetectMapper.INSTANCE ) );
    }

    public void testDecodingAutoDetection()
    {
        tester.testDecodingAutoDetection( createDecoder( JsonAutoDetectMapper.INSTANCE ) );
    }
}
