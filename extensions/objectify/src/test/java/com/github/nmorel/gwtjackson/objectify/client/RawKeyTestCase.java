package com.github.nmorel.gwtjackson.objectify.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import org.junit.Test;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY2_JSON;

public class RawKeyTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    @Test
    public void testRawKey1() throws IOException {
        assertEquals( RAW_KEY1_JSON, RawKeyMapper.INSTANCE.write( RAW_KEY1 ) );

        assertEquals( RAW_KEY1, RawKeyMapper.INSTANCE.read( RAW_KEY1_JSON ) );
    }

    @Test
    public void testRawKey2() throws IOException {
        assertEquals( RAW_KEY2_JSON, RawKeyMapper.INSTANCE.write( RAW_KEY2 ) );

        assertEquals( RAW_KEY2, RawKeyMapper.INSTANCE.read( RAW_KEY2_JSON ) );
    }

    public interface RawKeyMapper extends ObjectMapper<com.google.appengine.api.datastore.Key> {

        RawKeyMapper INSTANCE = GWT.create( RawKeyMapper.class );
    }
}
