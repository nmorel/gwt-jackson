package com.github.nmorel.gwtjackson.objectify.client;

import java.io.IOException;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Ref;
import org.junit.Test;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP2_JSON;

public class RefKeyTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    @Test
    public void testKey1() throws IOException {
        assertEquals( REF_MAP1_JSON, KeyKeyMapper.INSTANCE.write( REF_MAP1 ) );

        assertEquals( REF_MAP1, KeyKeyMapper.INSTANCE.read( REF_MAP1_JSON ) );
    }

    @Test
    public void testKey2() throws IOException {
        assertEquals( REF_MAP2_JSON, KeyKeyMapper.INSTANCE.write( REF_MAP2 ) );

        assertEquals( REF_MAP2, KeyKeyMapper.INSTANCE.read( REF_MAP2_JSON ) );
    }

    public interface KeyKeyMapper extends ObjectMapper<Map<Ref<Object>, Object>> {

        KeyKeyMapper INSTANCE = GWT.create( KeyKeyMapper.class );
    }
}
