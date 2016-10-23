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

package com.github.nmorel.gwtjackson.rebind;

import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

/**
 * <p>BeanJsonMapperInfo class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class BeanJsonMapperInfo {

    private final JClassType type;

    private final String packageName;

    private final boolean samePackage;

    private final String simpleSerializerClassName;

    private final String simpleDeserializerClassName;

    private final BeanInfo beanInfo;

    private final ImmutableMap<String, PropertyInfo> properties;

    /**
     * <p>Constructor for BeanJsonMapperInfo.</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @param packageName a {@link java.lang.String} object.
     * @param samePackage a boolean.
     * @param simpleSerializerClassName a {@link java.lang.String} object.
     * @param simpleDeserializerClassName a {@link java.lang.String} object.
     * @param beanInfo a {@link com.github.nmorel.gwtjackson.rebind.bean.BeanInfo} object.
     * @param properties a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableMap} object.
     */
    public BeanJsonMapperInfo( JClassType type, String packageName, boolean samePackage, String simpleSerializerClassName,
                               String simpleDeserializerClassName, BeanInfo beanInfo, ImmutableMap<String, PropertyInfo> properties ) {
        this.type = type;
        this.packageName = packageName;
        this.samePackage = samePackage;
        this.simpleSerializerClassName = simpleSerializerClassName;
        this.simpleDeserializerClassName = simpleDeserializerClassName;
        this.beanInfo = beanInfo;
        this.properties = properties;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     */
    public JClassType getType() {
        return type;
    }

    /**
     * <p>Getter for the field <code>packageName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * <p>isSamePackage</p>
     *
     * @return a boolean.
     */
    public boolean isSamePackage() {
        return samePackage;
    }

    /**
     * <p>Getter for the field <code>simpleSerializerClassName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSimpleSerializerClassName() {
        return simpleSerializerClassName;
    }

    /**
     * <p>Getter for the field <code>simpleDeserializerClassName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSimpleDeserializerClassName() {
        return simpleDeserializerClassName;
    }

    /**
     * <p>Getter for the field <code>beanInfo</code>.</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.rebind.bean.BeanInfo} object.
     */
    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    /**
     * <p>Getter for the field <code>properties</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableMap} object.
     */
    public ImmutableMap<String, PropertyInfo> getProperties() {
        return properties;
    }
}
