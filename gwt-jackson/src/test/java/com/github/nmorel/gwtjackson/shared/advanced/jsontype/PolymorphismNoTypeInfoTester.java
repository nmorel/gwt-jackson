/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.shared.advanced.jsontype;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class PolymorphismNoTypeInfoTester extends AbstractTester {

    @JsonPropertyOrder( alphabetic = true )
    public static interface Person {

        String getName();

        void setName( String name );
    }

    public static class Employee implements Person {

        private String name;

        public int id;

        public String title;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName( String name ) {
            this.name = name;
        }
    }

    public static class Manager extends Employee {

        public List<Employee> managedEmployees;
    }

    public static class Customer implements Person {

        private String name;

        public int satisfaction;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName( String name ) {
            this.name = name;
        }
    }

    public static final PolymorphismNoTypeInfoTester INSTANCE = new PolymorphismNoTypeInfoTester();

    private PolymorphismNoTypeInfoTester() {
    }

    public void testSerialize( ObjectWriterTester<Person[]> writer ) {
        Person[] persons = new Person[4];

        Employee employee2 = new Employee();
        employee2.setName( "Thomas" );
        employee2.id = 2;
        employee2.title = "Waiter";
        persons[0] = employee2;

        Employee employee3 = new Employee();
        employee3.setName( "Patricia" );
        employee3.id = 3;
        employee3.title = "Cook";
        persons[1] = employee3;

        Manager manager = new Manager();
        manager.setName( "Bob" );
        manager.id = 1;
        manager.title = "Boss";
        manager.managedEmployees = Arrays.asList( employee2, employee3 );
        persons[2] = manager;

        Customer customer = new Customer();
        customer.setName( "Brad" );
        customer.satisfaction = 90;
        persons[3] = customer;

        String result = writer.write( persons );

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
     * @param reader GWT or Jackson reader
     */
    public void testDeserializeNonInstantiableBean( ObjectReaderTester<Person[]> reader ) {
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

        try {
            reader.read( input );
            fail();
        } catch ( JsonDeserializationException e ) {
            // it's the normal behaviour
        }
    }

    /**
     * There is no information about the type to use but the superclass is instantiable so we can instantiate it and set its attributes.
     *
     * @param reader GWT or Jackson reader
     */
    public void testDeserializeInstantiableBean( ObjectReaderTester<Employee[]> reader ) {
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

        Person[] result = reader.read( input );
        {
            // Employee
            Employee employee = (Employee) result[0];
            assertEquals( 2, employee.id );
            assertEquals( "Waiter", employee.title );
            assertEquals( "Thomas", employee.getName() );
        }
        {
            // Employee
            Employee employee = (Employee) result[1];
            assertEquals( 3, employee.id );
            assertEquals( "Cook", employee.title );
            assertEquals( "Patricia", employee.getName() );
        }
        {
            // Manager
            Employee manager = (Employee) result[2];
            assertEquals( 1, manager.id );
            assertEquals( "Boss", manager.title );
            assertEquals( "Bob", manager.getName() );
            assertFalse( manager instanceof Manager );
        }
    }

}
