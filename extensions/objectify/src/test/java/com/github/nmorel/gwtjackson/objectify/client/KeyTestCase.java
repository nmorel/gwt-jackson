package com.github.nmorel.gwtjackson.objectify.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Key;
import org.junit.Test;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY2_JSON;

public class KeyTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    @Test
    public void testKey1() throws IOException {
        assertEquals( KEY1_JSON, KeyMapper.INSTANCE.write( KEY1 ) );

        assertEquals( KEY1, KeyMapper.INSTANCE.read( KEY1_JSON ) );
    }

    @Test
    public void testKey2() throws IOException {
        assertEquals( KEY2_JSON, KeyMapper.INSTANCE.write( KEY2 ) );

        assertEquals( KEY2, KeyMapper.INSTANCE.read( KEY2_JSON ) );
    }

    public interface KeyMapper extends ObjectMapper<Key<Object>> {

        KeyMapper INSTANCE = GWT.create( KeyMapper.class );
    }
}
