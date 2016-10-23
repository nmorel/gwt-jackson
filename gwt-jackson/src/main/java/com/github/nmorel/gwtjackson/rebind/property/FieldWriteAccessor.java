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
final class FieldWriteAccessor extends FieldAccessor {

    private static interface Converter {

        String convert( int index, JType type );

    }

    FieldWriteAccessor( String propertyName, boolean samePackage, boolean fieldAutoDetect, Optional<JField> field, boolean
            setterAutoDetect, Optional<JMethod> setter ) {
        super( propertyName, samePackage, fieldAutoDetect, field, setterAutoDetect, setter );
    }

    /** {@inheritDoc} */
    @Override
    protected Accessor getAccessor( final String beanName, final boolean useMethod, final boolean useJsni, Object... obj ) {
        final JType[] fieldsType;
        final JClassType enclosingType;
        if ( useMethod ) {
            fieldsType = method.get().getParameterTypes();
            enclosingType = method.get().getEnclosingType();
        } else {
            fieldsType = new JType[]{field.get().getType()};
            enclosingType = field.get().getEnclosingType();
        }

        String params = parametersToString( fieldsType, new Converter() {
            @Override
            public String convert( int index, JType type ) {
                return "$L";
            }
        } );

        if ( !useJsni ) {
            if ( useMethod ) {
                return new Accessor( CodeBlock.builder().add( beanName + "." + method.get().getName() + "(" + params + ")", obj ).build() );
            } else {
                return new Accessor( CodeBlock.builder().add( beanName + "." + field.get().getName() + " = " + params, obj ).build() );
            }
        }

        JsniCodeBlockBuilder jsniCode = JsniCodeBlockBuilder.builder();

        Converter typeToVariableName = new Converter() {
            @Override
            public String convert( int index, JType type ) {
                return "value" + index;
            }
        };
        String values = parametersToString( fieldsType, typeToVariableName );

        if ( useMethod ) {
            String jni = parametersToString( fieldsType, null, new Converter() {
                @Override
                public String convert( int index, JType type ) {
                    return type.getJNISignature();
                }
            } );
            jsniCode.addStatement( "bean.@$L::$L($L)($L)", enclosingType.getQualifiedSourceName(), method.get().getName(), jni, values );
        } else {
            jsniCode.addStatement( "bean.@$L::$L = $L", enclosingType.getQualifiedSourceName(), field.get().getName(), values );
        }

        MethodSpec.Builder additionalMethodBuilder = MethodSpec.methodBuilder( "setValueWithJsni" )
                .addModifiers( Modifier.PRIVATE, Modifier.NATIVE )
                .addParameter( typeName( enclosingType ), "bean" )
                .addCode( jsniCode.build() );
        for ( int i = 0; i < fieldsType.length; i++ ) {
            JType fieldType = fieldsType[i];
            additionalMethodBuilder.addParameter( typeName( fieldType ), typeToVariableName.convert( i, fieldType ) );
        }
        MethodSpec additionalMethod = additionalMethodBuilder.build();

        CodeBlock accessor = CodeBlock.builder().add( "$N($L, $L)",
                additionalMethod, beanName, CodeBlock.builder().add( params, obj )
                        .build() )
                .build();

        return new Accessor( accessor, additionalMethod );
    }

    private String parametersToString( JType[] types, Converter converter ) {
        return parametersToString( types, ", ", converter );
    }

    private String parametersToString( JType[] types, String separator, Converter converter ) {
        StringBuilder builder = new StringBuilder();

        for ( int i = 0; i < types.length; i++ ) {
            if ( i > 0 && null != separator ) {
                builder.append( separator );
            }
            builder.append( converter.convert( i, types[i] ) );
        }
        return builder.toString();
    }
}
