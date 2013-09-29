package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester.BeanWithIgnoredProperties;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonIgnoreGwtTest extends GwtJacksonTestCase {

    public interface JsonIgnoreMapper extends ObjectMapper<BeanWithIgnoredProperties>, ObjectMapperTester<BeanWithIgnoredProperties> {

        static JsonIgnoreMapper INSTANCE = GWT.create( JsonIgnoreMapper.class );
    }

    private JsonIgnoreTester tester = JsonIgnoreTester.INSTANCE;

    public void testSerialize() {
        tester.testSerialize( JsonIgnoreMapper.INSTANCE );
    }

    public void testDeserialize() {
        tester.testDeserialize( JsonIgnoreMapper.INSTANCE );
    }
}
