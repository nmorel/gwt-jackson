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

package com.github.nmorel.gwtjackson.rebind.property.processor;

import com.github.nmorel.gwtjackson.rebind.property.AdditionalMethod;
import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.user.rebind.SourceWriter;

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

    @Override
    protected Accessor getAccessor( final String beanName, final boolean useMethod, final boolean useJsni ) {
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
                return "%s";
            }
        } );

        if ( !useJsni ) {
            if ( useMethod ) {
                return new Accessor( beanName + "." + method.get().getName() + "(" + params + ")" );
            } else {
                return new Accessor( beanName + "." + field.get().getName() + " = " + params );
            }
        }

        // field/setter has not been detected or is private or is in a different package. We use JSNI to access private setter/field.
        final String methodName = "set" + propertyName.substring( 0, 1 ).toUpperCase() + propertyName.substring( 1 );
        String accessor = methodName + "(" + beanName + ", " + params + ")";
        AdditionalMethod additionalMethod = new AdditionalMethod() {
            @Override
            public void write( SourceWriter source ) {
                String paramsSignature = parametersToString( fieldsType, new Converter() {
                    @Override
                    public String convert( int index, JType type ) {
                        return type.getParameterizedQualifiedSourceName() + " value" + index;
                    }
                } );

                String jni = parametersToString( fieldsType, new Converter() {
                    @Override
                    public String convert( int index, JType type ) {
                        return type.getJNISignature();
                    }
                } );

                String values = parametersToString( fieldsType, new Converter() {
                    @Override
                    public String convert( int index, JType type ) {
                        return "value" + index;
                    }
                } );

                source.println( "private native void %s(%s bean, %s) /*-{", methodName, enclosingType
                        .getParameterizedQualifiedSourceName(), paramsSignature );
                source.indent();
                if ( useMethod ) {
                    source.println( "bean.@%s::%s(%s)(%s);", enclosingType.getQualifiedSourceName(), method.get().getName(), jni, values );
                } else {
                    source.println( "bean.@%s::%s = %s;", enclosingType.getQualifiedSourceName(), field.get().getName(), values );
                }
                source.outdent();
                source.println( "}-*/;" );
            }
        };

        return new Accessor( accessor, additionalMethod );
    }

    private String parametersToString( JType[] types, Converter converter ) {
        StringBuilder builder = new StringBuilder();

        for ( int i = 0; i < types.length; i++ ) {
            if ( i > 0 ) {
                builder.append( ", " );
            }
            builder.append( converter.convert( i, types[i] ) );
        }
        return builder.toString();
    }
}
