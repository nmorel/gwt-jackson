package com.github.nmorel.gwtjackson.shared;

/** @author Nicolas Morel */
public interface JsonDecoderTester<T>
{
    T decode( String input );
}
