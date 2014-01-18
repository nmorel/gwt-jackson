/*
 * Copyright 2013 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.mapper;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractObjectReader;
import com.github.nmorel.gwtjackson.client.AbstractObjectWriter;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public class CommonJsonMapperTest extends GwtJacksonTestCase {

    public void testDeserializeUnexpectedException() {
        ObjectReader<String[]> reader = new AbstractObjectReader<String[]>( null ) {
            @Override
            protected JsonDeserializer<String[]> newDeserializer() {
                return new JsonDeserializer<String[]>() {
                    @Override
                    protected String[] doDeserialize( JsonReader reader, JsonDeserializationContext ctx,
                                                      JsonDeserializerParameters params ) throws IOException {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
        try {
            reader.read( "[\"fail\"]" );
            fail();
        } catch ( JsonDeserializationException e ) {
            assertTrue( e.getCause() instanceof UnsupportedOperationException );
        }
    }

    public void testDeserializeIOException() {
        ObjectReader<String[]> reader = new AbstractObjectReader<String[]>( null ) {
            @Override
            protected JsonDeserializer<String[]> newDeserializer() {
                return new JsonDeserializer<String[]>() {
                    @Override
                    protected String[] doDeserialize( JsonReader reader, JsonDeserializationContext ctx,
                                                      JsonDeserializerParameters params ) throws IOException {
                        throw new IOException();
                    }
                };
            }
        };
        try {
            reader.read( "[\"fail\"]" );
            fail();
        } catch ( JsonDeserializationException e ) {
            assertTrue( e.getCause() instanceof IOException );
        }
    }

    public void testDeserializeDecodingException() {
        final JsonDeserializationException jsonDeserializationException = new JsonDeserializationException();
        ObjectReader<String[]> reader = new AbstractObjectReader<String[]>( null ) {
            @Override
            protected JsonDeserializer<String[]> newDeserializer() {
                return new JsonDeserializer<String[]>() {
                    @Override
                    protected String[] doDeserialize( JsonReader reader, JsonDeserializationContext ctx,
                                                      JsonDeserializerParameters params ) throws IOException {
                        throw jsonDeserializationException;
                    }
                };
            }
        };
        try {
            reader.read( "[\"fail\"]" );
            fail();
        } catch ( JsonDeserializationException e ) {
            assertSame( jsonDeserializationException, e );
        }
    }

    public void testSerializeUnexpectedException() {
        ObjectWriter<String> writer = new AbstractObjectWriter<String>( null ) {
            @Override
            protected JsonSerializer<String> newSerializer() {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doSerialize( JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx,
                                                JsonSerializerParameters params ) throws IOException {
                        throw new NullPointerException();
                    }
                };
            }
        };
        try {
            writer.write( "fail" );
            fail();
        } catch ( JsonSerializationException e ) {
            assertTrue( e.getCause() instanceof NullPointerException );
        }
    }

    public void testSerializeIOException() {
        ObjectWriter<String> writer = new AbstractObjectWriter<String>( null ) {
            @Override
            protected JsonSerializer<String> newSerializer() {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doSerialize( JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx,
                                                JsonSerializerParameters params ) throws IOException {
                        throw new IOException();
                    }
                };
            }
        };
        try {
            writer.write( "fail" );
            fail();
        } catch ( JsonSerializationException e ) {
            assertTrue( e.getCause() instanceof IOException );
        }
    }

    public void testSerializeEncodingException() {
        final JsonSerializationException jsonSerializationException = new JsonSerializationException();
        ObjectWriter<String> writer = new AbstractObjectWriter<String>( null ) {
            @Override
            protected JsonSerializer<String> newSerializer() {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doSerialize( JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx,
                                                JsonSerializerParameters params ) throws IOException {
                        throw jsonSerializationException;
                    }
                };
            }
        };
        try {
            writer.write( "fail" );
            fail();
        } catch ( JsonSerializationException e ) {
            assertSame( jsonSerializationException, e );
        }
    }
}
