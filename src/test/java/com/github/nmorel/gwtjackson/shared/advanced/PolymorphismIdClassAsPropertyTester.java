package com.github.nmorel.gwtjackson.shared.advanced;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

import static junit.framework.Assert.assertEquals;

/** @author Nicolas Morel */
public final class PolymorphismIdClassAsPropertyTester extends AbstractTester
{

    @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY )
    @JsonPropertyOrder( alphabetic = true )
    public static abstract class Person
    {
        public String name;
    }

    @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
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

    public static final PolymorphismIdClassAsPropertyTester INSTANCE = new PolymorphismIdClassAsPropertyTester();

    private PolymorphismIdClassAsPropertyTester()
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
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}," +
            "{" +
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Manager\"," +
            "\"id\":1," +
            "\"managedEmployees\":" +
            "[" +
            "{" +
            "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}" +
            "]," +
            "\"name\":\"Bob\"," +
            "\"title\":\"Boss\"" +
            "}," +
            "{" +
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Customer\"," +
            "\"name\":\"Brad\"," +
            "\"satisfaction\":90" +
            "}" +
            "]";

        assertEquals( expected, result );
    }

    public void testDecoding( JsonDecoderTester<Person[]> decoder )
    {
        String input = "[" +
            "{" +
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}," +
            "{" +
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Manager\"," +
            "\"id\":1," +
            "\"managedEmployees\":" +
            "[" +
            "{" +
            "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":2," +
            "\"name\":\"Thomas\"," +
            "\"title\":\"Waiter\"" +
            "}," +
            "{" +
            "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Employee\"," +
            "\"id\":3," +
            "\"name\":\"Patricia\"," +
            "\"title\":\"Cook\"" +
            "}" +
            "]," +
            "\"name\":\"Bob\"," +
            "\"title\":\"Boss\"" +
            "}," +
            "{" +
            "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.PolymorphismIdClassAsPropertyTester$Customer\"," +
            "\"name\":\"Brad\"," +
            "\"satisfaction\":90" +
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
            Manager manager = (Manager) result[2];
            assertEquals( 1, manager.id );
            assertEquals( "Boss", manager.title );
            assertEquals( "Bob", manager.name );
            assertEquals( 2, manager.managedEmployees.size() );

            Employee employee1 = manager.managedEmployees.get( 0 );
            assertEquals( 2, employee1.id );
            assertEquals( "Waiter", employee1.title );
            assertEquals( "Thomas", employee1.name );

            Employee employee2 = manager.managedEmployees.get( 1 );
            assertEquals( 3, employee2.id );
            assertEquals( "Cook", employee2.title );
            assertEquals( "Patricia", employee2.name );
        }
        {
            // Customer
            Customer customer = (Customer) result[3];
            assertEquals( "Brad", customer.name );
            assertEquals( 90, customer.satisfaction );
        }
    }

}
