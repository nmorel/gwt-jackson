package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.annotations.InheritanceTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class InheritanceGwtTest extends GwtJacksonTestCase
{
    public interface InheritanceMapper extends JsonMapper<InheritanceTester.ChildBean>
    {
    }

    public void testEncodingPrivateField()
    {
        InheritanceTester.INSTANCE.testEncoding( new JsonEncoderTester<InheritanceTester.ChildBean>()
        {
            @Override
            public String encode( InheritanceTester.ChildBean input )
            {
                return GWT.<InheritanceMapper>create( InheritanceMapper.class ).encode( input );
            }
        } );
    }

    public void testDecodingPrivateField()
    {
        InheritanceTester.INSTANCE.testDecoding( new JsonDecoderTester<InheritanceTester.ChildBean>()
        {
            @Override
            public InheritanceTester.ChildBean decode( String input )
            {
                return GWT.<InheritanceMapper>create( InheritanceMapper.class ).decode( input );
            }
        } );
    }
}
