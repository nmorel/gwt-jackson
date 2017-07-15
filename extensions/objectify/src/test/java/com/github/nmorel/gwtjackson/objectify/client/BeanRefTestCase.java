package com.github.nmorel.gwtjackson.objectify.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.Bean;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Ref;
import org.junit.Test;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.BEAN_REF;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.BEAN_REF_JSON;

public class BeanRefTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    @Test
    public void testBeanRef() throws IOException {
        assertEquals( BEAN_REF_JSON, BeanRefMapper.INSTANCE.write( BEAN_REF ) );

        Ref<Bean> _beanRef = BeanRefMapper.INSTANCE.read( BEAN_REF_JSON );
        assertEquals( BEAN_REF, _beanRef );
        assertEquals( BEAN_REF.getValue(), _beanRef.getValue() );
    }

    public interface BeanRefMapper extends ObjectMapper<Ref<Bean>> {

        BeanRefMapper INSTANCE = GWT.create( BeanRefMapper.class );
    }

}
