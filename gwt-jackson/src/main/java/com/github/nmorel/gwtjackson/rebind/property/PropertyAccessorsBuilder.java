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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList.Builder;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findAnnotationOnAnyAccessor;

/**
 * Used to aggregate field, getter method and setter method of the same field
 *
 * @author Nicolas Morel
 */
class PropertyAccessorsBuilder implements PropertyAccessors {

    private boolean prebuilt;

    private final String fieldName;

    private Optional<JField> field = Optional.absent();

    private Optional<JMethod> getter = Optional.absent();

    private ImmutableList<JMethod> getters;

    private Builder<JMethod> gettersBuilder = ImmutableList.builder();

    private Optional<JMethod> setter = Optional.absent();

    private ImmutableList<JMethod> setters;

    private Builder<JMethod> settersBuilder = ImmutableList.builder();

    private String propertyName;

    private Optional<JParameter> parameter = Optional.absent();

    public PropertyAccessorsBuilder( String fieldName ) {
        this.fieldName = fieldName;
    }

    @Override
    public Optional<JField> getField() {
        return field;
    }

    @Override
    public Optional<JMethod> getGetter() {
        return getter;
    }

    @Override
    public Optional<JMethod> getSetter() {
        return setter;
    }

    @Override
    public List<JMethod> getGetters() {
        return getters;
    }

    @Override
    public List<JMethod> getSetters() {
        return setters;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Optional<JParameter> getParameter() {
        return parameter;
    }

    void setField( JField field ) {
        assert !prebuilt : "cannot set field after prebuild";
        this.field = Optional.of( field );
    }

    void addGetter( JMethod getter ) {
        assert !prebuilt : "cannot add getter after prebuild";
        if ( !this.getter.isPresent() && !getter.isAbstract() ) {
            this.getter = Optional.of( getter );
        } else {
            this.gettersBuilder.add( getter );
        }
    }

    void addSetter( JMethod setter ) {
        assert !prebuilt : "cannot add setter after prebuild";
        if ( !this.setter.isPresent() && !setter.isAbstract() ) {
            this.setter = Optional.of( setter );
        } else {
            this.settersBuilder.add( setter );
        }
    }

    void setParameter( JParameter parameter ) {
        this.parameter = Optional.of( parameter );
    }

    void prebuild() {
        assert !prebuilt : "cannot call prebuild twice";

        getters = gettersBuilder.build();
        setters = settersBuilder.build();

        // determine the property name
        JsonProperty jsonProperty = findAnnotationOnAnyAccessor( this, JsonProperty.class );
        if ( null != jsonProperty && null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) ) {
            propertyName = jsonProperty.value();
        } else {
            propertyName = fieldName;
        }

        prebuilt = true;
    }

    PropertyAccessors build() {
        if ( !prebuilt ) {
            prebuild();
        }
        return this;
    }
}
