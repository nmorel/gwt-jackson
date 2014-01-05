package com.github.nmorel.gwtjackson.benchmark.client;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas Morel
 */
public final class DataProvider {

    public static List<Person> generateData( int nbItems ) {
        final List<Person> persons = new ArrayList<>( nbItems );
        for ( int i = 0; i < nbItems; i++ ) {
            persons.add( new Person( "John", "Doe", new Person( "Jane", "Doe" ), new Person( "Billy", "Doe", new Person( "Lily",
                    "Doe" ) ) ) );
        }
        return persons;
    }

    private DataProvider() {}

}

