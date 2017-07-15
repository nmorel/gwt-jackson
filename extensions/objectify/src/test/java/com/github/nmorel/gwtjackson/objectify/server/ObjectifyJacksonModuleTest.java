package com.github.nmorel.gwtjackson.objectify.server;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.objectify.shared.BeanRefTester;
import com.github.nmorel.gwtjackson.objectify.shared.BeanTester;
import com.github.nmorel.gwtjackson.objectify.shared.KeyKeyTester;
import com.github.nmorel.gwtjackson.objectify.shared.KeyTester;
import com.github.nmorel.gwtjackson.objectify.shared.ObjectifyAbstractTester;
import com.github.nmorel.gwtjackson.objectify.shared.ObjectifyAbstractTester.Bean;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyKeyTester;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyTester;
import com.github.nmorel.gwtjackson.objectify.shared.RefKeyTester;
import com.github.nmorel.gwtjackson.objectify.shared.RefTester;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ObjectifyJacksonModuleTest extends AbstractJacksonTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper( new LocalDatastoreServiceTestConfig() );

    private Closeable closeable;

    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    @Override
    public void setUp() {
        super.setUp();
        helper.setUp();
        closeable = ObjectifyService.begin();
        ObjectifyService.register( Bean.class );
        objectMapper.registerModule( new ObjectifyJacksonModule() );
        ObjectifyService.ofy().save().entity( ObjectifyAbstractTester.BEAN ).now();
    }

    @Test
    public void testRawKey() throws Exception {
        RawKeyTester.INSTANCE.testSerialize( createWriter( com.google.appengine.api.datastore.Key.class ) );
        RawKeyTester.INSTANCE.testDeserialize( createReader( com.google.appengine.api.datastore.Key.class ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        RawKeyTester.INSTANCE.testSerializeNonNull( createWriter( com.google.appengine.api.datastore.Key.class ) );
        RawKeyTester.INSTANCE.testDeserializeNonNull( createReader( com.google.appengine.api.datastore.Key.class ) );
    }

    @Test
    public void testKey() throws Exception {
        TypeReference<Key<Object>> typeReference = new TypeReference<Key<Object>>() {};
        KeyTester.INSTANCE.testSerialize( createWriter( typeReference ) );
        KeyTester.INSTANCE.testDeserialize( createReader( typeReference ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        KeyTester.INSTANCE.testSerializeNonNull( createWriter( typeReference ) );
        KeyTester.INSTANCE.testDeserializeNonNull( createReader( typeReference ) );
    }

    @Test
    public void testRefException() throws Exception {
        expectedEx.expect( JsonMappingException.class );
        expectedEx.expectMessage( "No content to map due to end-of-input" );
        objectMapper.readValue( "", Ref.class );
    }

    @Test
    public void testRef() throws Exception {
        TypeReference<Ref<Object>> typeReference = new TypeReference<Ref<Object>>() {};
        RefTester.INSTANCE.testSerialize( createWriter( typeReference ) );
        RefTester.INSTANCE.testDeserialize( createReader( typeReference ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        RefTester.INSTANCE.testSerializeNonNull( createWriter( typeReference ) );
        RefTester.INSTANCE.testDeserializeNonNull( createReader( typeReference ) );
    }

    @Test
    public void testBean() throws Exception {
        BeanTester.INSTANCE.testSerialize( createWriter( Bean.class ) );
        BeanTester.INSTANCE.testDeserialize( createReader( Bean.class ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        BeanTester.INSTANCE.testSerializeNonNull( createWriter( Bean.class ) );
        BeanTester.INSTANCE.testDeserializeNonNull( createReader( Bean.class ) );
    }

    @Test
    public void testBeanRef() throws Exception {
        TypeReference<Ref<Bean>> typeReference = new TypeReference<Ref<Bean>>() {};
        BeanRefTester.INSTANCE.testSerialize( createWriter( typeReference ) );
        BeanRefTester.INSTANCE.testDeserialize( createReader( typeReference ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        BeanRefTester.INSTANCE.testSerializeNonNull( createWriter( typeReference ) );
        BeanRefTester.INSTANCE.testDeserializeNonNull( createReader( typeReference ) );
    }

    @Test
    public void testRawKeyKey() throws Exception {
        TypeReference<Map<com.google.appengine.api.datastore.Key, Object>> typeReference = new TypeReference<Map<com.google.appengine.api
                .datastore.Key, Object>>() {};
        RawKeyKeyTester.INSTANCE.testSerialize( createWriter( typeReference ) );
        RawKeyKeyTester.INSTANCE.testDeserialize( createReader( typeReference ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        RawKeyKeyTester.INSTANCE.testSerializeNonNull( createWriter( typeReference ) );
        RawKeyKeyTester.INSTANCE.testDeserializeNonNull( createReader( typeReference ) );
    }

    @Test
    public void testKeyKey() throws Exception {
        TypeReference<Map<Key<Object>, Object>> typeReference = new TypeReference<Map<Key<Object>, Object>>() {};
        KeyKeyTester.INSTANCE.testSerialize( createWriter( typeReference ) );
        KeyKeyTester.INSTANCE.testDeserialize( createReader( typeReference ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        KeyKeyTester.INSTANCE.testSerializeNonNull( createWriter( typeReference ) );
        KeyKeyTester.INSTANCE.testDeserializeNonNull( createReader( typeReference ) );
    }

    @Test
    public void testRefKey() throws Exception {
        TypeReference<Map<Ref<Object>, Object>> typeReference = new TypeReference<Map<Ref<Object>, Object>>() {};
        RefKeyTester.INSTANCE.testSerialize( createWriter( typeReference ) );
        RefKeyTester.INSTANCE.testDeserialize( createReader( typeReference ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        RefKeyTester.INSTANCE.testSerializeNonNull( createWriter( typeReference ) );
        RefKeyTester.INSTANCE.testDeserializeNonNull( createReader( typeReference ) );
    }

    @After
    public void tearDown() throws IOException {
        closeable.close();
        helper.tearDown();
    }
}
