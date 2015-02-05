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

import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

/**
 * @author Nicolas Morel
 */
public abstract class FieldAccessor {

    public static class Accessor {

        private final CodeBlock accessor;

        private final Optional<MethodSpec> additionalMethod;

        public Accessor( CodeBlock accessor ) {
            Preconditions.checkNotNull( accessor );
            this.accessor = accessor;
            this.additionalMethod = Optional.absent();
        }

        public Accessor( CodeBlock accessor, MethodSpec additionalMethod ) {
            Preconditions.checkNotNull( accessor );
            Preconditions.checkNotNull( additionalMethod );

            this.accessor = accessor;
            this.additionalMethod = Optional.of( additionalMethod );
        }

        public CodeBlock getAccessor() {
            return accessor;
        }

        public Optional<MethodSpec> getAdditionalMethod() {
            return additionalMethod;
        }
    }

    protected final String propertyName;

    private final boolean samePackage;

    protected final Optional<JField> field;

    protected final Optional<JMethod> method;

    private final boolean useMethod;

    protected FieldAccessor( String propertyName, boolean samePackage, boolean fieldAutoDetect, Optional<JField> field,
                             boolean methodAutoDetect, Optional<JMethod> method ) {
        Preconditions.checkNotNull( propertyName );
        Preconditions.checkArgument( field.isPresent() || method.isPresent(), "At least one of the field or method must be given" );

        this.propertyName = propertyName;
        this.samePackage = samePackage;
        this.field = field;
        this.method = method;

        // We first test if we can use the method
        if ( method.isPresent() && (methodAutoDetect || !fieldAutoDetect || !field.isPresent()) ) {
            useMethod = true;
        }
        // else use the field
        else {
            useMethod = false;
        }
    }

    public Optional<JField> getField() {
        return field;
    }

    public Optional<JMethod> getMethod() {
        return method;
    }

    public Accessor getAccessor( final String beanName, Object... params ) {
        final boolean useJsni;
        if ( useMethod ) {
            useJsni = method.get().isPrivate() || (!samePackage && !method.get().isPublic());
        }
        // else use the field
        else {
            useJsni = field.get().isPrivate() || (!samePackage && !field.get().isPublic());
        }
        return getAccessor( beanName, useMethod, useJsni, params );
    }

    protected abstract Accessor getAccessor( final String beanName, final boolean useMethod, final boolean useJsni, Object... params );
}
