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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.extractBeanType;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findAnnotationOnAnyAccessor;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

/**
 * @author Nicolas Morel
 */
public class BeanTypeInfo {

    public static BeanTypeInfo process( TreeLogger logger, JacksonTypeOracle typeOracle,
                                        JClassType type ) throws UnableToCompleteException {
        return process( logger, typeOracle, type, null, false );
    }

    public static BeanTypeInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, JType type,
                                        FieldAccessors fieldAccessors ) throws UnableToCompleteException {
        JClassType classType = extractBeanType( typeOracle, type );
        if ( null == classType ) {
            return null;
        } else {
            return process( logger, typeOracle, classType, fieldAccessors, true );
        }
    }

    private static BeanTypeInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, JClassType type, FieldAccessors fieldAccessors,
                                         boolean property ) throws UnableToCompleteException {
        JsonTypeInfo jsonTypeInfo = null;
        JsonSubTypes propertySubTypes = null;
        if ( property ) {
            jsonTypeInfo = findAnnotationOnAnyAccessor( fieldAccessors, JsonTypeInfo.class );
            propertySubTypes = findAnnotationOnAnyAccessor( fieldAccessors, JsonSubTypes.class );
            if ( null == jsonTypeInfo && null == propertySubTypes ) {
                // no override on field
                return null;
            }
        }

        if ( null == jsonTypeInfo ) {
            jsonTypeInfo = findFirstEncounteredAnnotationsOnAllHierarchy( type, JsonTypeInfo.class );
            if ( null == jsonTypeInfo ) {
                return null;
            }
        }

        BeanTypeInfo result = new BeanTypeInfo();
        result.use = jsonTypeInfo.use();
        result.include = jsonTypeInfo.include();
        if ( JsonTypeInfo.As.PROPERTY.equals( jsonTypeInfo.include() ) ) {
            result.propertyName = jsonTypeInfo.property().isEmpty() ? jsonTypeInfo.use().getDefaultPropertyName() : jsonTypeInfo.property();
        }

        Map<JClassType, String> classToMetadata = new HashMap<JClassType, String>();
        JsonSubTypes typeSubTypes = findFirstEncounteredAnnotationsOnAllHierarchy( type, JsonSubTypes.class );
        List<JClassType> allSubtypes = Arrays.asList( type.getSubtypes() );
        classToMetadata.put( type, extractTypeMetadata( logger, type, type, jsonTypeInfo, propertySubTypes, typeSubTypes, allSubtypes ) );

        for ( JClassType subtype : type.getSubtypes() ) {
            classToMetadata
                .put( subtype, extractTypeMetadata( logger, type, subtype, jsonTypeInfo, propertySubTypes, typeSubTypes, allSubtypes ) );
        }

        result.mapTypeToMetadata = classToMetadata;

        return result;
    }

    private static String extractTypeMetadata( TreeLogger logger, JClassType baseType, JClassType subtype, JsonTypeInfo typeInfo,
                                               JsonSubTypes propertySubTypes, JsonSubTypes baseSubTypes,
                                               List<JClassType> allSubtypes ) throws UnableToCompleteException {
        switch ( typeInfo.use() ) {
            case NAME:
                // we first look the name on JsonSubTypes annotations. Top ones override the bottom ones.
                String name = findNameOnJsonSubTypes( baseType, subtype, allSubtypes, propertySubTypes, baseSubTypes );
                if ( null != name && !"".equals( name ) ) {
                    return name;
                }

                // we look if the name is defined on the type with JsonTypeName
                JsonTypeName typeName = findFirstEncounteredAnnotationsOnAllHierarchy( subtype, JsonTypeName.class );
                if ( null != typeName && null != typeName.value() && !typeName.value().isEmpty() ) {
                    return typeName.value();
                }

                // we use the default name (ie simple name of the class)
                String simpleBinaryName = subtype.getQualifiedBinaryName();
                int indexLastDot = simpleBinaryName.lastIndexOf( '.' );
                if ( indexLastDot != -1 ) {
                    simpleBinaryName = simpleBinaryName.substring( indexLastDot + 1 );
                }
                return simpleBinaryName;
            case MINIMAL_CLASS:
                if ( !baseType.getPackage().isDefault() ) {
                    String basePackage = baseType.getPackage().getName();
                    if ( subtype.getQualifiedBinaryName().startsWith( basePackage + "." ) ) {
                        return subtype.getQualifiedBinaryName().substring( basePackage.length() );
                    }
                }
            case CLASS:
                return subtype.getQualifiedBinaryName();
            default:
                logger.log( TreeLogger.Type.ERROR, "JsonTypeInfo.Id." + typeInfo.use() + " is not supported" );
                throw new UnableToCompleteException();
        }
    }

    private static String findNameOnJsonSubTypes( JClassType baseType, JClassType subtype, List<JClassType> allSubtypes,
                                                  JsonSubTypes propertySubTypes, JsonSubTypes baseSubTypes ) {
        JsonSubTypes.Type typeFound = findTypeOnSubTypes( subtype, propertySubTypes );
        if ( null != typeFound ) {
            return typeFound.name();
        }

        typeFound = findTypeOnSubTypes( subtype, baseSubTypes );
        if ( null != typeFound ) {
            return typeFound.name();
        }

        if ( baseType != subtype ) {
            // we look in all the hierarchy
            JClassType type = subtype;
            while ( null != type ) {
                if ( allSubtypes.contains( type ) ) {
                    JsonSubTypes.Type found = findTypeOnSubTypes( subtype, type.getAnnotation( JsonSubTypes.class ) );
                    if ( null != found ) {
                        typeFound = found;
                    }
                }
                type = type.getSuperclass();
            }

            if ( null != typeFound ) {
                return typeFound.name();
            }
        }

        return null;
    }

    private static JsonSubTypes.Type findTypeOnSubTypes( JClassType subtype, JsonSubTypes jsonSubTypes ) {
        if ( null != jsonSubTypes ) {
            for ( JsonSubTypes.Type type : jsonSubTypes.value() ) {
                if ( type.value().getName().equals( subtype.getQualifiedBinaryName() ) ) {
                    return type;
                }
            }
        }
        return null;
    }

    private Id use;

    private As include;

    private String propertyName;

    private Map<JClassType, String> mapTypeToMetadata;

    private BeanTypeInfo() {
    }

    public Id getUse() {
        return use;
    }

    public As getInclude() {
        return include;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Map<JClassType, String> getMapTypeToMetadata() {
        return mapTypeToMetadata;
    }
}
