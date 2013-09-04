package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsWrapperArrayTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class PolymorphismIdClassAsWrapperArrayGwtTest extends GwtJacksonTestCase
{
    public interface PolymorphismMapper extends JsonMapper<PolymorphismIdClassAsWrapperArrayTester.Person[]>
    {
        static PolymorphismMapper INSTANCE = GWT.create( PolymorphismMapper.class );
    }

    private PolymorphismIdClassAsWrapperArrayTester tester = PolymorphismIdClassAsWrapperArrayTester.INSTANCE;

    public void testEncoding()
    {
        tester.testEncoding( createEncoder( PolymorphismMapper.INSTANCE ) );
    }

    public void testDecoding()
    {
        tester.testDecoding( createDecoder( PolymorphismMapper.INSTANCE ) );
    }
}
