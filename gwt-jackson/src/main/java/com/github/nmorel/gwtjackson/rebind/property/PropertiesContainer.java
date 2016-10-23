/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.rebind.property;

import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

/**
 * <p>PropertiesContainer class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PropertiesContainer {

    private final ImmutableMap<String, PropertyInfo> properties;

    private final Optional<PropertyInfo> valuePropertyInfo;

    private final Optional<PropertyInfo> anyGetterPropertyInfo;

    private final Optional<PropertyInfo> anySetterPropertyInfo;

    /**
     * <p>Constructor for PropertiesContainer.</p>
     *
     * @param properties a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableMap} object.
     * @param valuePropertyInfo a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     * @param anyGetterPropertyInfo a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     * @param anySetterPropertyInfo a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public PropertiesContainer( ImmutableMap<String, PropertyInfo> properties, Optional<PropertyInfo> valuePropertyInfo,
                                Optional<PropertyInfo> anyGetterPropertyInfo, Optional<PropertyInfo> anySetterPropertyInfo ) {
        this.properties = properties;
        this.valuePropertyInfo = valuePropertyInfo;
        this.anyGetterPropertyInfo = anyGetterPropertyInfo;
        this.anySetterPropertyInfo = anySetterPropertyInfo;
    }

    /**
     * <p>Getter for the field <code>properties</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableMap} object.
     */
    public ImmutableMap<String, PropertyInfo> getProperties() {
        return properties;
    }

    /**
     * <p>Getter for the field <code>valuePropertyInfo</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<PropertyInfo> getValuePropertyInfo() {
        return valuePropertyInfo;
    }

    /**
     * <p>Getter for the field <code>anyGetterPropertyInfo</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<PropertyInfo> getAnyGetterPropertyInfo() {
        return anyGetterPropertyInfo;
    }

    /**
     * <p>Getter for the field <code>anySetterPropertyInfo</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<PropertyInfo> getAnySetterPropertyInfo() {
        return anySetterPropertyInfo;
    }
}
