package com.github.nmorel.gwtjackson.shared.advanced.jsontype;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * Tests to verify [JACKSON-437], [JACKSON-762]
 */
public final class VisibleTypeIdTester extends AbstractTester {

    // type id as property, exposed
    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type", visible = true )
    @JsonTypeName( "BaseType" )
    public static class PropertyBean {

        public int a = 3;

        protected String type;

        public void setType( String t ) { type = t; }
    }

    // as wrapper-array
    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY,
        property = "type", visible = true )
    @JsonTypeName( "ArrayType" )
    public static class WrapperArrayBean {

        public int a = 1;

        protected String type;

        public void setType( String t ) { type = t; }
    }

    // as wrapper-object
    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type", visible = true )
    @JsonTypeName( "ObjectType" )
    public static class WrapperObjectBean {

        public int a = 2;

        protected String type;

        public void setType( String t ) { type = t; }
    }

    // as external id, bit trickier
    public static class ExternalIdWrapper {

        @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type", visible = true )
        public ExternalIdBean bean = new ExternalIdBean();
    }

    @JsonTypeName( "ExternalType" )
    public static class ExternalIdBean {

        public int a = 2;

        protected String type;

        public void setType( String t ) { type = t; }
    }

    // // // [JACKSON-762]: type id from property

    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type" )
    public static class TypeIdFromFieldProperty {

        public int a = 3;

        @JsonTypeId
        public String type = "SomeType";
    }

    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY,
        property = "type" )
    public static class TypeIdFromFieldArray {

        public int a = 3;

        @JsonTypeId
        public String type = "SomeType";
    }

    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type" )
    public static class TypeIdFromMethodObject {

        public int a = 3;

        @JsonTypeId
        public String getType() { return "SomeType"; }
    }

    public static class ExternalIdWrapper2 {

        @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type", visible = true )
        public ExternalIdBean2 bean = new ExternalIdBean2();
    }

    public static class ExternalIdBean2 {

        public int a = 2;

        /* Type id property itself can not be external, as it is conceptually
         * part of the bean for which info is written:
         */
        @JsonTypeId
        public String getType() { return "SomeType"; }
    }

    // Invalid definition: multiple type ids
    public static class MultipleIds {

        @JsonTypeId
        public String type1 = "type1";

        @JsonTypeId
        public String getType2() { return "type2"; }
    }

    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "name" )
    @JsonSubTypes( {@JsonSubTypes.Type( value = I263Impl.class )} )
    public static abstract class I263Base {

        @JsonTypeId
        public abstract String getName();
    }

    @JsonPropertyOrder( {"age", "name"} )
    @JsonTypeName( "bob" )
    public static class I263Impl extends I263Base {

        public int age = 41;

        @Override
        public String getName() { return "bob"; }
    }

    public static final VisibleTypeIdTester INSTANCE = new VisibleTypeIdTester();

    private VisibleTypeIdTester() {
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    public void testVisibleWithProperty( ObjectMapperTester<PropertyBean> mapper ) {
        String json = mapper.write( new PropertyBean() );
        // just default behavior:
        assertEquals( "{\"type\":\"BaseType\",\"a\":3}", json );
        // but then expect to read it back
        PropertyBean result = mapper.read( json );
        assertEquals( "BaseType", result.type );

        // also, should work with order reversed
        result = mapper.read( "{\"a\":7, \"type\":\"BaseType\"}" );
        assertEquals( 7, result.a );
        assertEquals( "BaseType", result.type );
    }

    public void testVisibleWithWrapperArray( ObjectMapperTester<WrapperArrayBean> mapper ) {
        String json = mapper.write( new WrapperArrayBean() );
        // just default behavior:
        assertEquals( "[\"ArrayType\",{\"a\":1}]", json );
        // but then expect to read it back
        WrapperArrayBean result = mapper.read( json );
        assertEquals( "ArrayType", result.type );
        assertEquals( 1, result.a );
    }

    public void testVisibleWithWrapperObject( ObjectMapperTester<WrapperObjectBean> mapper ) {
        String json = mapper.write( new WrapperObjectBean() );
        assertEquals( "{\"ObjectType\":{\"a\":2}}", json );
        // but then expect to read it back
        WrapperObjectBean result = mapper.read( json );
        assertEquals( "ObjectType", result.type );
    }

    public void testVisibleWithExternalId( ObjectMapperTester<ExternalIdWrapper> mapper ) {
        String json = mapper.write( new ExternalIdWrapper() );
        // but then expect to read it back
        ExternalIdWrapper result = mapper.read( json );
        assertEquals( "ExternalType", result.bean.type );
        assertEquals( 2, result.bean.a );
    }

    // [JACKSON-762]

    public void testTypeIdFromProperty( ObjectWriterTester<TypeIdFromFieldProperty> writer ) {
        assertEquals( "{\"type\":\"SomeType\",\"a\":3}", writer.write( new TypeIdFromFieldProperty() ) );
    }

    public void testTypeIdFromArray( ObjectWriterTester<TypeIdFromFieldArray> writer ) {
        assertEquals( "[\"SomeType\",{\"a\":3}]", writer.write( new TypeIdFromFieldArray() ) );
    }

    public void testTypeIdFromObject( ObjectWriterTester<TypeIdFromMethodObject> writer ) {
        assertEquals( "{\"SomeType\":{\"a\":3}}", writer.write( new TypeIdFromMethodObject() ) );
    }

    public void testTypeIdFromExternal( ObjectWriterTester<ExternalIdWrapper2> writer ) {
        String json = writer.write( new ExternalIdWrapper2() );
        // Implementation detail: type id written AFTER value, due to constraints
        assertEquals( "{\"bean\":{\"a\":2},\"type\":\"SomeType\"}", json );
    }

    public void testIssue263( ObjectMapperTester<I263Base> mapper ) {
        // first, serialize:
        assertEquals( "{\"name\":\"bob\",\"age\":41}", mapper.write( new I263Impl() ) );

        // then bring back:
        I263Base result = mapper.read( "{\"age\":19,\"name\":\"bob\"}" );
        assertTrue( result instanceof I263Impl );
        assertEquals( 19, ((I263Impl) result).age );
    }

    /*
    /**********************************************************
    /* Unit tests, fails
    /**********************************************************
     */

    public void testInvalidMultipleTypeIds( ObjectMapperTester<MultipleIds> writer ) {
        try {
            writer.write( new MultipleIds() );
            fail( "Should have failed" );
        } catch ( JsonSerializationException e ) {
            // expected
        }
    }
}
