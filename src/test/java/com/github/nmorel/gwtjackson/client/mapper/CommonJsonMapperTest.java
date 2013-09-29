package com.github.nmorel.gwtjackson.client.mapper;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractObjectReader;
import com.github.nmorel.gwtjackson.client.AbstractObjectWriter;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
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
        ObjectReader<String> reader = new AbstractObjectReader<String>() {
            @Override
            protected JsonDeserializer<String> newDeserializer( JsonDeserializationContext ctx ) {
                return new JsonDeserializer<String>() {
                    @Override
                    protected String doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
        try {
            reader.read( "\"fail\"" );
            fail();
        } catch ( JsonDeserializationException e ) {
            assertTrue( e.getCause() instanceof UnsupportedOperationException );
        }
    }

    public void testDeserializeIOException() {
        ObjectReader<String> reader = new AbstractObjectReader<String>() {
            @Override
            protected JsonDeserializer<String> newDeserializer( JsonDeserializationContext ctx ) {
                return new JsonDeserializer<String>() {
                    @Override
                    protected String doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
                        throw new IOException();
                    }
                };
            }
        };
        try {
            reader.read( "\"fail\"" );
            fail();
        } catch ( JsonDeserializationException e ) {
            assertTrue( e.getCause() instanceof IOException );
        }
    }

    public void testDeserializeDecodingException() {
        final JsonDeserializationException jsonDeserializationException = new JsonDeserializationException();
        ObjectReader<String> reader = new AbstractObjectReader<String>() {
            @Override
            protected JsonDeserializer<String> newDeserializer( JsonDeserializationContext ctx ) {
                return new JsonDeserializer<String>() {
                    @Override
                    protected String doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
                        throw jsonDeserializationException;
                    }
                };
            }
        };
        try {
            reader.read( "\"fail\"" );
            fail();
        } catch ( JsonDeserializationException e ) {
            assertSame( jsonDeserializationException, e );
        }
    }

    public void testSerializeUnexpectedException() {
        ObjectWriter<String> writer = new AbstractObjectWriter<String>() {
            @Override
            protected JsonSerializer<String> newSerializer( JsonSerializationContext ctx ) {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doSerialize( JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx ) throws
                        IOException {
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
        ObjectWriter<String> writer = new AbstractObjectWriter<String>() {
            @Override
            protected JsonSerializer<String> newSerializer( JsonSerializationContext ctx ) {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doSerialize( JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx ) throws
                        IOException {
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
        ObjectWriter<String> writer = new AbstractObjectWriter<String>() {
            @Override
            protected JsonSerializer<String> newSerializer( JsonSerializationContext ctx ) {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doSerialize( JsonWriter writer, @Nonnull String value, JsonSerializationContext ctx ) throws
                        IOException {
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
