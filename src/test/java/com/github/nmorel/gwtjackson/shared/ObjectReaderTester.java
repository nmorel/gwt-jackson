package com.github.nmorel.gwtjackson.shared;

/**
 * @author Nicolas Morel
 */
public interface ObjectReaderTester<T> {

    T read( String input );
}
