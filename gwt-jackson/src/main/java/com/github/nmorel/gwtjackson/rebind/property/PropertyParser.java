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

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.rebind.BeanInfo;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.google.gwt.thirdparty.guava.common.collect.Maps;

/**
 * @author Nicolas Morel.
 */
public final class PropertyParser {

    public static ImmutableMap<String, PropertyAccessors> findPropertyAccessors( TreeLogger logger, BeanInfo beanInfo ) {
        Map<String, PropertyAccessorsBuilder> fieldsAndMethodsMap = new LinkedHashMap<String, PropertyAccessorsBuilder>();
        parseFields( logger, beanInfo.getType(), fieldsAndMethodsMap );
        parseMethods( logger, beanInfo.getType(), fieldsAndMethodsMap );

        Map<String, PropertyAccessorsBuilder> propertyAccessors = new LinkedHashMap<String, PropertyAccessorsBuilder>();
        for ( PropertyAccessorsBuilder fieldAccessors : fieldsAndMethodsMap.values() ) {
            fieldAccessors.prebuild();
            propertyAccessors.put( fieldAccessors.getPropertyName(), fieldAccessors );
        }

        if ( !beanInfo.getCreatorParameters().isEmpty() ) {
            for ( Entry<String, JParameter> entry : beanInfo.getCreatorParameters().entrySet() ) {
                // If there is a constructor parameter referencing this property, we add it to the accessors
                PropertyAccessorsBuilder fieldAccessors = propertyAccessors.get( entry.getKey() );
                if ( null == fieldAccessors ) {
                    fieldAccessors = new PropertyAccessorsBuilder( entry.getKey() );
                    propertyAccessors.put( entry.getKey(), fieldAccessors );
                }
                fieldAccessors.setParameter( entry.getValue() );
            }
        }

        return ImmutableMap.copyOf( Maps.transformValues( propertyAccessors, new Function<PropertyAccessorsBuilder, PropertyAccessors>() {

            @Override
            public PropertyAccessors apply( @Nullable PropertyAccessorsBuilder propertyAccessorsBuilder ) {
                return null == propertyAccessorsBuilder ? null : propertyAccessorsBuilder.build();
            }
        } ) );
    }

    private static void parseFields( TreeLogger logger, JClassType type, Map<String, PropertyAccessorsBuilder> propertiesMap ) {
        if ( null == type || type.getQualifiedSourceName().equals( "java.lang.Object" ) ) {
            return;
        }

        for ( JField field : type.getFields() ) {
            if ( field.isStatic() ) {
                continue;
            }

            String fieldName = field.getName();
            PropertyAccessorsBuilder property = propertiesMap.get( fieldName );
            if ( null == property ) {
                property = new PropertyAccessorsBuilder( fieldName );
                propertiesMap.put( fieldName, property );
            }
            if ( property.getField().isPresent() ) {
                // we found an other field with the same name on a superclass. we ignore it
                logger.log( TreeLogger.Type.WARN, "A field with the same name as " + field
                        .getName() + " has already been found on child class" );
            } else {
                property.setField( field );
            }
        }
        parseFields( logger, type.getSuperclass(), propertiesMap );
    }

    private static void parseMethods( TreeLogger logger, JClassType type, Map<String, PropertyAccessorsBuilder> propertiesMap ) {
        if ( null == type || type.getQualifiedSourceName().equals( "java.lang.Object" ) ) {
            return;
        }

        for ( JMethod method : type.getMethods() ) {
            if ( null != method.isConstructor() || method.isStatic() ) {
                continue;
            }

            JType returnType = method.getReturnType();
            if ( null != returnType.isPrimitive() && JPrimitiveType.VOID.equals( returnType.isPrimitive() ) ) {
                // might be a setter
                if ( method.getParameters().length == 1 ) {
                    String methodName = method.getName();
                    if ( (methodName.startsWith( "set" ) && methodName.length() > 3) || method.isAnnotationPresent( JsonProperty.class ) ) {
                        // it's a setter method
                        String fieldName = extractFieldNameFromGetterSetterMethodName( methodName );
                        PropertyAccessorsBuilder property = propertiesMap.get( fieldName );
                        if ( null == property ) {
                            property = new PropertyAccessorsBuilder( fieldName );
                            propertiesMap.put( fieldName, property );
                        }
                        property.addSetter( method );
                    }
                }
            } else {
                // might be a getter
                if ( method.getParameters().length == 0 ) {
                    String methodName = method.getName();
                    if ( (methodName.startsWith( "get" ) && methodName.length() > 3) || (methodName.startsWith( "is" ) && methodName
                            .length() > 2 && null != returnType.isPrimitive() && JPrimitiveType.BOOLEAN.equals( returnType
                            .isPrimitive() )) || method.isAnnotationPresent( JsonProperty.class ) ) {
                        // it's a getter method
                        String fieldName = extractFieldNameFromGetterSetterMethodName( methodName );
                        PropertyAccessorsBuilder property = propertiesMap.get( fieldName );
                        if ( null == property ) {
                            property = new PropertyAccessorsBuilder( fieldName );
                            propertiesMap.put( fieldName, property );
                        }
                        property.addGetter( method );
                    }
                }
            }
        }

        for ( JClassType interf : type.getImplementedInterfaces() ) {
            parseMethods( logger, interf, propertiesMap );
        }

        parseMethods( logger, type.getSuperclass(), propertiesMap );
    }

    private static String extractFieldNameFromGetterSetterMethodName( String methodName ) {
        if ( methodName.startsWith( "is" ) ) {
            return methodName.substring( 2, 3 ).toLowerCase() + methodName.substring( 3 );
        } else if ( methodName.startsWith( "get" ) || methodName.startsWith( "set" ) ) {
            return methodName.substring( 3, 4 ).toLowerCase() + methodName.substring( 4 );
        } else {
            return methodName;
        }
    }

}
