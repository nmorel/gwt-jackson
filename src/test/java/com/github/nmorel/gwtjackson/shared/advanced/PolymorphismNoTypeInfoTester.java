package com.github.nmorel.gwtjackson.shared.advanced;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

/** @author Nicolas Morel */
public final class PolymorphismNoTypeInfoTester extends AbstractTester
{

    @JsonPropertyOrder( alphabetic = true )
    public static abstract class Person
    {
        public String name;
    }

    public static class Employee extends Person
    {
        public int id;
        public String title;
    }

    public static class Manager extends Employee
    {
        public List<Employee> managedEmployees;
    }

    public static class Customer extends Person
    {
        public int satisfaction;
    }

    public static final PolymorphismNoTypeInfoTester INSTANCE = new PolymorphismNoTypeInfoTester();

    private PolymorphismNoTypeInfoTester()
    {
    }

    public void testEncoding( JsonEncoderTester<Person[]> encoder )
    {
        Person[] persons = new Person[4];

        Employee employee2 = new Employee();
        employee2.name = "Thomas";
        employee2.id = 2;
        employee2.title = "Waiter";
        persons[0] = employee2;

        Employee employee3 = new Employee();
        employee3.name = "Patricia";
        employee3.id = 3;
        employee3.title = "Cook";
        persons[1] = employee3;

        Manager manager = new Manager();
        manager.name = "Bob";
        manager.id = 1;
        manager.title = "Boss";
        manager.managedEmployees = Arrays.asList( employee2, employee3 );
        persons[2] = manager;

        Customer customer = new Customer();
        customer.name = "Brad";
        customer.satisfaction = 90;
        persons[3] = customer;

        String result = encoder.encode( persons );

        String expected = "[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}," +
            "{" +
            "\"id\":1," +
            "\"managedEmployees\":" +
            "[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}" +
            "]," +
            "\"name\":\"Bob\"," +
            "\"title\":\"Boss\"" +
            "}," +
            "{" +
            "\"name\":\"Brad\"," +
            "\"satisfaction\":90" +
            "}" +
            "]";

        assertEquals( expected, result );
    }

    /**
     * There is no information about the type to use and the superclass is not instantiable so we can't do anything, an exception is
     * thrown.
     *
     * @param decoder GWT or Jackson decoder
     */
    public void testDecodingNonInstantiableBean( JsonDecoderTester<Person[]> decoder )
    {
        String input = "[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}," +
            "{" +
            "\"id\":1," +
            "\"managedEmployees\":" +
            "[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}" +
            "]," +
            "\"name\":\"Bob\"," +
            "\"title\":\"Boss\"" +
            "}," +
            "{" +
            "\"name\":\"Brad\"," +
            "\"satisfaction\":90" +
            "}" +
            "]";

        try
        {
            decoder.decode( input );
            fail();
        }
        catch ( JsonDecodingException e )
        {
            // it's the normal behaviour
        }
    }

    /**
     * There is no information about the type to use but the superclass is instantiable so we can instantiate it and set its attributes.
     *
     * @param decoder GWT or Jackson decoder
     */
    public void testDecodingInstantiableBean( JsonDecoderTester<Employee[]> decoder )
    {
        String input = "[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}," +
            "{" +
            "\"id\":1," +
            "\"managedEmployees\":" +
            "[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}" +
            "]," +
            "\"name\":\"Bob\"," +
            "\"title\":\"Boss\"" +
            "}" +
            "]";

        Person[] result = decoder.decode( input );
        {
            // Employee
            Employee employee = (Employee) result[0];
            assertEquals( 2, employee.id );
            assertEquals( "Waiter", employee.title );
            assertEquals( "Thomas", employee.name );
        }
        {
            // Employee
            Employee employee = (Employee) result[1];
            assertEquals( 3, employee.id );
            assertEquals( "Cook", employee.title );
            assertEquals( "Patricia", employee.name );
        }
        {
            // Manager
            Employee manager = (Employee) result[2];
            assertEquals( 1, manager.id );
            assertEquals( "Boss", manager.title );
            assertEquals( "Bob", manager.name );
            assertFalse( manager instanceof Manager );
        }
    }

}
