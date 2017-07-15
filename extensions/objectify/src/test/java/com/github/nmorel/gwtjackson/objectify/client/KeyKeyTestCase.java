package com.github.nmorel.gwtjackson.objectify.client;

import java.io.IOException;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Key;
import org.junit.Test;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP2_JSON;

public class KeyKeyTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    @Test
    public void testKey1() throws IOException {
        assertEquals( KEY_MAP1_JSON, KeyKeyMapper.INSTANCE.write( KEY_MAP1 ) );

        assertEquals( KEY_MAP1, KeyKeyMapper.INSTANCE.read( KEY_MAP1_JSON ) );
    }

    @Test
    public void testKey2() throws IOException {
        assertEquals( KEY_MAP2_JSON, KeyKeyMapper.INSTANCE.write( KEY_MAP2 ) );

        assertEquals( KEY_MAP2, KeyKeyMapper.INSTANCE.read( KEY_MAP2_JSON ) );
    }

    public interface KeyKeyMapper extends ObjectMapper<Map<Key<Object>, Object>> {

        KeyKeyMapper INSTANCE = GWT.create( KeyKeyMapper.class );
    }
}
