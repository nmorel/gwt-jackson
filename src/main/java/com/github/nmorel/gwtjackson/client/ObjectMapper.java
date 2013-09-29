package com.github.nmorel.gwtjackson.client;

/**
 * Interface combining {@link ObjectReader} and {@link ObjectWriter}
 *
 * @param <T> Type of the mapped object
 *
 * @author Nicolas Morel
 */
public interface ObjectMapper<T> extends ObjectReader<T>, ObjectWriter<T> {}
