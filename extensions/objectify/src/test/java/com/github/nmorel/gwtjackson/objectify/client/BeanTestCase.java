package com.github.nmorel.gwtjackson.objectify.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.Bean;
import com.google.gwt.core.client.GWT;
import org.junit.Test;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.BEAN;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.BEAN1_JSON;

public class BeanTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    @Test
    public void testBean() throws IOException {
        assertEquals( BEAN1_JSON, BeanMapper.INSTANCE.write( BEAN ) );

        Bean _bean1 = BeanMapper.INSTANCE.read( BEAN1_JSON );
        assertEquals( BEAN, _bean1 );
    }

    public interface BeanMapper extends ObjectMapper<Bean> {

        BeanMapper INSTANCE = GWT.create( BeanMapper.class );
    }
}
