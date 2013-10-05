package com.github.nmorel.gwtjackson.client;

import java.util.Date;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author Nicolas Morel
 */
public abstract class GwtJacksonTestCase extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.GwtJacksonTest";
    }

    @SuppressWarnings( "deprecation" )
    protected long getUTCTime( int year, int month, int day, int hour, int minute, int second, int milli ) {
        return AbstractTester.getUTCTime( year, month, day, hour, minute, second, milli );
    }

    protected Date getUTCDate( int year, int month, int day, int hour, int minute, int second, int milli ) {
        return AbstractTester.getUTCDate( year, month, day, hour, minute, second, milli );
    }

    protected <T> ObjectMapperTester<T> createMapper( final ObjectMapper<T> mapper ) {
        return createMapper( mapper, newDefaultDeserializationContext(), newDefaultSerializationContext() );
    }

    protected <T> ObjectMapperTester<T> createMapper( final ObjectMapper<T> mapper, final JsonDeserializationContext deserCtx,
                                                      final JsonSerializationContext serCtx ) {
        return new ObjectMapperTester<T>() {
            @Override
            public T read( String input ) {
                return mapper.read( input, deserCtx );
            }

            @Override
            public String write( T input ) {
                return mapper.write( input, serCtx );
            }
        };
    }

    protected <T> ObjectReaderTester<T> createReader( final ObjectReader<T> reader ) {
        return createReader( reader, newDefaultDeserializationContext() );
    }

    protected <T> ObjectReaderTester<T> createReader( final ObjectReader<T> reader, final JsonDeserializationContext ctx ) {
        return new ObjectReaderTester<T>() {
            @Override
            public T read( String input ) {
                return reader.read( input, ctx );
            }
        };
    }

    protected <T> ObjectWriterTester<T> createWriter( final ObjectWriter<T> writer ) {
        return createWriter( writer, newDefaultSerializationContext() );
    }

    protected <T> ObjectWriterTester<T> createWriter( final ObjectWriter<T> writer, final JsonSerializationContext serCtx ) {
        return new ObjectWriterTester<T>() {
            @Override
            public String write( T input ) {
                return writer.write( input, serCtx );
            }
        };
    }

    protected JsonDeserializationContext newDefaultDeserializationContext() {
        return new JsonDeserializationContext.Builder().build();
    }

    protected JsonSerializationContext newDefaultSerializationContext() {
        return new JsonSerializationContext.Builder().build();
    }
}
