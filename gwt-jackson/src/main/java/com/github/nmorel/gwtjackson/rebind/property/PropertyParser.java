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

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.github.nmorel.gwtjackson.rebind.RebindConfiguration;
import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.google.gwt.thirdparty.guava.common.collect.Maps;

/**
 * Utility class used to parse a bean looking for all its properties
 *
 * @author Nicolas Morel.
 */
public final class PropertyParser {

    public static ImmutableMap<String, PropertyAccessors> findPropertyAccessors( final RebindConfiguration configuration,
                                                                                 TreeLogger logger, BeanInfo beanInfo ) {
        Map<String, PropertyAccessorsBuilder> fieldsAndMethodsMap = new LinkedHashMap<String, PropertyAccessorsBuilder>();
        parse( configuration, logger, beanInfo.getType(), fieldsAndMethodsMap, false );

        Map<String, PropertyAccessorsBuilder> propertyAccessors = new LinkedHashMap<String, PropertyAccessorsBuilder>();
        for ( PropertyAccessorsBuilder fieldAccessors : fieldsAndMethodsMap.values() ) {
            propertyAccessors.put( fieldAccessors.computePropertyName(), fieldAccessors );
        }

        if ( !beanInfo.getCreatorParameters().isEmpty() ) {
            for ( Entry<String, JParameter> entry : beanInfo.getCreatorParameters().entrySet() ) {
                // If there is a constructor parameter referencing this property, we add it to the accessors
                PropertyAccessorsBuilder fieldAccessors = propertyAccessors.get( entry.getKey() );
                if ( null == fieldAccessors ) {
                    fieldAccessors = new PropertyAccessorsBuilder( entry.getKey() );
                    fieldAccessors.setParameter( entry.getValue() );
                    propertyAccessors.put( fieldAccessors.computePropertyName(), fieldAccessors );
                } else {
                    fieldAccessors.setParameter( entry.getValue() );
                }
            }
        }

        return ImmutableMap.copyOf( Maps.transformValues( propertyAccessors, new Function<PropertyAccessorsBuilder, PropertyAccessors>() {

            @Override
            public PropertyAccessors apply( @Nullable PropertyAccessorsBuilder propertyAccessorsBuilder ) {
                return null == propertyAccessorsBuilder ? null : propertyAccessorsBuilder.build();
            }
        } ) );
    }

    private static void parse( RebindConfiguration configuration, TreeLogger logger, JClassType type, Map<String,
            PropertyAccessorsBuilder> propertiesMap, boolean mixin ) {
        if ( null == type ) {
            return;
        }

        if ( !mixin ) {
            Optional<JClassType> mixinAnnotation = configuration.getMixInAnnotations( type );
            if ( mixinAnnotation.isPresent() ) {
                parse( configuration, logger, mixinAnnotation.get(), propertiesMap, true );
            }
        }

        parseFields( logger, type, propertiesMap, mixin );
        parseMethods( logger, type, propertiesMap, mixin );

        for ( JClassType interf : type.getImplementedInterfaces() ) {
            parse( configuration, logger, interf, propertiesMap, mixin );
        }

        parse( configuration, logger, type.getSuperclass(), propertiesMap, mixin );
    }

    private static void parseFields( TreeLogger logger, JClassType type, Map<String, PropertyAccessorsBuilder> propertiesMap,
                                     boolean mixin ) {
        if ( type.getQualifiedSourceName().equals( "java.lang.Object" ) ) {
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
            if ( property.getField().isPresent() && !mixin ) {
                // we found an other field with the same name on a superclass. we ignore it
                logger.log( Type.INFO, "A field with the same name as '" + field
                        .getName() + "' has already been found on child class" );
            } else {
                property.addField( field, mixin );
            }
        }
    }

    private static void parseMethods( TreeLogger logger, JClassType type, Map<String, PropertyAccessorsBuilder> propertiesMap,
                                      boolean mixin ) {
        for ( JMethod method : type.getMethods() ) {
            if ( null != method.isConstructor() || method.isStatic() || (type.getQualifiedSourceName()
                    .equals( "java.lang.Object" ) && method.getName().equals( "getClass" )) ) {
                continue;
            }

            JType returnType = method.getReturnType();
            if ( null != returnType.isPrimitive() && JPrimitiveType.VOID.equals( returnType.isPrimitive() ) ) {
                // might be a setter
                if ( method.getParameters().length == 1 || (method.getParameters().length == 2 && method.isAnnotationPresent( JsonAnySetter.class )) ) {
                    String fieldName = extractFieldNameFromGetterSetterMethodName( method.getName() );
                    PropertyAccessorsBuilder property = propertiesMap.get( fieldName );
                    if ( null == property ) {
                        property = new PropertyAccessorsBuilder( fieldName );
                        propertiesMap.put( fieldName, property );
                    }
                    property.addSetter( method, mixin );
                }
            } else {
                // might be a getter
                if ( method.getParameters().length == 0 ) {
                    String fieldName = extractFieldNameFromGetterSetterMethodName( method.getName() );
                    PropertyAccessorsBuilder property = propertiesMap.get( fieldName );
                    if ( null == property ) {
                        property = new PropertyAccessorsBuilder( fieldName );
                        propertiesMap.put( fieldName, property );
                    }
                    property.addGetter( method, mixin );
                }
            }
        }
    }

    private static String extractFieldNameFromGetterSetterMethodName( String methodName ) {
        String fieldName;
        if ( methodName.startsWith( "is" ) && methodName.length() > 2 ) {
            fieldName = methodName.substring( 2 );
        } else if ( (methodName.startsWith( "get" ) || methodName.startsWith( "set" )) && methodName.length() > 3 ) {
            fieldName = methodName.substring( 3 );
        } else {
            fieldName = methodName;
        }

        int index = 0;
        while ( Character.isUpperCase( fieldName.charAt( index++ ) ) && index < fieldName.length() ) {
        }
        return fieldName.substring( 0, index ).toLowerCase() + fieldName.substring( index );
    }

}
