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

package com.github.nmorel.gwtjackson.rebind.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;

/**
 * <p>BeanIdentityInfo class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class BeanIdentityInfo {

    private final String propertyName;

    private final boolean idABeanProperty;

    private final boolean alwaysAsId;

    private final Class<? extends ObjectIdGenerator<?>> generator;

    private final Class<?> scope;

    private final Optional<JType> type;

    BeanIdentityInfo( String propertyName, boolean alwaysAsId, Class<? extends ObjectIdGenerator<?>> generator, Class<?> scope ) {
        this.propertyName = propertyName;
        this.alwaysAsId = alwaysAsId;
        this.generator = generator;
        this.scope = scope;
        this.idABeanProperty = true;
        this.type = Optional.absent();
    }

    BeanIdentityInfo( String propertyName, boolean alwaysAsId, Class<? extends ObjectIdGenerator<?>> generator, Class<?> scope,
                      JType type ) {
        this.propertyName = propertyName;
        this.alwaysAsId = alwaysAsId;
        this.generator = generator;
        this.scope = scope;
        this.idABeanProperty = false;
        this.type = Optional.of( type );
    }

    /**
     * <p>isIdABeanProperty</p>
     *
     * @return a boolean.
     */
    public boolean isIdABeanProperty() {
        return idABeanProperty;
    }

    /**
     * <p>Getter for the field <code>propertyName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * <p>isAlwaysAsId</p>
     *
     * @return a boolean.
     */
    public boolean isAlwaysAsId() {
        return alwaysAsId;
    }

    /**
     * <p>Getter for the field <code>generator</code>.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    public Class<? extends ObjectIdGenerator<?>> getGenerator() {
        return generator;
    }

    /**
     * <p>Getter for the field <code>scope</code>.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    public Class<?> getScope() {
        return scope;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<JType> getType() {
        return type;
    }
}
