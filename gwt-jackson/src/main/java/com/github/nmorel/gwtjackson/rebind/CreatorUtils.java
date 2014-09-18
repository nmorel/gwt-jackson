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

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

import com.google.gwt.core.ext.typeinfo.HasAnnotations;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

/**
 * @author Nicolas Morel
 */
public final class CreatorUtils {

    public static final Function<Object, String> QUOTED_FUNCTION = new Function<Object, String>() {
        @Override
        public String apply( @Nullable Object o ) {
            if ( null == o ) {
                return null;
            }
            return "\"" + o + "\"";
        }
    };

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
                Optional<T> annot = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, interf, annotation );
                if ( annot.isPresent() ) {
                    return annot;
                }
            }
            currentType = currentType.getSuperclass();
        }
        return Optional.absent();
    }

    public static <T extends Annotation> boolean isAnnotationPresent( Class<T> annotation,
                                                                      List<? extends HasAnnotations> hasAnnotationsList ) {
        for ( HasAnnotations accessor : hasAnnotationsList ) {
            if ( accessor.isAnnotationPresent( annotation ) ) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Annotation> Optional<T> getAnnotation( Class<T> annotation,
                                                                    List<? extends HasAnnotations> hasAnnotationsList ) {
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

    public static ImmutableList<JClassType> filterSubtypes( JClassType type ) {
        ImmutableList.Builder<JClassType> builder = ImmutableList.builder();
        if ( type.getSubtypes().length > 0 ) {
            for ( JClassType subtype : type.getSubtypes() ) {
                if ( null == subtype.isInterface() && !subtype.isAbstract() && subtype.isPublic() ) {
                    builder.add( subtype );
                }
            }
        }
        return builder.build();
    }

    private CreatorUtils() {
    }
}
