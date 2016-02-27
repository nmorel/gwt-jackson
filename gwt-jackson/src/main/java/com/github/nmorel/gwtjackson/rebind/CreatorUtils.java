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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.github.nmorel.gwtjackson.client.stream.impl.DefaultJsonWriter;
import com.github.nmorel.gwtjackson.rebind.type.JMapperType;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.HasAnnotations;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

/**
 * @author Nicolas Morel
 */
public final class CreatorUtils {

    /**
     * Browse all the hierarchy of the type and return the first corresponding annotation it found
     *
     * @param type type
     * @param annotation annotation to find
     * @param <T> type of the annotation
     *
     * @return the annotation if found, null otherwise
     */
    public static <T extends Annotation> Optional<T> findFirstEncounteredAnnotationsOnAllHierarchy( RebindConfiguration configuration,
                                                                                                    JClassType type, Class<T> annotation ) {
        return findFirstEncounteredAnnotationsOnAllHierarchy( configuration, type, annotation, Optional.<JClassType>absent() );
    }

    /**
     * Browse all the hierarchy of the type and return the first corresponding annotation it found
     *
     * @param type type
     * @param annotation annotation to find
     * @param <T> type of the annotation
     * @param ignoreType type to ignore when browsing the hierarchy
     *
     * @return the annotation if found, null otherwise
     */
    private static <T extends Annotation> Optional<T> findFirstEncounteredAnnotationsOnAllHierarchy( RebindConfiguration configuration,
                                                                                                    JClassType type, Class<T> annotation,
                                                                                                    Optional<JClassType> ignoreType ) {
        JClassType currentType = type;
        while ( null != currentType ) {
            Optional<JClassType> mixin = configuration.getMixInAnnotations( currentType );
            if ( mixin.isPresent() && mixin.get().isAnnotationPresent( annotation ) ) {
                return Optional.of( mixin.get().getAnnotation( annotation ) );
            }
            if ( currentType.isAnnotationPresent( annotation ) ) {
                return Optional.of( currentType.getAnnotation( annotation ) );
            }
            for ( JClassType interf : currentType.getImplementedInterfaces() ) {
                if (!ignoreType.isPresent() || !ignoreType.get().equals( interf )) {
                    Optional<T> annot = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, interf, annotation );
                    if ( annot.isPresent() ) {
                        return annot;
                    }
                }
            }
            currentType = currentType.getSuperclass();
            if ( ignoreType.isPresent() && ignoreType.get().equals( currentType ) ) {
                currentType = null;
            }
        }
        return Optional.absent();
    }

    /**
     * Returns true when one of the type  has the specified annotation
     *
     * @param annotation the annotation
     * @param hasAnnotationsList the types
     * @param <T> Type of the annotation
     *
     * @return true if one the type has the annotation
     */
    public static <T extends Annotation> boolean isAnnotationPresent( Class<T> annotation, List<? extends HasAnnotations>
            hasAnnotationsList ) {
        for ( HasAnnotations accessor : hasAnnotationsList ) {
            if ( accessor.isAnnotationPresent( annotation ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the first occurence of the annotation found on the types
     *
     * @param annotation the annotation
     * @param hasAnnotationsList the types
     * @param <T> Type of the annotation
     *
     * @return the first occurence of the annotation found on the types
     */
    public static <T extends Annotation> Optional<T> getAnnotation( String annotation, List<? extends HasAnnotations>
            hasAnnotationsList ) {
        try {
            Class clazz = Class.forName( annotation );
            return getAnnotation( clazz, hasAnnotationsList );
        } catch ( ClassNotFoundException e ) {
            return Optional.absent();
        }
    }

    /**
     * Returns the first occurence of the annotation found on the types
     *
     * @param annotation the annotation
     * @param hasAnnotationsList the types
     * @param <T> Type of the annotation
     *
     * @return the first occurence of the annotation found on the types
     */
    public static <T extends Annotation> Optional<T> getAnnotation( Class<T> annotation, List<? extends HasAnnotations>
            hasAnnotationsList ) {
        for ( HasAnnotations accessor : hasAnnotationsList ) {
            if ( accessor.isAnnotationPresent( annotation ) ) {
                return Optional.of( accessor.getAnnotation( annotation ) );
            }
        }
        return Optional.absent();
    }

    /**
     * Returns the default value of the given type.
     * <ul>
     * <li>{@link Object} : null</li>
     * <li>char : '\u0000'</li>
     * <li>boolean : false</li>
     * <li>other primitive : 0</li>
     * </ul>
     *
     * @param type type to find the default value
     *
     * @return the default value of the type.
     */
    public static String getDefaultValueForType( JType type ) {
        JPrimitiveType primitiveType = type.isPrimitive();
        if ( null != primitiveType ) {
            return primitiveType.getUninitializedFieldExpression();
        }
        return "null";
    }

    /**
     * @param type the type to test
     *
     * @return true if the type is {@link Object}, false otherwise
     */
    public static boolean isObject( JType type ) {
        return null != type.isClass() && Object.class.getName().equals( type.getQualifiedSourceName() );
    }

    /**
     * @param type the type to test
     *
     * @return true if the type is {@link Serializable}, false otherwise
     */
    public static boolean isSerializable( JType type ) {
        return null != type.isInterface() && Serializable.class.getName().equals( type.getQualifiedSourceName() );
    }

    /**
     * @param type the type to test
     *
     * @return true if the type is {@link Object} or {@link Serializable}, false otherwise
     */
    public static boolean isObjectOrSerializable( JType type ) {
        return isObject( type ) || isSerializable( type );
    }

    public static ImmutableList<JClassType> filterSubtypesForSerialization( TreeLogger logger, RebindConfiguration configuration,
                                                                            JClassType type ) {
        boolean filterOnlySupportedType = isObjectOrSerializable( type );

        ImmutableList.Builder<JClassType> builder = ImmutableList.builder();
        if ( type.getSubtypes().length > 0 ) {
            for ( JClassType subtype : type.getSubtypes() ) {
                if ( null == subtype.isAnnotation()
                        && subtype.isPublic()
                        && (!filterOnlySupportedType || configuration.isTypeSupportedForSerialization( logger, subtype ))
                        && !findFirstEncounteredAnnotationsOnAllHierarchy( configuration, subtype.isClassOrInterface(), JsonIgnoreType.class, Optional.of( type ) ).isPresent()) {
                    builder.add( subtype );
                }
            }
        }
        return builder.build();
    }

    public static ImmutableList<JClassType> filterSubtypesForDeserialization( TreeLogger logger, RebindConfiguration configuration,
                                                                              JClassType type ) {
        boolean filterOnlySupportedType = isObjectOrSerializable( type );

        ImmutableList.Builder<JClassType> builder = ImmutableList.builder();
        if ( type.getSubtypes().length > 0 ) {
            for ( JClassType subtype : type.getSubtypes() ) {
                if ( (null == subtype.isInterface() && !subtype.isAbstract() && (!subtype.isMemberType() || subtype.isStatic()))
                        && null == subtype.isAnnotation()
                        && subtype.isPublic()
                        && (!filterOnlySupportedType ||
                        (configuration.isTypeSupportedForDeserialization( logger, subtype )
                                // EnumSet and EnumMap are not supported in subtype deserialization because we can't know the enum to use.
                                && !EnumSet.class.getCanonicalName().equals( subtype.getQualifiedSourceName() )
                                && !EnumMap.class.getCanonicalName().equals( subtype.getQualifiedSourceName() )))
                        && !findFirstEncounteredAnnotationsOnAllHierarchy( configuration, subtype.isClassOrInterface(), JsonIgnoreType.class, Optional.of( type ) ).isPresent() ) {
                    builder.add( subtype );
                }
            }
        }
        return builder.build();
    }

    /**
     * Escapes the {@link String} given in parameter
     *
     * @param value the {@link String}
     *
     * @return the escaped {@link String}
     */
    public static String escapeString( String value ) {
        return DefaultJsonWriter.encodeString( value );
    }

    /**
     * @param mapperType the type to search inside
     *
     * @return the first bean type encountered
     */
    public static JClassType findFirstTypeToApplyPropertyAnnotation( JMapperType mapperType ) {
        return findFirstTypeToApplyPropertyAnnotation( Arrays.asList( mapperType ) );
    }

    /**
     * @param mapperTypeList the types to search inside
     *
     * @return the first bean type encountered
     */
    private static JClassType findFirstTypeToApplyPropertyAnnotation( List<JMapperType> mapperTypeList ) {
        if ( mapperTypeList.isEmpty() ) {
            return null;
        }

        List<JMapperType> subLevel = new ArrayList<JMapperType>();
        for ( JMapperType mapperType : mapperTypeList ) {
            if ( mapperType.isBeanMapper() ) {
                return mapperType.getType().isClass();
            } else if ( mapperType.getParameters().size() > 0 ) {
                subLevel.addAll( mapperType.getParameters() );
            }
        }

        return findFirstTypeToApplyPropertyAnnotation( subLevel );
    }

    private CreatorUtils() {
    }
}
