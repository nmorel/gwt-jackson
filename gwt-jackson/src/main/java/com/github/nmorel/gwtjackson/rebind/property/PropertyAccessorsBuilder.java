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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.rebind.CreatorUtils;
import com.google.gwt.core.ext.typeinfo.HasAnnotations;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList.Builder;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

/**
 * Used to aggregate field, getter method and setter method of the same field
 *
 * @author Nicolas Morel
 */
class PropertyAccessorsBuilder implements PropertyAccessors {

    private boolean prebuilt;

    private final String fieldName;

    private ImmutableList<HasAnnotations> accessors;

    private Builder<HasAnnotations> accessorsBuilder = ImmutableList.builder();

    private Optional<JField> field = Optional.absent();

    private ImmutableList<JField> fields;

    private List<JField> fieldsBuilder = Lists.newArrayList();

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

    @Override
    public <T extends Annotation> boolean isAnnotationPresentOnField( Class<T> annotation ) {
        return CreatorUtils.isAnnotationPresent( annotation, fields );
    }

    @Override
    public <T extends Annotation> boolean isAnnotationPresentOnGetter( Class<T> annotation ) {
        return CreatorUtils.isAnnotationPresent( annotation, getters );
    }

    @Override
    public <T extends Annotation> boolean isAnnotationPresentOnSetter( Class<T> annotation ) {
        return CreatorUtils.isAnnotationPresent( annotation, setters );
    }

    @Override
    public <T extends Annotation> boolean isAnnotationPresent( Class<T> annotation ) {
        return CreatorUtils.isAnnotationPresent( annotation, accessors );
    }

    @Override
    public <T extends Annotation> T getAnnotation( Class<T> annotation ) {
        return getAnnotation( annotation, false );
    }

    @Override
    public <T extends Annotation> T getAnnotation( Class<T> annotation, boolean ignoreParameter ) {
        if ( !ignoreParameter && parameter.isPresent() && parameter.get().isAnnotationPresent( annotation ) ) {
            return parameter.get().getAnnotation( annotation );
        }

        return CreatorUtils.getAnnotation( annotation, accessors );
    }

    void addField( JField field, boolean mixin ) {
        assert !prebuilt : "cannot set field after prebuild";
        if ( this.fieldsBuilder.size() > 1 || (mixin && !this.field.isPresent() && this.fieldsBuilder.size() == 1) || (!mixin && this.field
                .isPresent()) ) {
            // we already found one mixin and one field type hierarchy
            // or we want to add a mixin but we have already one
            // or we want to add a field but we have already one
            return;
        }
        if ( !mixin ) {
            this.field = Optional.of( field );
        }
        this.fieldsBuilder.add( field );
        this.accessorsBuilder.add( field );
    }

    void addGetter( JMethod getter, boolean mixin ) {
        assert !prebuilt : "cannot add getter after prebuild";
        if ( !mixin && !this.getter.isPresent() /*&& !getter.isAbstract()*/ ) {
            this.getter = Optional.of( getter );
        }
        this.gettersBuilder.add( getter );
        this.accessorsBuilder.add( getter );
    }

    void addSetter( JMethod setter, boolean mixin ) {
        assert !prebuilt : "cannot add setter after prebuild";
        if ( !mixin && !this.setter.isPresent() /*&& !setter.isAbstract()*/ ) {
            this.setter = Optional.of( setter );
        }
        this.settersBuilder.add( setter );
        this.accessorsBuilder.add( setter );
    }

    void setParameter( JParameter parameter ) {
        this.parameter = Optional.of( parameter );
    }

    void prebuild() {
        assert !prebuilt : "cannot call prebuild twice";

        fields = ImmutableList.copyOf( fieldsBuilder );
        getters = gettersBuilder.build();
        setters = settersBuilder.build();
        accessors = accessorsBuilder.build();

        // determine the property name
        JsonProperty jsonProperty = getAnnotation( JsonProperty.class );
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
