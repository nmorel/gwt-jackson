package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismNoTypeInfoTester;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismNoTypeInfoTester.Employee;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismNoTypeInfoTester.Person;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class PolymorphismNoTypeInfoGwtTest extends GwtJacksonTestCase {

    public interface PolymorphismPersonMapper extends ObjectMapper<Person[]>, JsonMapperTester<Person[]> {

        static PolymorphismPersonMapper INSTANCE = GWT.create( PolymorphismPersonMapper.class );
    }

    public interface PolymorphismEmployeeMapper extends ObjectMapper<Employee[]>, JsonMapperTester<Employee[]> {

        static PolymorphismEmployeeMapper INSTANCE = GWT.create( PolymorphismEmployeeMapper.class );
    }

    private PolymorphismNoTypeInfoTester tester = PolymorphismNoTypeInfoTester.INSTANCE;

    public void testEncoding() {
        tester.testEncoding( PolymorphismPersonMapper.INSTANCE );
    }

    public void testDecodingNonInstantiableBean() {
        tester.testDecodingNonInstantiableBean( PolymorphismPersonMapper.INSTANCE );
    }

    public void testDecodingInstantiableBean() {
        tester.testDecodingInstantiableBean( PolymorphismEmployeeMapper.INSTANCE );
    }
}
