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

import java.lang.annotation.Annotation;

import com.github.nmorel.gwtjackson.rebind.CreatorUtils;
import com.google.gwt.core.ext.typeinfo.HasAnnotations;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

/**
 * @author Nicolas Morel.
 */
public final class PropertyAccessors {

    private final String propertyName;

    private final Optional<JField> field;

    private final Optional<JMethod> getter;

    private final Optional<JMethod> setter;

    private final Optional<JParameter> parameter;

    private final ImmutableList<JField> fields;

    private final ImmutableList<JMethod> getters;

    private final ImmutableList<JMethod> setters;

    private final ImmutableList<HasAnnotations> accessors;

    PropertyAccessors( String propertyName, Optional<JField> field, Optional<JMethod> getter, Optional<JMethod> setter,
                       Optional<JParameter> parameter, ImmutableList<JField> fields, ImmutableList<JMethod> getters,
                       ImmutableList<JMethod> setters, ImmutableList<HasAnnotations> accessors ) {

        this.propertyName = propertyName;
        this.field = field;
        this.getter = getter;
        this.setter = setter;
        this.parameter = parameter;
        this.fields = fields;
        this.getters = getters;
        this.setters = setters;
        this.accessors = accessors;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Optional<JField> getField() {
        return field;
    }

    public Optional<JMethod> getGetter() {
        return getter;
    }

    public Optional<JMethod> getSetter() {
        return setter;
    }

    public Optional<JParameter> getParameter() {
        return parameter;
    }

    public <T extends Annotation> boolean isAnnotationPresentOnField( Class<T> annotation ) {
        return CreatorUtils.isAnnotationPresent( annotation, fields );
    }

    public <T extends Annotation> boolean isAnnotationPresentOnGetter( Class<T> annotation ) {
        return CreatorUtils.isAnnotationPresent( annotation, getters );
    }

    public <T extends Annotation> boolean isAnnotationPresentOnSetter( Class<T> annotation ) {
        return CreatorUtils.isAnnotationPresent( annotation, setters );
    }

    public <T extends Annotation> Optional<T> getAnnotation( Class<T> annotation ) {
        return CreatorUtils.getAnnotation( annotation, accessors );
    }
}
