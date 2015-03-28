/*
 * Copyright 2015 Nicolas Morel
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

package com.github.nmorel.gwtjackson.shared.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * @author Nicolas Morel
 */
public final class AutoDetectionTester extends AbstractTester {

    @JsonPropertyOrder( alphabetic = true )
    public static class AutoDetectionBeanBuilder {

        // Differents from getter/setter naming to be sure jackson doesn't use the field
        private String aStringValue;

        private int anIntValue;

        private long aLongValue;

        public String getStringValue() {
            return aStringValue;
        }

        public AutoDetectionBeanBuilder setStringValue( String stringValue ) {
            this.aStringValue = stringValue;
            return this;
        }

        @JsonProperty // will for the detection of this getter
        public int intValue() {
            return anIntValue;
        }

        public AutoDetectionBeanBuilder intValue( int intValue ) {
            this.anIntValue = intValue;
            return this;
        }

        public long longValue() {
            return aLongValue;
        }

        @JsonProperty // will for the detection of this setter
        public AutoDetectionBeanBuilder longValue( long longValue ) {
            this.aLongValue = longValue;
            return this;
        }

        public AutoDetectionBean build() {
            return new AutoDetectionBean( aStringValue, anIntValue, aLongValue );
        }
    }

    public static class AutoDetectionBean {

        private final String stringValue;

        private final int intValue;

        private final long longValue;

        public AutoDetectionBean( String stringValue, int intValue, long longValue ) {
            this.stringValue = stringValue;
            this.intValue = intValue;
            this.longValue = longValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public int getIntValue() {
            return intValue;
        }

        public long getLongValue() {
            return longValue;
        }
    }

    public static final AutoDetectionTester INSTANCE = new AutoDetectionTester();

    private AutoDetectionTester() {

    }

    public void test( ObjectMapperTester<AutoDetectionBeanBuilder> mapper ) {
        AutoDetectionBeanBuilder builder = new AutoDetectionBeanBuilder();
        builder.setStringValue( "Hello" ).intValue( 487 ).longValue( 987l );

        String json = mapper.write( builder );
        // we get intValue because the getter is annotated with @JsonProperty
        assertEquals( "{\"intValue\":487,\"stringValue\":\"Hello\"}", json );

        builder = mapper.read( "{\"stringValue\":\"Hello World\",\"longValue\":874}" );

        AutoDetectionBean bean = builder.build();

        assertEquals( "Hello World", bean.getStringValue() );
        assertEquals( 0, bean.getIntValue() );
        assertEquals( 874l, bean.getLongValue() );

        try {
            mapper.read( "{\"stringValue\":\"Hello World\",\"intValue\":8451,\"longValue\":874}" );
            fail( "intValue should be unknown in deserialization process" );
        } catch ( JsonDeserializationException e ) {
        }
    }
}
