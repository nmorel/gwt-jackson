package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester.BeanWithIgnoredProperties;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonIgnoreGwtTest extends GwtJacksonTestCase {

    public interface JsonIgnoreMapper extends ObjectMapper<BeanWithIgnoredProperties>, JsonMapperTester<BeanWithIgnoredProperties> {

        static JsonIgnoreMapper INSTANCE = GWT.create( JsonIgnoreMapper.class );
    }

    private JsonIgnoreTester tester = JsonIgnoreTester.INSTANCE;

    public void testEncoding() {
        tester.testEncoding( JsonIgnoreMapper.INSTANCE );
    }

    public void testDecoding() {
        tester.testDecoding( JsonIgnoreMapper.INSTANCE );
    }
}
