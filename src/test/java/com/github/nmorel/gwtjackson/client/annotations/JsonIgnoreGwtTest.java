package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonIgnoreGwtTest extends GwtJacksonTestCase
{
    public interface JsonIgnoreMapper extends JsonMapper<JsonIgnoreTester.BeanWithIgnoredProperties>{

    }

    public void testEncoding()
    {
        JsonIgnoreTester.INSTANCE.testEncoding( new JsonEncoderTester<JsonIgnoreTester.BeanWithIgnoredProperties>()
        {
            @Override
            public String encode( JsonIgnoreTester.BeanWithIgnoredProperties input )
            {
                return GWT.<JsonIgnoreMapper>create( JsonIgnoreMapper.class ).encode( input );
            }
        } );
    }

    public void testDecoding()
    {
        JsonIgnoreTester.INSTANCE.testDecoding( new JsonDecoderTester<JsonIgnoreTester.BeanWithIgnoredProperties>()
        {
            @Override
            public JsonIgnoreTester.BeanWithIgnoredProperties decode( String input )
            {
                return GWT.<JsonIgnoreMapper>create( JsonIgnoreMapper.class ).decode( input );
            }
        } );
    }
}
