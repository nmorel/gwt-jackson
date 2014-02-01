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

import com.github.nmorel.gwtjackson.rebind.PropertyInfo.AdditionalMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public class FieldReadAccessor extends FieldAccessor {

    protected FieldReadAccessor( String propertyName, boolean fieldAutoDetect, Optional<JField> field, boolean getterAutoDetect,
                                 Optional<JMethod> getter ) {
        super( propertyName, fieldAutoDetect, field, getterAutoDetect, getter );
    }

    @Override
    protected Accessor getAccessor( final String beanName, final boolean useMethod, final boolean useJsni ) {
        if ( !useJsni ) {
            if ( useMethod ) {
                return new Accessor( beanName + "." + method.get().getName() + "()" );
            } else {
                return new Accessor( beanName + "." + field.get().getName() );
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

        final String methodName = "get" + propertyName.substring( 0, 1 ).toUpperCase() + propertyName.substring( 1 );

        String accessor = methodName + "(" + beanName + ")";
        AdditionalMethod additionalMethod = new AdditionalMethod() {
            @Override
            public void write( SourceWriter source ) {
                source.println( "private native %s %s(%s bean) /*-{", fieldType
                        .getParameterizedQualifiedSourceName(), methodName, enclosingType.getParameterizedQualifiedSourceName() );
                source.indent();
                if ( useMethod ) {
                    source.println( "return bean.@%s::%s()();", enclosingType.getQualifiedSourceName(), method.get().getName() );
                } else {
                    source.println( "return bean.@%s::%s;", enclosingType.getQualifiedSourceName(), field.get().getName() );
                }
                source.outdent();
                source.println( "}-*/;" );
            }
        };

        return new Accessor( accessor, additionalMethod );
    }
}
