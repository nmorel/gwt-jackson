package com.github.nmorel.gwtjackson.jackson;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import org.junit.Before;

/** @author Nicolas Morel */
public abstract class AbstractJacksonTest
{
    protected ObjectMapper objectMapper;

    @Before
    public void setUp()
    {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL);
    }

    protected <T> JsonEncoderTester<T> createEncoder(Class<T> clazz)
    {
        return new JsonEncoderTester<T>()
        {
            @Override
            public String encode( T input )
            {
                try
                {
                    return objectMapper.writeValueAsString( input );
                }
                catch ( JsonProcessingException e )
                {
                    throw new RuntimeException( e );
                }
            }
        };
    }

    protected <T> JsonDecoderTester<T> createDecoder( final Class<T> clazz )
    {
        return new JsonDecoderTester<T>()
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
                    throw new RuntimeException( e );
                }
            }
        };
    }
}
