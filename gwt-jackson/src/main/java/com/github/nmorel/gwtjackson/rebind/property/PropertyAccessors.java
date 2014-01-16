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

package com.github.nmorel.gwtjackson.rebind.property;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;

/**
 * Used to aggregate field, getter/setter method or parameter of the same property
 *
 * @author Nicolas Morel
 */
public interface PropertyAccessors {

    /**
     * Returns the name of the property. It can be the value defined in a {@link JsonProperty#value()},
     * the name of a field or the name of a getter/setter method minus get/set.
     *
     * @return the name of the property
     */
    String getPropertyName();

    /**
     * @return the field corresponding to the property
     */
    Optional<JField> getField();

    /**
     * @return the main getter corresponding to the property
     */
    Optional<JMethod> getGetter();

    /**
     * @return the main setter corresponding to the property
     */
    Optional<JMethod> getSetter();

    /**
     * @return additionnal getters found on superclass
     */
    List<JMethod> getGetters();

    /**
     * @return additionnal setters found on superclass
     */
    List<JMethod> getSetters();

    /**
     * @return the constructor parameter corresponding to the property
     */
    Optional<JParameter> getParameter();

}
