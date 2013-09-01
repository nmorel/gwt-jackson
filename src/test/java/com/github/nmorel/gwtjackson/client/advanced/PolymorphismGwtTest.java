package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class PolymorphismGwtTest extends GwtJacksonTestCase
{
    public interface PolymorphismMapper extends JsonMapper<PolymorphismTester.Person[]>
    {
        static PolymorphismMapper INSTANCE = GWT.create( PolymorphismMapper.class );
    }

    private PolymorphismTester tester = PolymorphismTester.INSTANCE;

    public void testEncoding()
    {
        tester.testEncoding( createEncoder( PolymorphismMapper.INSTANCE ) );
    }

    public void testDecoding()
    {
        tester.testDecoding( createDecoder( PolymorphismMapper.INSTANCE ) );
    }
}
