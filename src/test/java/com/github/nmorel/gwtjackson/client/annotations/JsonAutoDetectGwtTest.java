package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonAutoDetectGwtTest extends GwtJacksonTestCase
{
    public interface JsonAutoDetectMapper extends JsonMapper<JsonAutoDetectTester.BeanOne>{

    }

    public void testEncodingAutoDetection()
    {
        JsonAutoDetectTester.INSTANCE.testEncodingAutoDetection( new JsonEncoderTester<JsonAutoDetectTester.BeanOne>()
        {
            @Override
            public String encode( JsonAutoDetectTester.BeanOne input )
            {
                return GWT.<JsonAutoDetectMapper>create( JsonAutoDetectMapper.class ).encode( input );
            }
        } );
    }

    public void testDecodingAutoDetection()
    {
        JsonAutoDetectTester.INSTANCE.testDecodingAutoDetection( new JsonDecoderTester<JsonAutoDetectTester.BeanOne>()
        {
            @Override
            public JsonAutoDetectTester.BeanOne decode( String input )
            {
                return GWT.<JsonAutoDetectMapper>create( JsonAutoDetectMapper.class ).decode( input );
            }
        } );
    }
}
