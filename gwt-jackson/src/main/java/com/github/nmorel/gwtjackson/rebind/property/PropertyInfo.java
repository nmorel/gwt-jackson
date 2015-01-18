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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.rebind.bean.BeanIdentityInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanTypeInfo;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;

/**
 * @author Nicolas Morel.
 */
public interface PropertyInfo {

    String getPropertyName();

    JType getType();

    boolean isIgnored();

    boolean isRequired();

    boolean isRawValue();

    boolean isValue();

    boolean isAnyGetter();

    boolean isAnySetter();

    boolean isUnwrapped();

    Optional<String> getManagedReference();

    Optional<String> getBackReference();

    Optional<? extends FieldAccessor> getGetterAccessor();

    Optional<? extends FieldAccessor> getSetterAccessor();

    Optional<BeanIdentityInfo> getIdentityInfo();

    Optional<BeanTypeInfo> getTypeInfo();

    Optional<JsonFormat> getFormat();

    Optional<Include> getInclude();

    Optional<Boolean> getIgnoreUnknown();

    Optional<String[]> getIgnoredProperties();
}
