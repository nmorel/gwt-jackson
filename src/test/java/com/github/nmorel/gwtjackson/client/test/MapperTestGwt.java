package com.github.nmorel.gwtjackson.client.test;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.model.Person;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
@JsonFormat
@JsonIgnoreType
public class MapperTestGwt extends GwtJacksonTestCase
{
    public interface PersonJsonMapper extends JsonMapper<Person>
    {
    }

    public void testReadPerson()
    {
        PersonJsonMapper mapper = GWT.create( PersonJsonMapper.class );
        Person person = mapper.decode( "{\"id\":245, \"birthday\":\"1986-03-14\", \"lastName\":\"Morel\", \"firstName\":\"Nicolas\"}" );
        assertNotNull( person );
        assertEquals( person.getId(), 245 );
        assertEquals( person.getBirthday(), new Date( 86, 2, 14 ) );
        assertEquals( person.getFirstName(), "Nicolas" );
        assertEquals( person.getLastName(), "Morel" );
    }
}
