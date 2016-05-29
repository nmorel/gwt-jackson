/*
 * Copyright 2014 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.shared.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * @author Nicolas Morel.
 */
public class GenericsAndInheritanceTester extends AbstractTester {

    @JsonTypeInfo( property = "class", include = As.PROPERTY, use = Id.CLASS )
    @JsonPropertyOrder( alphabetic = true )
    public static class Result<T> {

        private String exceptionMessage;

        private T payload;

        public String getExceptionMessage() {
            return exceptionMessage;
        }

        public void setExceptionMessage( String exceptionMessage ) {
            this.exceptionMessage = exceptionMessage;
        }

        public T getPayload() {
            return payload;
        }

        public void setPayload( T payload ) {
            this.payload = payload;
        }
    }

    public static class GetResult<V extends Number, K> extends Result<K> {

        private String aString;

        private V aNumber;

        public String getaString() {
            return aString;
        }

        public void setaString( String aString ) {
            this.aString = aString;
        }

        public V getaNumber() {
            return aNumber;
        }

        public void setaNumber( V aNumber ) {
            this.aNumber = aNumber;
        }
    }

    public static class IntegerResult extends Result<Integer> {}

    //##### With Map

    @JsonTypeInfo( use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY )
    public static class Owner {

        List<Parent<?>> parents = new ArrayList<Parent<?>>();

        public List<Parent<?>> getParents() {
            return parents;
        }

        public void setParents( List<Parent<?>> parents ) {
            this.parents = parents;
        }

    }

    @JsonTypeInfo( use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY )
    public static class Parent<T> {

        Child<T> child;

        Map<T, T> typed = new HashMap<T, T>();

        Map untyped = new HashMap();

        public Child<T> getChild() {
            return child;
        }

        public void setChild( Child<T> child ) {
            this.child = child;
        }

        public Map<T, T> getTyped() {
            return typed;
        }

        public void setTyped( Map<T, T> typed ) {
            this.typed = typed;
        }

        public Map getUntyped() {
            return untyped;
        }

        public void setUntyped( Map untyped ) {
            this.untyped = untyped;
        }
    }

    public static class IntegerParent extends Parent<Integer> {}

    public static class StringParent extends Parent<String> {}

    public static class Child<T> {

        Map<T, T> typed = new HashMap<T, T>();

        Map untyped = new HashMap();

        public Map<T, T> getTyped() {
            return typed;
        }

        public void setTyped( Map<T, T> typed ) {
            this.typed = typed;
        }

        public Map getUntyped() {
            return untyped;
        }

        public void setUntyped( Map untyped ) {
            this.untyped = untyped;
        }

    }

    public static final GenericsAndInheritanceTester INSTANCE = new GenericsAndInheritanceTester();

    private GenericsAndInheritanceTester() {
    }

    public void test( ObjectMapperTester<Result<Integer>[]> mapper ) {
        Result<Integer>[] result = mapper.read( "[\n" +
                "  {\n" +
                "    \"class\":\"com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester$Result\",\n" +
                "    \"exceptionMessage\":null,\n" +
                "    \"payload\":10" +
                "  },\n" +
                "  {\n" +
                "    \"class\":\"com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester$GetResult\",\n" +
                "    \"exceptionMessage\":\"anException\",\n" +
                "    \"payload\":45,\n" +
                "    \"aString\":\"aSuperString\",\n" +
                "    \"aNumber\":28.8\n" +
                "  },\n" +
                "  {\n" +
                "    \"class\":\"com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester$IntegerResult\",\n" +
                "    \"payload\":70\n" +
                "  }\n" +
                "]" );

        assertEquals( 3, result.length );

        assertEquals( Result.class, result[0].getClass() );
        assertNull( result[0].getExceptionMessage() );
        assertEquals( 10, result[0].getPayload().intValue() );

        assertEquals( GetResult.class, result[1].getClass() );
        assertEquals( "anException", result[1].getExceptionMessage() );
        assertEquals( 45, result[1].getPayload().intValue() );
        assertEquals( "aSuperString", ((GetResult<Double, Integer>) result[1]).getaString() );
        assertEquals( 28.8, ((GetResult<Double, Integer>) result[1]).getaNumber() );

        assertEquals( IntegerResult.class, result[2].getClass() );
        assertNull( result[2].getExceptionMessage() );
        assertEquals( 70, result[2].getPayload().intValue() );

        assertEquals( "[" +
                "{" +
                "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester$Result\"," +
                "\"exceptionMessage\":null," +
                "\"payload\":10" +
                "}," +
                "{" +
                "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester$GetResult\"," +
                "\"aNumber\":28.8," +
                "\"aString\":\"aSuperString\"," +
                "\"exceptionMessage\":\"anException\"," +
                "\"payload\":45" +
                "}," +
                "{" +
                "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester$IntegerResult\"," +
                "\"exceptionMessage\":null," +
                "\"payload\":70" +
                "}]", mapper.write( result ) );
    }

    public void testMap( ObjectMapperTester<Owner> mapper ) {
        Owner owner = new Owner();

        StringParent sp = new StringParent();
        sp.setTyped( createMap( "1", "1" ) );
        sp.setUntyped( createMap( "1", "1" ) );

        Child<String> sc = new Child<String>();
        sc.setTyped( createMap( "1", "1" ) );
        sc.setUntyped( createMap( "1", "1" ) );
        sp.setChild( sc );

        IntegerParent ip = new IntegerParent();
        ip.setTyped( createMap( 1, 1 ) );
        ip.setUntyped( createMap( 1, 1 ) );

        Child<Integer> ic = new Child<Integer>();
        ic.setTyped( createMap( 1, 1 ) );
        ic.setUntyped( createMap( 1, 1 ) );
        ip.setChild( ic );

        owner.getParents().add( sp );
        owner.getParents().add( ip );

        String json = mapper.write( owner );
        assertEquals( "{" +
                "\"@c\":\".GenericsAndInheritanceTester$Owner\"," +
                "\"parents\":[" +
                "{" +
                "\"@c\":\".GenericsAndInheritanceTester$StringParent\"," +
                "\"child\":{" +
                "\"typed\":{\"1\":\"1\"},\"untyped\":{\"1\":\"1\"}" +
                "}," +
                "\"typed\":{\"1\":\"1\"}," +
                "\"untyped\":{\"1\":\"1\"}" +
                "}," +
                "{" +
                "\"@c\":\".GenericsAndInheritanceTester$IntegerParent\"," +
                "\"child\":{" +
                "\"typed\":{\"1\":1}," +
                "\"untyped\":{\"1\":1}" +
                "}," +
                "\"typed\":{\"1\":1}," +
                "\"untyped\":{\"1\":1}" +
                "}" +
                "]}", json );

        owner = mapper.read( json );

        sp = (StringParent) owner.getParents().get( 0 );
        ip = (IntegerParent) owner.getParents().get( 1 );

        // Raw map : keys are strings
        assertEquals( sp.getUntyped().get( "1" ), "1" );
        assertEquals( sp.getChild().getUntyped().get( "1" ), "1" );
        assertEquals( ip.getUntyped().get( "1" ), 1 );
        assertEquals( ip.getChild().getUntyped().get( "1" ), 1 );

        // Typed map : keys are integer or string
        assertEquals( sp.getTyped().get( "1" ), "1" );
        assertEquals( sp.getChild().getTyped().get( "1" ), "1" );
        assertEquals( ip.getTyped().get( 1 ), (Integer) 1 );
        assertEquals( ip.getChild().getTyped().get( 1 ), (Integer) 1 );
    }

    private static <T> Map<T, T> createMap( T t1, T t2 ) {
        Map<T, T> map = new HashMap<T, T>();
        map.put( t1, t2 );
        return map;
    }

}
