package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.advanced.PrivateAccessTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class PrivateAccessGwtTest extends GwtJacksonTestCase
{
    public interface PrivateAccessMapper extends JsonMapper<PrivateAccessTester.PrivateBean>
    {
        static PrivateAccessMapper INSTANCE = GWT.create( PrivateAccessMapper.class );
    }

    private PrivateAccessTester tester = PrivateAccessTester.INSTANCE;

    public void testEncodingPrivateField()
    {
        tester.testEncodingPrivateField( createEncoder( PrivateAccessMapper.INSTANCE ) );
    }

    public void testDecodingPrivateField()
    {
        tester.testDecodingPrivateField( createDecoder( PrivateAccessMapper.INSTANCE ) );
    }
}
