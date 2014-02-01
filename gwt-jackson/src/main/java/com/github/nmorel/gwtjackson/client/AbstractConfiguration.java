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

package com.github.nmorel.gwtjackson.client;

import java.util.HashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractConfiguration {

    public class PrimitiveTypeConfiguration {

        private final Class type;

        private PrimitiveTypeConfiguration( Class type ) {
            this.type = type;
        }

        public PrimitiveTypeConfiguration serializer( Class serializer ) {
            mapTypeToSerializer.put( type, serializer );
            return this;
        }

        public PrimitiveTypeConfiguration deserializer( Class deserializer ) {
            mapTypeToDeserializer.put( type, deserializer );
            return this;
        }
    }

    public class TypeConfiguration<T> {

        private final Class<T> type;

        private TypeConfiguration( Class<T> type ) {
            this.type = type;
        }

        public TypeConfiguration<T> serializer( Class<? extends JsonSerializer> serializer ) {
            mapTypeToSerializer.put( type, serializer );
            return this;
        }

        public TypeConfiguration<T> deserializer( Class<? extends JsonDeserializer> deserializer ) {
            mapTypeToDeserializer.put( type, deserializer );
            return this;
        }
    }

    public class KeyTypeConfiguration<T> {

        private final Class<T> type;

        private KeyTypeConfiguration( Class<T> type ) {
            this.type = type;
        }

        public KeyTypeConfiguration<T> serializer( Class<? extends KeySerializer> serializer ) {
            mapTypeToKeySerializer.put( type, serializer );
            return this;
        }

        public KeyTypeConfiguration<T> deserializer( Class<? extends KeyDeserializer> deserializer ) {
            mapTypeToKeyDeserializer.put( type, deserializer );
            return this;
        }
    }

    private final Map<Class, Class> mapTypeToSerializer = new HashMap<Class, Class>();

    private final Map<Class, Class> mapTypeToDeserializer = new HashMap<Class, Class>();

    private final Map<Class, Class> mapTypeToKeySerializer = new HashMap<Class, Class>();

    private final Map<Class, Class> mapTypeToKeyDeserializer = new HashMap<Class, Class>();

    private final Map<Class, Class> mapMixInAnnotations = new HashMap<Class, Class>();

    protected AbstractConfiguration() {
        configure();
    }

    /**
     * Return a {@link PrimitiveTypeConfiguration} to configure serializer and/or deserializer for the given primitive type.
     */
    protected PrimitiveTypeConfiguration primitiveType( Class type ) {
        if ( !type.isPrimitive() ) {
            throw new IllegalArgumentException( "Type " + type + " is not a primitive. Call type(Class) instead" );
        }
        return new PrimitiveTypeConfiguration( type );
    }

    /**
     * Return a {@link TypeConfiguration} to configure serializer and/or deserializer for the given type.
     */
    protected <T> TypeConfiguration<T> type( Class<T> type ) {
        if ( type.isPrimitive() ) {
            throw new IllegalArgumentException( "Type " + type + " is a primitive. Call primitiveType(Class) instead" );
        }
        return new TypeConfiguration<T>( type );
    }

    /**
     * Return a {@link KeyTypeConfiguration} to configure key serializer and/or deserializer for the given type.
     */
    protected <T> KeyTypeConfiguration<T> key( Class<T> type ) {
        if ( type.isPrimitive() ) {
            throw new IllegalArgumentException( "Primitive types cannot be used as a map's key" );
        }
        return new KeyTypeConfiguration<T>( type );
    }

    /**
     * Method to use for adding mix-in annotations to use for augmenting
     * specified class or interface. All annotations from
     * <code>mixinSource</code> are taken to override annotations
     * that <code>target</code> (or its supertypes) has.
     *
     * @param target Class (or interface) whose annotations to effectively override
     * @param mixinSource Class (or interface) whose annotations are to
     * be "added" to target's annotations, overriding as necessary
     */
    protected AbstractConfiguration addMixInAnnotations( Class<?> target, Class<?> mixinSource ) {
        mapMixInAnnotations.put( target, mixinSource );
        return this;
    }

    protected abstract void configure();

    public Map<Class, Class> getMapTypeToSerializer() {
        return mapTypeToSerializer;
    }

    public Map<Class, Class> getMapTypeToDeserializer() {
        return mapTypeToDeserializer;
    }

    public Map<Class, Class> getMapTypeToKeySerializer() {
        return mapTypeToKeySerializer;
    }

    public Map<Class, Class> getMapTypeToKeyDeserializer() {
        return mapTypeToKeyDeserializer;
    }

    public Map<Class, Class> getMapMixInAnnotations() {
        return mapMixInAnnotations;
    }
}
