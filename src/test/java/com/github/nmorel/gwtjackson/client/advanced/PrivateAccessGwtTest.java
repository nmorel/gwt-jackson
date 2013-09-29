package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.PrivateAccessTester;
import com.github.nmorel.gwtjackson.shared.advanced.PrivateAccessTester.PrivateBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class PrivateAccessGwtTest extends GwtJacksonTestCase {

    public interface PrivateAccessMapper extends ObjectMapper<PrivateBean>, ObjectMapperTester<PrivateBean> {

        static PrivateAccessMapper INSTANCE = GWT.create( PrivateAccessMapper.class );
    }

    private PrivateAccessTester tester = PrivateAccessTester.INSTANCE;

    public void testSerializePrivateField() {
        tester.testSerializePrivateField( PrivateAccessMapper.INSTANCE );
    }

    public void testDeserializePrivateField() {
        tester.testDeserializePrivateField( PrivateAccessMapper.INSTANCE );
    }
}
