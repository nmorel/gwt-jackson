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

package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;

/**
 * Default {@link KeyDeserializer} implementation for {@link Enum}.
 *
 * @param <E> Type of the enum
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class EnumKeyDeserializer<E extends Enum<E>> extends KeyDeserializer<E> {

    /**
     * <p>newInstance</p>
     *
     * @param enumClass class of the enumeration
     * @return a new instance of {@link EnumKeyDeserializer}
     * @param <E> a E object.
     */
    public static <E extends Enum<E>> EnumKeyDeserializer<E> newInstance( Class<E> enumClass ) {
        return new EnumKeyDeserializer<E>( enumClass );
    }

    private final Class<E> enumClass;

    /**
     * @param enumClass class of the enumeration
     */
    private EnumKeyDeserializer( Class<E> enumClass ) {
        if ( null == enumClass ) {
            throw new IllegalArgumentException( "enumClass cannot be null" );
        }
        this.enumClass = enumClass;
    }

    /** {@inheritDoc} */
    @Override
    protected E doDeserialize( String key, JsonDeserializationContext ctx ) {
        try {
            return Enum.valueOf( enumClass, key );
        } catch ( IllegalArgumentException ex ) {
            if ( ctx.isReadUnknownEnumValuesAsNull() ) {
                return null;
            }
            throw ex;
        }
    }

    /**
     * <p>Getter for the field <code>enumClass</code>.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    public Class<E> getEnumClass() {
        return enumClass;
    }
}
