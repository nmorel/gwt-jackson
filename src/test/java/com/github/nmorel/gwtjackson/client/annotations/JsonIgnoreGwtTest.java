package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonIgnoreGwtTest extends GwtJacksonTestCase
{
    public interface JsonIgnoreMapper extends JsonMapper<JsonIgnoreTester.BeanWithIgnoredProperties>
    {
        static JsonIgnoreMapper INSTANCE = GWT.create( JsonIgnoreMapper.class );
    }

    private JsonIgnoreTester tester = JsonIgnoreTester.INSTANCE;

    public void testEncoding()
    {
        tester.testEncoding( createEncoder( JsonIgnoreMapper.INSTANCE ) );
    }

    public void testDecoding()
    {
        tester.testDecoding( createDecoder( JsonIgnoreMapper.INSTANCE ) );
    }
}
