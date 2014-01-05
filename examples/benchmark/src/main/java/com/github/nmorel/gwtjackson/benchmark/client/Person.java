package com.github.nmorel.gwtjackson.benchmark.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Nicolas Morel
 */
public class Person {

    private String firstName;

    private String lastName;

    private List<Person> childs;

    public Person() {
    }

    public Person( String firstName, String lastName, Person... childs ) {
        this.firstName = firstName;
        this.lastName = lastName;
        if ( null == childs || childs.length == 0 ) {
            this.childs = Collections.emptyList();
        } else {
            this.childs = Arrays.asList( childs );
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public List<Person> getChilds() {
        return childs;
    }

    public void setChilds( List<Person> childs ) {
        this.childs = childs;
    }
}
