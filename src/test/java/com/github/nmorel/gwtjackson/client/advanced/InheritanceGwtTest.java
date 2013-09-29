package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.InheritanceTester;
import com.github.nmorel.gwtjackson.shared.advanced.InheritanceTester.ChildBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class InheritanceGwtTest extends GwtJacksonTestCase {

    public interface InheritanceMapper extends ObjectMapper<ChildBean>, ObjectMapperTester<ChildBean> {

        static InheritanceMapper INSTANCE = GWT.create( InheritanceMapper.class );
    }

    private InheritanceTester tester = InheritanceTester.INSTANCE;

    public void testSerializePrivateField() {
        tester.testSerialize( InheritanceMapper.INSTANCE );
    }

    public void testDeserializePrivateField() {
        tester.testDeserialize( InheritanceMapper.INSTANCE );
    }
}
