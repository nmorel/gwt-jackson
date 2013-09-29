package com.github.nmorel.gwtjackson.client.advanced.identity;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithPolymorphicTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithPolymorphicTester.Base;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectIdWithPolymorphicGwtTest extends GwtJacksonTestCase {

    public interface BaseMapper extends ObjectMapper<Base>, JsonMapperTester<Base> {

        static BaseMapper INSTANCE = GWT.create( BaseMapper.class );
    }

    private ObjectIdWithPolymorphicTester tester = ObjectIdWithPolymorphicTester.INSTANCE;

    public void testPolymorphicRoundtrip() {
        tester.testPolymorphicRoundtrip( BaseMapper.INSTANCE );
    }
}
