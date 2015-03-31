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

    @JsonTypeInfo( include = As.PROPERTY, use = Id.CLASS )
    public static interface IdentifiableEntity<T extends IdentifiableEntity<T>> {

        Long getId();

        T setId( Long id );
    }

    public static class Entity implements IdentifiableEntity<Entity> {

        public Long id;

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public Entity setId( Long id ) {
            this.id = id;
            return this;
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

    public void testRecursiveInheritance( ObjectMapperTester<IdentifiableEntity<Entity>> mapper ) {

    }

}
