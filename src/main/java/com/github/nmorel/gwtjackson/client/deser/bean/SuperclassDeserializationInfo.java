package com.github.nmorel.gwtjackson.client.deser.bean;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

/**
 * Contains deserialization informations about superclass
 *
 * @author Nicolas Morel
 */
public class SuperclassDeserializationInfo<T> {

    /**
     * Name of the property containing information about the subtype
     */
    private final boolean includeTypeInfo;

    private final As include;

    private final String propertyName;

    private final Map<String, SubtypeDeserializer<? extends T>> subtypeInfoToDeserializer;

    private final Map<Class<? extends T>, SubtypeDeserializer<? extends T>> subtypeClassToDeserializer;

    public SuperclassDeserializationInfo() {
        this( null, null, false );
    }

    public SuperclassDeserializationInfo( As include, String propertyName ) {
        this( include, propertyName, true );
    }

    private SuperclassDeserializationInfo( As include, String propertyName, boolean includeTypeInfo ) {
        this.includeTypeInfo = includeTypeInfo;
        this.include = include;
        this.propertyName = propertyName;
        if ( includeTypeInfo ) {
            this.subtypeInfoToDeserializer = new HashMap<String, SubtypeDeserializer<? extends T>>();
        } else {
            this.subtypeInfoToDeserializer = null;
        }
        this.subtypeClassToDeserializer = new HashMap<Class<? extends T>, SubtypeDeserializer<? extends T>>();
    }

    public <S extends T> SuperclassDeserializationInfo<T> addSubtypeDeserializer( SubtypeDeserializer<S> subtypeDeserializer,
                                                                                  Class<S> clazz, String typeInfo ) {
        if ( includeTypeInfo ) {
            subtypeInfoToDeserializer.put( typeInfo, subtypeDeserializer );
        }
        subtypeClassToDeserializer.put( clazz, subtypeDeserializer );
        return this;
    }

    public boolean isIncludeTypeInfo() {
        return includeTypeInfo;
    }

    public As getInclude() {
        return include;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public SubtypeDeserializer<? extends T> getDeserializer( Class aClass ) {
        return subtypeClassToDeserializer.get( aClass );
    }

    public SubtypeDeserializer<? extends T> getDeserializer( String typeInfo ) {
        return subtypeInfoToDeserializer.get( typeInfo );
    }
}
