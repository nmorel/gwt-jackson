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
 * @author Nicolas Morel
 */
public class BeanJsonMapperInfo {

    private final JClassType type;

    private final String packageName;

    private final boolean samePackage;

    private final String simpleSerializerClassName;

    private final String simpleDeserializerClassName;

    private final BeanInfo beanInfo;

    private final ImmutableMap<String, PropertyInfo> properties;

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

    public JClassType getType() {
        return type;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isSamePackage() {
        return samePackage;
    }

    public String getSimpleSerializerClassName() {
        return simpleSerializerClassName;
    }

    public String getSimpleDeserializerClassName() {
        return simpleDeserializerClassName;
    }

    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    public ImmutableMap<String, PropertyInfo> getProperties() {
        return properties;
    }
}
