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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
public interface BeanInfo {

    JClassType getType();

    ImmutableList<JClassType> getParameterizedTypes();

    boolean isCreatorDefaultConstructor();

    Optional<JAbstractMethod> getCreatorMethod();

    ImmutableMap<String, JParameter> getCreatorParameters();

    boolean isCreatorDelegation();

    Optional<BeanTypeInfo> getTypeInfo();

    ImmutableSet<String> getIgnoredFields();

    JsonAutoDetect.Visibility getFieldVisibility();

    JsonAutoDetect.Visibility getGetterVisibility();

    JsonAutoDetect.Visibility getIsGetterVisibility();

    JsonAutoDetect.Visibility getSetterVisibility();

    JsonAutoDetect.Visibility getCreatorVisibility();

    boolean isIgnoreUnknown();

    ImmutableList<String> getPropertyOrderList();

    boolean isPropertyOrderAlphabetic();

    Optional<BeanIdentityInfo> getIdentityInfo();
}
