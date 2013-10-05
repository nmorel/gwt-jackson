package com.github.nmorel.gwtjackson.shared.advanced.identity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * Test from jackson-databind and adapted for the project
 */
public final class ObjectIdTester extends AbstractTester {

    @JsonPropertyOrder({"a", "b"})
    public static class Wrapper {

        public ColumnMetadata a, b;
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    public static class ColumnMetadata {

        private final String name;

        private final String type;

        private final String comment;

        @JsonCreator
        public ColumnMetadata( @JsonProperty("name") String name, @JsonProperty("type") String type,
                               @JsonProperty("comment") String comment ) {
            this.name = name;
            this.type = type;
            this.comment = comment;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("type")
        public String getType() {
            return type;
        }

        @JsonProperty("comment")
        public String getComment() {
            return comment;
        }
    }

    /* Problem in which always-as-id reference may prevent initial
     * serialization of a POJO.
     */

    public static class Company {

        public List<Employee> employees;

        public void add( Employee e ) {
            if ( employees == null ) {
                employees = new ArrayList<Employee>();
            }
            employees.add( e );
        }
    }

    @JsonIdentityInfo(property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class)
    public static class Employee {

        public int id;

        public String name;

        @JsonIdentityReference(alwaysAsId = true)
        public Employee manager;

        @JsonIdentityReference(alwaysAsId = true)
        public List<Employee> reports;

        public Employee() {
        }

        public Employee( int id, String name, Employee manager ) {
            this.id = id;
            this.name = name;
            this.manager = manager;
            reports = new ArrayList<Employee>();
        }

        public Employee addReport( Employee e ) {
            reports.add( e );
            return this;
        }
    }

    /*
    /**********************************************************
    /* Singleton
    /**********************************************************
     */
    public static final ObjectIdTester INSTANCE = new ObjectIdTester();

    private ObjectIdTester() {
    }

    /*
    /**********************************************************
    /* Test methods
    /**********************************************************
     */

    public void testColumnMetadata( ObjectMapperTester<Wrapper> mapper ) {
        ColumnMetadata col = new ColumnMetadata( "Billy", "employee", "comment" );
        Wrapper w = new Wrapper();
        w.a = col;
        w.b = col;

        String json = mapper.write( w );
        assertEquals( "{\"a\":{\"@id\":1,\"name\":\"Billy\",\"type\":\"employee\",\"comment\":\"comment\"},\"b\":1}", json );

        Wrapper deserialized = mapper.read( json );
        assertNotNull( deserialized );
        assertNotNull( deserialized.a );
        assertNotNull( deserialized.b );

        assertEquals( "Billy", deserialized.a.getName() );
        assertEquals( "employee", deserialized.a.getType() );
        assertEquals( "comment", deserialized.a.getComment() );

        assertSame( deserialized.a, deserialized.b );
    }

    // For Issue#188
    public void testMixedRefsIssue188( ObjectMapperTester<Company> mapper ) {
        Company comp = new Company();
        Employee e1 = new Employee( 1, "First", null );
        Employee e2 = new Employee( 2, "Second", e1 );
        e1.addReport( e2 );
        comp.add( e1 );
        comp.add( e2 );

        String json = mapper.write( comp );
        assertEquals( "{\"employees\":[{\"id\":1,\"name\":\"First\",\"manager\":null,\"reports\":[2]},{\"id\":2,\"name\":\"Second\"," +
            "\"manager\":1,\"reports\":[]}]}", json );
    }
}
