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

import javax.lang.model.element.Modifier;

import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor;
import com.github.nmorel.gwtjackson.rebind.writer.JsniCodeBlockBuilder;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.typeName;

/**
 * @author Nicolas Morel
 */
final class FieldReadAccessor extends FieldAccessor {

    FieldReadAccessor( String propertyName, boolean samePackage, boolean fieldAutoDetect, Optional<JField> field,
                       boolean getterAutoDetect, Optional<JMethod> getter ) {
        super( propertyName, samePackage, fieldAutoDetect, field, getterAutoDetect, getter );
    }

    /** {@inheritDoc} */
    @Override
    protected Accessor getAccessor( final String beanName, final boolean useMethod, final boolean useJsni, Object... params ) {
        if ( !useJsni ) {
            if ( useMethod ) {
                return new Accessor( CodeBlock.builder().add( beanName + "." + method.get().getName() + "()" ).build() );
            } else {
                return new Accessor( CodeBlock.builder().add( beanName + "." + field.get().getName() ).build() );
            }
        }

        // field/getter has not been detected or is private or is in a different package. We use JSNI to access getter/field.
        final JType fieldType;
        final JClassType enclosingType;
        if ( useMethod ) {
            fieldType = method.get().getReturnType();
            enclosingType = method.get().getEnclosingType();
        } else {
            fieldType = field.get().getType();
            enclosingType = field.get().getEnclosingType();
        }

        JsniCodeBlockBuilder jsniCode = JsniCodeBlockBuilder.builder();
        if ( useMethod ) {
            jsniCode.addStatement( "return bean.@$L::$L()()", enclosingType.getQualifiedSourceName(), method.get().getName() );
        } else {
            jsniCode.addStatement( "return bean.@$L::$L", enclosingType.getQualifiedSourceName(), field.get().getName() );
        }

        MethodSpec additionalMethod = MethodSpec.methodBuilder( "getValueWithJsni" )
                .addModifiers( Modifier.PRIVATE, Modifier.NATIVE )
                .returns( typeName( fieldType ) )
                .addParameter( typeName( enclosingType ), "bean" )
                .addCode( jsniCode.build() )
                .build();

        CodeBlock accessor = CodeBlock.builder().add( "$N($L)", additionalMethod, beanName ).build();

        return new Accessor( accessor, additionalMethod );
    }
}
