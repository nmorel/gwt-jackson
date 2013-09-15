package com.github.nmorel.gwtjackson.jackson;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import org.junit.Before;

/** @author Nicolas Morel */
public abstract class AbstractJacksonTest
{
    protected ObjectMapper objectMapper;

    @Before
    public void setUp()
    {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
    }

    protected <T> JsonMapperTester<T> createMapper( final Class<T> clazz )
    {
        return new JsonMapperTester<T>()
        {
            @Override
            public T decode( String input )
            {
                try
                {
                    return objectMapper.readValue( input, clazz );
                }
                catch ( IOException e )
                {
                    throw new JsonDecodingException( e );
                }
            }

            @Override
            public String encode( T input )
            {
                try
                {
                    return objectMapper.writeValueAsString( input );
                }
                catch ( JsonProcessingException e )
                {
                    throw new JsonEncodingException( e );
                }
            }
        };
    }

    protected <T> JsonEncoderTester<T> createEncoder( Class<T> clazz )
    {
        return createMapper( clazz );
    }

    protected <T> JsonDecoderTester<T> createDecoder( final Class<T> clazz )
    {
        return createMapper( clazz );
    }
}
