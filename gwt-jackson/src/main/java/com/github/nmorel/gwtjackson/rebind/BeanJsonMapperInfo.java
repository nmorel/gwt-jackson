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

import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

/**
 * @author Nicolas Morel
 */
public class BeanJsonMapperInfo {

    private final JClassType type;

    private final String qualifiedSerializerClassName;

    private final String simpleSerializerClassName;

    private final String qualifiedDeserializerClassName;

    private final String simpleDeserializerClassName;

    private final String genericClassParameters;

    private final String genericClassBoundedParameters;

    private final BeanInfo beanInfo;

    private final ImmutableMap<String, PropertyInfo> properties;

    public BeanJsonMapperInfo( JClassType type, String qualifiedSerializerClassName, String simpleSerializerClassName, String
            qualifiedDeserializerClassName, String simpleDeserializerClassName, BeanInfo beanInfo, ImmutableMap<String,
            PropertyInfo> properties ) {
        this.type = type;
        this.qualifiedSerializerClassName = qualifiedSerializerClassName;
        this.simpleSerializerClassName = simpleSerializerClassName;
        this.qualifiedDeserializerClassName = qualifiedDeserializerClassName;
        this.simpleDeserializerClassName = simpleDeserializerClassName;
        this.beanInfo = beanInfo;
        this.properties = properties;

        if ( null != type.isGenericType() && type.isGenericType().getTypeParameters().length > 0 ) {
            StringBuilder genericClassParametersBuilder = new StringBuilder();
            StringBuilder genericClassBoundedParametersBuilder = new StringBuilder();
            for ( JTypeParameter parameter : type.isGenericType().getTypeParameters() ) {
                if ( genericClassParametersBuilder.length() == 0 ) {
                    genericClassParametersBuilder.append( '<' );
                    genericClassBoundedParametersBuilder.append( '<' );
                } else {
                    genericClassParametersBuilder.append( ", " );
                    genericClassBoundedParametersBuilder.append( ", " );
                }
                genericClassParametersBuilder.append( parameter.getName() );

                genericClassBoundedParametersBuilder.append( parameter.getName() );
                if ( !(parameter.getBounds().length == 1 && parameter.getBounds()[0].getQualifiedSourceName().equals( Object.class
                        .getName() )) ) {
                    for ( int i = 0; i < parameter.getBounds().length; i++ ) {
                        if ( i == 0 ) {
                            genericClassBoundedParametersBuilder.append( " extends " );
                        } else {
                            genericClassBoundedParametersBuilder.append( " & " );
                        }
                        JClassType bound = parameter.getBounds()[i];
                        genericClassBoundedParametersBuilder.append( bound.getParameterizedQualifiedSourceName() );
                    }
                }
            }
            genericClassParametersBuilder.append( '>' );
            genericClassBoundedParametersBuilder.append( '>' );
            genericClassParameters = genericClassParametersBuilder.toString();
            genericClassBoundedParameters = genericClassBoundedParametersBuilder.toString();
        } else {
            genericClassParameters = "";
            genericClassBoundedParameters = "";
        }
    }

    public JClassType getType() {
        return type;
    }

    public String getQualifiedSerializerClassName() {
        return qualifiedSerializerClassName;
    }

    public String getSimpleSerializerClassName() {
        return simpleSerializerClassName;
    }

    public String getQualifiedDeserializerClassName() {
        return qualifiedDeserializerClassName;
    }

    public String getSimpleDeserializerClassName() {
        return simpleDeserializerClassName;
    }

    public String getGenericClassParameters() {
        return genericClassParameters;
    }

    public String getGenericClassBoundedParameters() {
        return genericClassBoundedParameters;
    }

    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    public ImmutableMap<String, PropertyInfo> getProperties() {
        return properties;
    }
}
