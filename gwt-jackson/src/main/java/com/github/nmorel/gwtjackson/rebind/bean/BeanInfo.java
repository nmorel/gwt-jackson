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

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableSet;

/**
 * @author Nicolas Morel
 */
public final class BeanInfo {

    private final JClassType type;

    private final ImmutableList<JClassType> parameterizedTypes;

    /*####  Instantiation properties  ####*/
    private final Optional<JClassType> builder;

    private final Optional<JAbstractMethod> creatorMethod;

    private final ImmutableMap<String, JParameter> creatorParameters;

    private final boolean creatorDefaultConstructor;

    private final boolean creatorDelegation;

    private final Optional<BeanTypeInfo> typeInfo;

    private final Optional<PropertyInfo> valuePropertyInfo;

    private final Optional<PropertyInfo> anyGetterPropertyInfo;

    private final Optional<PropertyInfo> anySetterPropertyInfo;

    /*####  Visibility properties  ####*/
    private final ImmutableSet<String> ignoredFields;

    private final JsonAutoDetect.Visibility fieldVisibility;

    private final JsonAutoDetect.Visibility getterVisibility;

    private final JsonAutoDetect.Visibility isGetterVisibility;

    private final JsonAutoDetect.Visibility setterVisibility;

    private final JsonAutoDetect.Visibility creatorVisibility;

    private final boolean ignoreUnknown;

    /*####  Ordering properties  ####*/
    private final ImmutableList<String> propertyOrderList;

    private final boolean propertyOrderAlphabetic;

    /*####  Identity info  ####*/
    private final Optional<BeanIdentityInfo> identityInfo;

    /*#### Inclusion info ####*/
    private final Optional<Include> include;

    BeanInfo( JClassType type, List<JClassType> parameterizedTypes, Optional<JClassType> builder, Optional<JAbstractMethod> creatorMethod, Map<String, JParameter> creatorParameters, boolean creatorDefaultConstructor, boolean creatorDelegation, Optional<BeanTypeInfo> typeInfo, Optional<PropertyInfo> valuePropertyInfo, Optional<PropertyInfo> anyGetterPropertyInfo, Optional<PropertyInfo> anySetterPropertyInfo, Set<String> ignoredFields, Visibility fieldVisibility, Visibility getterVisibility, Visibility isGetterVisibility, Visibility setterVisibility, Visibility creatorVisibility, boolean ignoreUnknown, List<String> propertyOrderList, boolean propertyOrderAlphabetic, Optional<BeanIdentityInfo> identityInfo, Optional<Include> include ) {

        this.type = type;
        this.parameterizedTypes = ImmutableList.copyOf( parameterizedTypes );

        this.builder = builder;
        this.creatorMethod = creatorMethod;
        this.creatorParameters = ImmutableMap.copyOf( creatorParameters );
        this.creatorDefaultConstructor = creatorDefaultConstructor;
        this.creatorDelegation = creatorDelegation;
        this.typeInfo = typeInfo;
        this.valuePropertyInfo = valuePropertyInfo;
        this.anyGetterPropertyInfo = anyGetterPropertyInfo;
        this.anySetterPropertyInfo = anySetterPropertyInfo;
        this.ignoredFields = ImmutableSet.copyOf( ignoredFields );

        this.fieldVisibility = fieldVisibility;
        this.getterVisibility = getterVisibility;
        this.isGetterVisibility = isGetterVisibility;
        this.setterVisibility = setterVisibility;
        this.creatorVisibility = creatorVisibility;

        this.ignoreUnknown = ignoreUnknown;
        this.propertyOrderList = ImmutableList.copyOf( propertyOrderList );
        this.propertyOrderAlphabetic = propertyOrderAlphabetic;
        this.identityInfo = identityInfo;
        this.include = include;
    }

    public JClassType getType() {
        return type;
    }

    public ImmutableList<JClassType> getParameterizedTypes() {
        return parameterizedTypes;
    }

    public Optional<JClassType> getBuilder() {
        return builder;
    }

    public Optional<JAbstractMethod> getCreatorMethod() {
        return creatorMethod;
    }

    public ImmutableMap<String, JParameter> getCreatorParameters() {
        return creatorParameters;
    }

    public boolean isCreatorDefaultConstructor() {
        return creatorDefaultConstructor;
    }

    public boolean isCreatorDelegation() {
        return creatorDelegation;
    }

    public Optional<BeanTypeInfo> getTypeInfo() {
        return typeInfo;
    }

    public Optional<PropertyInfo> getValuePropertyInfo() {
        return valuePropertyInfo;
    }

    public Optional<PropertyInfo> getAnyGetterPropertyInfo() {
        return anyGetterPropertyInfo;
    }

    public Optional<PropertyInfo> getAnySetterPropertyInfo() {
        return anySetterPropertyInfo;
    }

    public ImmutableSet<String> getIgnoredFields() {
        return ignoredFields;
    }

    public Visibility getFieldVisibility() {
        return fieldVisibility;
    }

    public Visibility getGetterVisibility() {
        return getterVisibility;
    }

    public Visibility getIsGetterVisibility() {
        return isGetterVisibility;
    }

    public Visibility getSetterVisibility() {
        return setterVisibility;
    }

    public Visibility getCreatorVisibility() {
        return creatorVisibility;
    }

    public boolean isIgnoreUnknown() {
        return ignoreUnknown;
    }

    public ImmutableList<String> getPropertyOrderList() {
        return propertyOrderList;
    }

    public boolean isPropertyOrderAlphabetic() {
        return propertyOrderAlphabetic;
    }

    public Optional<BeanIdentityInfo> getIdentityInfo() {
        return identityInfo;
    }

    public Optional<Include> getInclude() {
        return include;
    }
}
