package com.github.nmorel.gwtjackson.objectify.server;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.Bean;
import com.github.nmorel.gwtjackson.objectify.shared.JsonConstant;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.DeadRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.BEAN1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.BEAN_REF_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY2_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.KEY_MAP2_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY2_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY_MAP1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY_MAP1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY_MAP2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.RAW_KEY_MAP2_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF2_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP1;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP1_JSON;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP2;
import static com.github.nmorel.gwtjackson.objectify.shared.JsonConstant.REF_MAP2_JSON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ObjectifyJacksonModuleTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper( new LocalDatastoreServiceTestConfig() );

    private ObjectMapper objectMapper;

    private Closeable closeable;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        closeable = ObjectifyService.begin();
        ObjectifyService.register( Bean.class );
        objectMapper = new ObjectMapper();
        objectMapper.registerModule( new ObjectifyJacksonModule() );

        ObjectifyService.ofy().save().entity( JsonConstant.BEAN ).now();
    }

    @Test
    public void testRawKey1() throws IOException {
        assertEquals( RAW_KEY1_JSON, objectMapper.writeValueAsString( RAW_KEY1 ) );

        assertEquals( RAW_KEY1, objectMapper.readValue( RAW_KEY1_JSON, com.google.appengine.api.datastore.Key.class ) );
    }

    @Test
    public void testRawKey2() throws IOException {
        assertEquals( RAW_KEY2_JSON, objectMapper.writeValueAsString( RAW_KEY2 ) );

        assertEquals( RAW_KEY2, objectMapper.readValue( RAW_KEY2_JSON, com.google.appengine.api.datastore.Key.class ) );
    }

    @Test
    public void testKey1() throws IOException {
        assertEquals( KEY1_JSON, objectMapper.writeValueAsString( KEY1 ) );

        assertEquals( KEY1, objectMapper.readValue( KEY1_JSON, Key.class ) );
    }

    @Test
    public void testKey2() throws IOException {
        assertEquals( KEY2_JSON, objectMapper.writeValueAsString( KEY2 ) );

        assertEquals( KEY2, objectMapper.readValue( KEY2_JSON, Key.class ) );
    }

    @Test
    public void testRef() throws IOException {
        expectedEx.expect( JsonMappingException.class );
        expectedEx.expectMessage( "No content to map due to end-of-input" );
        objectMapper.readValue( "", Ref.class );
    }

    @Test
    public void testRef1() throws IOException {
        assertEquals( REF1_JSON, objectMapper.writeValueAsString( REF1 ) );

        Ref<Bean> _ref1 = objectMapper.readValue( REF1_JSON, new TypeReference<Ref<Object>>() {} );
        assertEquals( REF1, _ref1 );
        assertTrue( _ref1 instanceof DeadRef );
        assertNull( _ref1.getValue() );
    }

    @Test
    public void testRef2() throws IOException {
        assertEquals( REF2_JSON, objectMapper.writeValueAsString( REF2 ) );

        Ref<Bean> _ref2 = objectMapper.readValue( REF2_JSON, new TypeReference<Ref<Object>>() {} );
        assertEquals( REF2, _ref2 );
        assertTrue( _ref2 instanceof DeadRef );
        assertNull( _ref2.getValue() );
    }

    @Test
    public void testBean() throws IOException {
        assertEquals( BEAN1_JSON, objectMapper.writeValueAsString( JsonConstant.BEAN ) );

        Bean _bean1 = objectMapper.readValue( BEAN1_JSON, Bean.class );
        assertEquals( JsonConstant.BEAN, _bean1 );
    }

    @Test
    public void testBeanRef() throws IOException {
        Ref<Bean> beanRef = Ref.create( JsonConstant.BEAN );
        assertEquals( BEAN_REF_JSON, objectMapper.writeValueAsString( beanRef ) );

        Ref<Bean> _beanRef = objectMapper.readValue( BEAN_REF_JSON, new TypeReference<Ref<Bean>>() {} );
        assertEquals( beanRef, _beanRef );
        assertEquals( beanRef.getValue(), _beanRef.getValue() );
    }

    @Test
    public void testRawKeyMap1() throws IOException {
        assertEquals( RAW_KEY_MAP1_JSON, objectMapper.writeValueAsString( RAW_KEY_MAP1 ) );

        assertEquals( RAW_KEY_MAP1, objectMapper
                .readValue( RAW_KEY_MAP1_JSON, new TypeReference<Map<com.google.appengine.api.datastore.Key, Object>>() {} ) );
    }

    @Test
    public void testRawKeyMap2() throws IOException {
        assertEquals( RAW_KEY_MAP2_JSON, objectMapper.writeValueAsString( RAW_KEY_MAP2 ) );

        assertEquals( RAW_KEY_MAP2, objectMapper
                .readValue( RAW_KEY_MAP2_JSON, new TypeReference<Map<com.google.appengine.api.datastore.Key, Object>>() {} ) );
    }

    @Test
    public void testKeyMap1() throws IOException {
        assertEquals( KEY_MAP1_JSON, objectMapper.writeValueAsString( KEY_MAP1 ) );

        assertEquals( KEY_MAP1, objectMapper.readValue( KEY_MAP1_JSON, new TypeReference<Map<Key<Object>, Object>>() {} ) );
    }

    @Test
    public void testKeyMap2() throws IOException {
        assertEquals( KEY_MAP2_JSON, objectMapper.writeValueAsString( KEY_MAP2 ) );

        assertEquals( KEY_MAP2, objectMapper.readValue( KEY_MAP2_JSON, new TypeReference<Map<Key<Object>, Object>>() {} ) );
    }

    @Test
    public void testRefMap1() throws IOException {
        assertEquals( REF_MAP1_JSON, objectMapper.writeValueAsString( REF_MAP1 ) );

        assertEquals( REF_MAP1, objectMapper.readValue( REF_MAP1_JSON, new TypeReference<Map<Ref<Object>, Object>>() {} ) );
    }

    @Test
    public void testRefMap2() throws IOException {
        assertEquals( REF_MAP2_JSON, objectMapper.writeValueAsString( REF_MAP2 ) );

        assertEquals( REF_MAP2, objectMapper.readValue( REF_MAP2_JSON, new TypeReference<Map<Ref<Object>, Object>>() {} ) );
    }

    @After
    public void tearDown() throws IOException {
        closeable.close();
        helper.tearDown();
    }
}
