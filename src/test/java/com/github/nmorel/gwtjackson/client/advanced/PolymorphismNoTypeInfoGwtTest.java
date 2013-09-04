package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismNoTypeInfoTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class PolymorphismNoTypeInfoGwtTest extends GwtJacksonTestCase
{
    public interface PolymorphismPersonMapper extends JsonMapper<PolymorphismNoTypeInfoTester.Person[]>
    {
        static PolymorphismPersonMapper INSTANCE = GWT.create( PolymorphismPersonMapper.class );
    }
    public interface PolymorphismEmployeeMapper extends JsonMapper<PolymorphismNoTypeInfoTester.Employee[]>
    {
        static PolymorphismEmployeeMapper INSTANCE = GWT.create( PolymorphismEmployeeMapper.class );
    }

    private PolymorphismNoTypeInfoTester tester = PolymorphismNoTypeInfoTester.INSTANCE;

    public void testEncoding()
    {
        tester.testEncoding( createEncoder( PolymorphismPersonMapper.INSTANCE ) );
    }

    public void testDecodingNonInstantiableBean()
    {
        tester.testDecodingNonInstantiableBean( createDecoder( PolymorphismPersonMapper.INSTANCE ) );
    }

    public void testDecodingInstantiableBean()
    {
        tester.testDecodingInstantiableBean( createDecoder( PolymorphismEmployeeMapper.INSTANCE ) );
    }
}
