package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.advanced.InheritanceTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class InheritanceGwtTest extends GwtJacksonTestCase
{
    public interface InheritanceMapper extends JsonMapper<InheritanceTester.ChildBean>
    {
        static InheritanceMapper INSTANCE = GWT.create( InheritanceMapper.class );
    }

    private InheritanceTester tester = InheritanceTester.INSTANCE;

    public void testEncodingPrivateField()
    {
        tester.testEncoding( createEncoder( InheritanceMapper.INSTANCE ) );
    }

    public void testDecodingPrivateField()
    {
        tester.testDecoding( createDecoder( InheritanceMapper.INSTANCE ) );
    }
}
