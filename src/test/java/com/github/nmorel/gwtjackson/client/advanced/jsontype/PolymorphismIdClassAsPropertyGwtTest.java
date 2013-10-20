package com.github.nmorel.gwtjackson.client.advanced.jsontype;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.PolymorphismIdClassAsPropertyTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.PolymorphismIdClassAsPropertyTester.Person;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class PolymorphismIdClassAsPropertyGwtTest extends GwtJacksonTestCase {

    public interface PolymorphismMapper extends ObjectMapper<Person[]>, ObjectMapperTester<Person[]> {

        static PolymorphismMapper INSTANCE = GWT.create( PolymorphismMapper.class );
    }

    private PolymorphismIdClassAsPropertyTester tester = PolymorphismIdClassAsPropertyTester.INSTANCE;

    public void testSerialize() {
        tester.testSerialize( PolymorphismMapper.INSTANCE );
    }

    public void testDeserialize() {
        tester.testDeserialize( PolymorphismMapper.INSTANCE );
    }
}
