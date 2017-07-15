package com.github.nmorel.gwtjackson.objectify.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.DeadRef;
import org.junit.Test;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF2_JSON;

public class RefTestCase extends GwtJacksonTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectifyTest";
    }

    @Test
    public void testRef1() throws IOException {
        assertEquals( REF1_JSON, RefMapper.INSTANCE.write( REF1 ) );

        Ref<?> _ref1 = RefMapper.INSTANCE.read( REF1_JSON );
        assertEquals( REF1, _ref1 );
        assertTrue( _ref1 instanceof DeadRef );
        assertNull( _ref1.getValue() );
    }

    @Test
    public void testRef2() throws IOException {
        assertEquals( REF2_JSON, RefMapper.INSTANCE.write( REF2 ) );

        Ref<?> _ref2 = RefMapper.INSTANCE.read( REF2_JSON );
        assertEquals( REF2, _ref2 );
        assertTrue( _ref2 instanceof DeadRef );
        assertNull( _ref2.getValue() );
    }

    public interface RefMapper extends ObjectMapper<Ref<Object>> {

        RefMapper INSTANCE = GWT.create( RefMapper.class );
    }
}
