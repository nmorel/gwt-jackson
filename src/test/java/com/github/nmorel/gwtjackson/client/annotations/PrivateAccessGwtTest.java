package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.annotations.PrivateAccessTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class PrivateAccessGwtTest extends GwtJacksonTestCase
{
    public interface PrivateAccessMapper extends JsonMapper<PrivateAccessTester.PrivateBean>
    {
    }

    public void testEncodingPrivateField()
    {
        PrivateAccessTester.INSTANCE.testEncodingPrivateField( new JsonEncoderTester<PrivateAccessTester.PrivateBean>()
        {
            @Override
            public String encode( PrivateAccessTester.PrivateBean input )
            {
                return GWT.<PrivateAccessMapper>create( PrivateAccessMapper.class ).encode( input );
            }
        } );
    }

    public void testDecodingPrivateField()
    {
        PrivateAccessTester.INSTANCE.testDecodingPrivateField( new JsonDecoderTester<PrivateAccessTester.PrivateBean>()
        {
            @Override
            public PrivateAccessTester.PrivateBean decode( String input )
            {
                return GWT.<PrivateAccessMapper>create( PrivateAccessMapper.class ).decode( input );
            }
        } );
    }
}
