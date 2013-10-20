package com.github.nmorel.gwtjackson.shared.advanced.jsontype;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * Testing to verify that {@link JsonTypeInfo} works
 * for properties as well as types (see [JACKSON-280] for details)
 */
public final class JsonTypeOnPropertiesTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper types
    /**********************************************************
     */

    public static class FieldWrapperBean {

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public StringWrapper value;

        public FieldWrapperBean() { }

        public FieldWrapperBean( StringWrapper o ) { value = o; }
    }

    public static class FieldWrapperBeanList {

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public ArrayList<FieldWrapperBean> beans;

        public FieldWrapperBeanList() { }

        public FieldWrapperBeanList( ArrayList<FieldWrapperBean> beans ) { this.beans = beans; }
    }

    public static class FieldWrapperBeanMap {

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public HashMap<String, FieldWrapperBean> beans;

        public FieldWrapperBeanMap() { }

        public FieldWrapperBeanMap( HashMap<String, FieldWrapperBean> beans ) { this.beans = beans; }
    }

    public static class FieldWrapperBeanArray {

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public FieldWrapperBean[] beans;

        public FieldWrapperBeanArray() { }

        public FieldWrapperBeanArray( FieldWrapperBean[] beans ) { this.beans = beans; }
    }

    public static class MethodWrapperBean {

        private IntWrapper value;

        public MethodWrapperBean() { }

        public MethodWrapperBean( IntWrapper o ) { value = o; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public IntWrapper getValue() { return value; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public void setValue( IntWrapper v ) { value = v; }
    }

    public static class MethodWrapperBeanList {

        protected ArrayList<MethodWrapperBean> beans;

        public MethodWrapperBeanList() { }

        public MethodWrapperBeanList( ArrayList<MethodWrapperBean> beans ) { this.beans = beans; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public ArrayList<MethodWrapperBean> getValue() { return beans; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public void setValue( ArrayList<MethodWrapperBean> v ) { beans = v; }
    }

    public static class MethodWrapperBeanMap {

        protected HashMap<String, MethodWrapperBean> beans;

        public MethodWrapperBeanMap() { }

        public MethodWrapperBeanMap( HashMap<String, MethodWrapperBean> beans ) { this.beans = beans; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public HashMap<String, MethodWrapperBean> getValue() { return beans; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public void setValue( HashMap<String, MethodWrapperBean> v ) { beans = v; }
    }

    public static class MethodWrapperBeanArray {

        protected MethodWrapperBean[] beans;

        public MethodWrapperBeanArray() { }

        public MethodWrapperBeanArray( MethodWrapperBean[] beans ) { this.beans = beans; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public MethodWrapperBean[] getValue() { return beans; }

        @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
        public void setValue( MethodWrapperBean[] v ) { beans = v; }
    }

    public static final JsonTypeOnPropertiesTester INSTANCE = new JsonTypeOnPropertiesTester();

    private JsonTypeOnPropertiesTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testSimpleField( ObjectMapperTester<FieldWrapperBean> mapper ) {
        String json = mapper.write( new FieldWrapperBean( new StringWrapper( "foo" ) ) );

        FieldWrapperBean bean = mapper.read( json );
        assertNotNull( bean.value );
        assertEquals( bean.value.str, "foo" );
    }

    public void testSimpleMethod( ObjectMapperTester<MethodWrapperBean> mapper ) {
        String json = mapper.write( new MethodWrapperBean( new IntWrapper( 10 ) ) );

        MethodWrapperBean bean = mapper.read( json );
        assertNotNull( bean.value );
        assertEquals( bean.value.i, 10 );
    }

    public void testSimpleListField( ObjectMapperTester<FieldWrapperBeanList> mapper ) {
        ArrayList<FieldWrapperBean> list = new ArrayList<FieldWrapperBean>();
        list.add( new FieldWrapperBean( new StringWrapper( "foo" ) ) );
        FieldWrapperBeanList beanList = new FieldWrapperBeanList( list );

        String json = mapper.write( beanList );

        FieldWrapperBeanList result = mapper.read( json );
        assertNotNull( result );
        assertEquals( 1, result.beans.size() );
        FieldWrapperBean bean = result.beans.get( 0 );
        assertEquals( bean.value.str, "foo" );
    }

    public void testSimpleListMethod( ObjectMapperTester<MethodWrapperBeanList> mapper ) {
        ArrayList<MethodWrapperBean> list = new ArrayList<MethodWrapperBean>();
        list.add( new MethodWrapperBean( new IntWrapper( 1 ) ) );
        list.add( new MethodWrapperBean( new IntWrapper( 2 ) ) );
        list.add( new MethodWrapperBean( new IntWrapper( 3 ) ) );
        MethodWrapperBeanList beanList = new MethodWrapperBeanList( list );

        String json = mapper.write( beanList );

        MethodWrapperBeanList result = mapper.read( json );
        assertNotNull( result );
        assertEquals( 3, result.beans.size() );
        assertEquals( result.beans.get( 0 ).value.i, 1 );
        assertEquals( result.beans.get( 1 ).value.i, 2 );
        assertEquals( result.beans.get( 2 ).value.i, 3 );
    }

    public void testSimpleArrayField( ObjectMapperTester<FieldWrapperBeanArray> mapper ) {
        FieldWrapperBeanArray array = new FieldWrapperBeanArray( new FieldWrapperBean[]{new FieldWrapperBean( new StringWrapper( "foo" )
        )} );

        String json = mapper.write( array );

        FieldWrapperBeanArray result = mapper.read( json );
        assertNotNull( result );
        FieldWrapperBean[] beans = result.beans;
        assertEquals( 1, beans.length );
        FieldWrapperBean bean = beans[0];
        assertEquals( bean.value.str, "foo" );
    }

    public void testSimpleArrayMethod( ObjectMapperTester<MethodWrapperBeanArray> mapper ) {
        MethodWrapperBeanArray array = new MethodWrapperBeanArray( new MethodWrapperBean[]{new MethodWrapperBean( new IntWrapper( 15 ) )} );

        String json = mapper.write( array );

        MethodWrapperBeanArray result = mapper.read( json );
        assertNotNull( result );
        MethodWrapperBean[] beans = result.beans;
        assertEquals( 1, beans.length );
        MethodWrapperBean bean = beans[0];
        assertEquals( bean.value.i, 15 );
    }

    public void testSimpleMapField( ObjectMapperTester<FieldWrapperBeanMap> mapper ) {
        HashMap<String, FieldWrapperBean> map = new HashMap<String, FieldWrapperBean>();
        map.put( "foo", new FieldWrapperBean( new StringWrapper( "bar" ) ) );
        FieldWrapperBeanMap beanMap = new FieldWrapperBeanMap( map );

        String json = mapper.write( beanMap );

        FieldWrapperBeanMap result = mapper.read( json );
        assertNotNull( result );
        assertEquals( 1, result.beans.size() );
        FieldWrapperBean bean = result.beans.get( "foo" );
        assertNotNull( bean );
        assertEquals( bean.value.str, "bar" );
    }

    public void testSimpleMapMethod( ObjectMapperTester<MethodWrapperBeanMap> mapper ) {
        HashMap<String, MethodWrapperBean> map = new HashMap<String, MethodWrapperBean>();
        map.put( "xyz", new MethodWrapperBean( new IntWrapper( 105 ) ) );
        MethodWrapperBeanMap beanMap = new MethodWrapperBeanMap( map );

        String json = mapper.write( beanMap );

        MethodWrapperBeanMap result = mapper.read( json );
        assertNotNull( result );
        assertEquals( 1, result.beans.size() );
        MethodWrapperBean bean = result.beans.get( "xyz" );
        assertNotNull( bean );
        assertEquals( bean.value.i, 105 );
    }
}
