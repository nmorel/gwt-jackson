package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdMinimalClassAsWrapperArrayTester;
import com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdMinimalClassAsWrapperArrayTester.Person;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class PolymorphismIdMinimalClassAsWrapperArrayGwtTest extends GwtJacksonTestCase {

    public interface PolymorphismMapper extends ObjectMapper<Person[]>, JsonMapperTester<Person[]> {

        static PolymorphismMapper INSTANCE = GWT.create( PolymorphismMapper.class );
    }

    private PolymorphismIdMinimalClassAsWrapperArrayTester tester = PolymorphismIdMinimalClassAsWrapperArrayTester.INSTANCE;

    public void testEncoding() {
        tester.testEncoding( PolymorphismMapper.INSTANCE );
    }

    public void testDecoding() {
        tester.testDecoding( PolymorphismMapper.INSTANCE );
    }
}
