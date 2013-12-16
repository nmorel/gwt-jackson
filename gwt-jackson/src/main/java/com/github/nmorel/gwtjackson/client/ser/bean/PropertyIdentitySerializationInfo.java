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

package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Contains identity informations for serialization process.
 *
 * @author Nicolas Morel
 */
public class PropertyIdentitySerializationInfo<T> implements IdentitySerializationInfo<T> {

    /**
     * if we always serialize the bean as an id even for the first encounter.
     */
    private final boolean alwaysAsId;

    /**
     * Name of the property holding the identity
     */
    private final String propertyName;

    public PropertyIdentitySerializationInfo( boolean alwaysAsId, String propertyName ) {
        this.alwaysAsId = alwaysAsId;
        this.propertyName = propertyName;
    }

    @Override
    public boolean isAlwaysAsId() {
        return alwaysAsId;
    }

    @Override
    public boolean isProperty() {
        return true;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public ObjectIdSerializer<?> getObjectId( T bean, JsonSerializationContext ctx ) {
        throw ctx.traceError( bean, "getObjectId() is not supported by PropertyIdentitySerializationInfo" );
    }
}
