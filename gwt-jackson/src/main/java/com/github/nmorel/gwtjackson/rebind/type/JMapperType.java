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

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;

/**
 * Contains informations about serializer or deserializer like its type or the string to instantiate it.
 *
 * @author Nicolas Morel
 */
public abstract class JMapperType {

    protected static abstract class Builder<B extends Builder, M extends JMapperType> {

        protected JClassType mapperType;

        protected JType type;

        protected String instance;

        protected M[] parameters;

        public B mapperType( JClassType mapperType ) {
            this.mapperType = mapperType;
            return (B) this;
        }

        public B type( JType type ) {
            this.type = type;
            return (B) this;
        }

        public B instance( String instance ) {
            this.instance = instance;
            return (B) this;
        }

        public B parameters( M[] parameters ) {
            this.parameters = parameters;
            return (B) this;
        }
    }

    protected final JClassType mapperType;

    protected final JType type;

    protected final String instance;

    protected final JMapperType[] parameters;

    protected JMapperType( JClassType mapperType, JType type, String instance, JMapperType[] parameters ) {
        this.mapperType = mapperType;
        this.type = type;
        this.instance = instance;
        this.parameters = parameters;
    }

    public JClassType getMapperType() {
        return mapperType;
    }

    public JType getType() {
        return type;
    }

    public String getInstance() {
        return instance;
    }

    public JMapperType[] getParameters() {
        return parameters;
    }
}
