package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.PrivateAccessTester;
import com.github.nmorel.gwtjackson.shared.advanced.PrivateAccessTester.PrivateBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class PrivateAccessGwtTest extends GwtJacksonTestCase {

    public interface PrivateAccessMapper extends ObjectMapper<PrivateBean>, JsonMapperTester<PrivateBean> {

        static PrivateAccessMapper INSTANCE = GWT.create( PrivateAccessMapper.class );
    }

    private PrivateAccessTester tester = PrivateAccessTester.INSTANCE;

    public void testEncodingPrivateField() {
        tester.testEncodingPrivateField( PrivateAccessMapper.INSTANCE );
    }

    public void testDecodingPrivateField() {
        tester.testDecodingPrivateField( PrivateAccessMapper.INSTANCE );
    }
}
