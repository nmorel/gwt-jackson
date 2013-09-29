package com.github.nmorel.gwtjackson.client.mapper;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractObjectReader;
import com.github.nmorel.gwtjackson.client.AbstractObjectWriter;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public class CommonJsonMapperTest extends GwtJacksonTestCase {

    public void testDecodeUnexpectedException() {
        ObjectReader<String> mapper = new AbstractObjectReader<String>() {
            @Override
            protected JsonDeserializer<String> newDeserializer( JsonDecodingContext ctx ) {
                return new JsonDeserializer<String>() {
                    @Override
                    protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
        try {
            mapper.decode( "\"fail\"" );
            fail();
        } catch ( JsonDecodingException e ) {
            assertTrue( e.getCause() instanceof UnsupportedOperationException );
        }
    }

    public void testDecodeIOException() {
        ObjectReader<String> mapper = new AbstractObjectReader<String>() {
            @Override
            protected JsonDeserializer<String> newDeserializer( JsonDecodingContext ctx ) {
                return new JsonDeserializer<String>() {
                    @Override
                    protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
                        throw new IOException();
                    }
                };
            }
        };
        try {
            mapper.decode( "\"fail\"" );
            fail();
        } catch ( JsonDecodingException e ) {
            assertTrue( e.getCause() instanceof IOException );
        }
    }

    public void testDecodeDecodingException() {
        final JsonDecodingException jsonDecodingException = new JsonDecodingException();
        ObjectReader<String> mapper = new AbstractObjectReader<String>() {
            @Override
            protected JsonDeserializer<String> newDeserializer( JsonDecodingContext ctx ) {
                return new JsonDeserializer<String>() {
                    @Override
                    protected String doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
                        throw jsonDecodingException;
                    }
                };
            }
        };
        try {
            mapper.decode( "\"fail\"" );
            fail();
        } catch ( JsonDecodingException e ) {
            assertSame( jsonDecodingException, e );
        }
    }

    public void testEncodeUnexpectedException() {
        ObjectWriter<String> mapper = new AbstractObjectWriter<String>() {
            @Override
            protected JsonSerializer<String> newSerializer( JsonEncodingContext ctx ) {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doEncode( JsonWriter writer, @Nonnull String value, JsonEncodingContext ctx ) throws IOException {
                        throw new NullPointerException();
                    }
                };
            }
        };
        try {
            mapper.encode( "fail" );
            fail();
        } catch ( JsonEncodingException e ) {
            assertTrue( e.getCause() instanceof NullPointerException );
        }
    }

    public void testEncodeIOException() {
        ObjectWriter<String> mapper = new AbstractObjectWriter<String>() {
            @Override
            protected JsonSerializer<String> newSerializer( JsonEncodingContext ctx ) {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doEncode( JsonWriter writer, @Nonnull String value, JsonEncodingContext ctx ) throws IOException {
                        throw new IOException();
                    }
                };
            }
        };
        try {
            mapper.encode( "fail" );
            fail();
        } catch ( JsonEncodingException e ) {
            assertTrue( e.getCause() instanceof IOException );
        }
    }

    public void testEncodeEncodingException() {
        final JsonEncodingException jsonEncodingException = new JsonEncodingException();
        ObjectWriter<String> mapper = new AbstractObjectWriter<String>() {
            @Override
            protected JsonSerializer<String> newSerializer( JsonEncodingContext ctx ) {
                return new JsonSerializer<String>() {
                    @Override
                    protected void doEncode( JsonWriter writer, @Nonnull String value, JsonEncodingContext ctx ) throws IOException {
                        throw jsonEncodingException;
                    }
                };
            }
        };
        try {
            mapper.encode( "fail" );
            fail();
        } catch ( JsonEncodingException e ) {
            assertSame( jsonEncodingException, e );
        }
    }
}
