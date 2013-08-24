package com.github.nmorel.gwtjackson.client.test;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.model.Person;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class MapperTestGwt extends GwtJacksonTestCase
{
    public interface PersonJsonMapper extends JsonMapper<Iterable<Person>>
    {
    }

    public void testReadPerson() throws IOException
    {
        PersonJsonMapper mapper = GWT.create( PersonJsonMapper.class );
        Iterable<Person> persons = mapper.decode( "[{\"id\":245, \"birthday\":\"1986-03-14T10:29:40.000\", \"lastName\":\"Morel\", " +
            "" + "\"firstName\":\"Nicolas\"}]" );
        assertNotNull( persons );
        Iterator<Person> iterator = persons.iterator();

        Person person = iterator.next();
        assertEquals( 245, person.getId() );
        assertEquals( new Date( Date.UTC( 86, 2, 14, 10, 29, 40 ) ), person.getBirthday() );
        assertEquals( "Nicolas", person.getFirstName() );
        assertEquals( "Morel", person.getLastName() );

        assertFalse( iterator.hasNext() );
    }
}
