package com.github.nmorel.gwtjackson.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import org.junit.Before;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractJacksonTest {

    protected ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    protected <T> ObjectMapperTester<T> createMapper( final Class<T> clazz ) {
        return new ObjectMapperTester<T>() {
            @Override
            public T read( String input ) {
                try {
                    return objectMapper.readValue( input, clazz );
                } catch ( IOException e ) {
                    throw new JsonDeserializationException( e );
                }
            }

            @Override
            public String write( T input ) {
                try {
                    return objectMapper.writeValueAsString( input );
                } catch ( JsonProcessingException e ) {
                    throw new JsonSerializationException( e );
                }
            }
        };
    }

    protected <T> ObjectWriterTester<T> createWriter( Class<T> clazz ) {
        return createMapper( clazz );
    }

    protected <T> ObjectReaderTester<T> createReader( Class<T> clazz ) {
        return createMapper( clazz );
    }

    protected <T> ObjectMapperTester<T> createMapper( final TypeReference<T> typeReference ) {
        return new ObjectMapperTester<T>() {
            @Override
            public T read( String input ) {
                try {
                    return objectMapper.readValue( input, typeReference );
                } catch ( IOException e ) {
                    throw new JsonDeserializationException( e );
                }
            }

            @Override
            public String write( T input ) {
                try {
                    return objectMapper.writeValueAsString( input );
                } catch ( JsonProcessingException e ) {
                    throw new JsonSerializationException( e );
                }
            }
        };
    }

    protected <T> ObjectWriterTester<T> createWriter( TypeReference<T> typeReference ) {
        return createMapper( typeReference );
    }

    protected <T> ObjectReaderTester<T> createReader( TypeReference<T> typeReference ) {
        return createMapper( typeReference );
    }
}
