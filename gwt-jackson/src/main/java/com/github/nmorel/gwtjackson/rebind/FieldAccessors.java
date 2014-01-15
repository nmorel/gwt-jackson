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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findAnnotationOnAnyAccessor;

/**
 * Used to aggregate field, getter method and setter method of the same field
 *
 * @author Nicolas Morel
 */
public class FieldAccessors {

    // name of the field
    private final String fieldName;

    // field
    private JField field;

    // getter method that will be called
    private JMethod getter;

    // additionnal getters found on superclass or interfaces that may contains annotations
    private List<JMethod> getters = new ArrayList<JMethod>();

    // setter method that will be called
    private JMethod setter;

    // additionnal setters found on superclass or interfaces that may contains annotations
    private List<JMethod> setters = new ArrayList<JMethod>();

    // the property's name. the same as fieldName unless a @JsonProperty annotation is found overriding the name
    private String propertyName;

    // constructor parameter
    private JParameter parameter;

    public FieldAccessors( String fieldName ) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public JField getField() {
        return field;
    }

    public void setField( JField field ) {
        this.field = field;
    }

    public JMethod getGetter() {
        return getter;
    }

    public void addGetter( JMethod getter ) {
        if ( null == this.getter && !getter.isAbstract() ) {
            this.getter = getter;
        } else {
            this.getters.add( getter );
        }
    }

    public JMethod getSetter() {
        return setter;
    }

    public void addSetter( JMethod setter ) {
        if ( null == this.setter && !setter.isAbstract() ) {
            this.setter = setter;
        } else {
            this.setters.add( setter );
        }
    }

    public List<JMethod> getGetters() {
        return getters;
    }

    public List<JMethod> getSetters() {
        return setters;
    }

    public String getPropertyName() {
        if ( null == propertyName ) {
            // determine the property name
            JsonProperty jsonProperty = findAnnotationOnAnyAccessor( this, JsonProperty.class );
            if ( null != jsonProperty && null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) ) {
                propertyName = jsonProperty.value();
            } else {
                propertyName = getFieldName();
            }
        }
        return propertyName;
    }

    public JParameter getParameter() {
        return parameter;
    }

    public void setParameter( JParameter parameter ) {
        this.parameter = parameter;
    }
}
