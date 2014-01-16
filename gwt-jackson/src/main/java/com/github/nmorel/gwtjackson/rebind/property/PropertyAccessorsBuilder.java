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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findAnnotationOnAnyAccessor;

/**
 * Used to aggregate field, getter method and setter method of the same field
 *
 * @author Nicolas Morel
 */
class PropertyAccessorsBuilder implements PropertyAccessors {

    private final String fieldName;

    private Optional<JField> field = Optional.absent();

    private Optional<JMethod> getter = Optional.absent();

    private List<JMethod> getters = new ArrayList<JMethod>();

    private Optional<JMethod> setter = Optional.absent();

    private List<JMethod> setters = new ArrayList<JMethod>();

    private String propertyName;

    private Optional<JParameter> parameter = Optional.absent();

    public PropertyAccessorsBuilder( String fieldName ) {
        this.fieldName = fieldName;
    }

    @Override
    public Optional<JField> getField() {
        return field;
    }

    void setField( JField field ) {
        this.field = Optional.of( field );
    }

    @Override
    public Optional<JMethod> getGetter() {
        return getter;
    }

    void addGetter( JMethod getter ) {
        if ( !this.getter.isPresent() && !getter.isAbstract() ) {
            this.getter = Optional.of( getter );
        } else {
            this.getters.add( getter );
        }
    }

    @Override
    public Optional<JMethod> getSetter() {
        return setter;
    }

    void addSetter( JMethod setter ) {
        if ( !this.setter.isPresent() && !setter.isAbstract() ) {
            this.setter = Optional.of( setter );
        } else {
            this.setters.add( setter );
        }
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
        if ( null == propertyName ) {
            // determine the property name
            JsonProperty jsonProperty = findAnnotationOnAnyAccessor( this, JsonProperty.class );
            if ( null != jsonProperty && null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) ) {
                propertyName = jsonProperty.value();
            } else {
                propertyName = fieldName;
            }
        }
        return propertyName;
    }

    @Override
    public Optional<JParameter> getParameter() {
        return parameter;
    }

    void setParameter( JParameter parameter ) {
        this.parameter = Optional.of( parameter );
    }

    PropertyAccessors build() {
        return this;
    }
}
