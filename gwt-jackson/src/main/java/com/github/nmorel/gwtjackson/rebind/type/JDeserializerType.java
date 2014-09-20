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

package com.github.nmorel.gwtjackson.rebind.type;

import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

/**
 * Contains informations about deserializer like its type or the string to instantiate it.
 *
 * @author Nicolas Morel
 */
public class JDeserializerType extends JMapperType {

    public static class Builder extends JMapperType.Builder<Builder, JDeserializerType> {

        public JDeserializerType build() {
            Preconditions.checkNotNull( instance, "instance is mandatory" );
            Preconditions.checkNotNull( type, "type is mandatory" );
            if ( null == parameters ) {
                parameters = ImmutableList.of();
            }
            return new JDeserializerType( beanMapper, type, instance, parameters );
        }
    }

    public JDeserializerType( boolean beanMapper, JType type, String instance, ImmutableList<JDeserializerType> parameters ) {
        super( beanMapper, type, instance, parameters );
    }
}
