package com.github.nmorel.gwtjackson.client.test;

import java.io.IOException;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.model.Person;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.i18n.client.DateTimeFormat;

/** @author Nicolas Morel */
public class MapperTestGwt extends GwtJacksonTestCase
{
    public interface PersonJsonMapper extends JsonMapper<Person>
    {
    }

    private static class PersonJsonMapperTestImpl extends AbstractJsonMapper<Person> implements PersonJsonMapper
    {
        @Override
        public Person decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
        {
            Person result = new Person();

            reader.beginObject();

            while ( JsonToken.NAME.equals( reader.peek() ) )
            {
                String name = reader.nextName();
                if ( JsonToken.NULL.equals( reader.peek() ) )
                {
                    reader.skipValue();
                    continue;
                }
                if ( "id".equals( name ) )
                {
                    result.setId( reader.nextInt() );
                }
                else if ( "firstName".equals( name ) )
                {
                    result.setFirstName( reader.nextString() );
                }
                else if ( "lastName".equals( name ) )
                {
                    result.setLastName( reader.nextString() );
                }
                else if ( "birthday".equals( name ) )
                {
                    result.setBirthday( DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 ).parse( reader.nextString() ) );
                }
                else
                {
                    reader.skipValue();
                }
            }

            reader.endObject();

            return result;
        }

        @Override
        public void encode( JsonWriter writer, Person value, JsonEncodingContext ctx )
        {

        }
    }

    public void testReadPerson() throws IOException
    {
        PersonJsonMapper mapper = new PersonJsonMapperTestImpl();
//        PersonJsonMapper mapper = GWT.create( PersonJsonMapper.class );
        Person person = mapper.decode( "{\"id\":245, \"birthday\":\"1986-03-14T10:29:40.000\", \"lastName\":\"Morel\", " +
            "" + "\"firstName\":\"Nicolas\"}" );
        assertNotNull( person );
        assertEquals( person.getId(), 245 );
        assertEquals( person.getBirthday(), new Date( Date.UTC( 86, 2, 14, 10, 29, 40 ) ) );
        assertEquals( person.getFirstName(), "Nicolas" );
        assertEquals( person.getLastName(), "Morel" );
    }
}
