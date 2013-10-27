package com.github.nmorel.gwtjackson.rebind;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Function;

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
    public static <T extends Annotation> T findFirstEncounteredAnnotationsOnAllHierarchy( JClassType type, Class<T> annotation ) {
        JClassType currentType = type;
        while ( null != currentType && !currentType.getQualifiedSourceName().equals( "java.lang.Object" ) ) {
            if ( currentType.isAnnotationPresent( annotation ) ) {
                return currentType.getAnnotation( annotation );
            }
            for ( JClassType interf : currentType.getImplementedInterfaces() ) {
                T annot = findFirstEncounteredAnnotationsOnAllHierarchy( interf, annotation );
                if ( null != annot ) {
                    return annot;
                }
            }
            currentType = currentType.getSuperclass();
        }
        return null;
    }

    public static <T extends Annotation> T findAnnotationOnAnyAccessor( FieldAccessors fieldAccessors, Class<T> annotation ) {
        // TODO with this current setup, an annotation present on a getter method in superclass will be returned instead of the same
        // annotation present on field in the child class. Test the behaviour in jackson.

        if ( null != fieldAccessors.getGetter() && fieldAccessors.getGetter().isAnnotationPresent( annotation ) ) {
            return fieldAccessors.getGetter().getAnnotation( annotation );
        }
        if ( null != fieldAccessors.getSetter() && fieldAccessors.getSetter().isAnnotationPresent( annotation ) ) {
            return fieldAccessors.getSetter().getAnnotation( annotation );
        }
        if ( null != fieldAccessors.getField() && fieldAccessors.getField().isAnnotationPresent( annotation ) ) {
            return fieldAccessors.getField().getAnnotation( annotation );
        }

        for ( JMethod method : fieldAccessors.getGetters() ) {
            if ( method.isAnnotationPresent( annotation ) ) {
                return method.getAnnotation( annotation );
            }
        }

        for ( JMethod method : fieldAccessors.getSetters() ) {
            if ( method.isAnnotationPresent( annotation ) ) {
                return method.getAnnotation( annotation );
            }
        }

        return null;
    }

    /**
     * Extract the bean type from the type given in parameter. For {@link java.util.Collection}, it gives the bounded type. For {@link
     * java.util.Map}, it gives the second bounded type. Otherwise, it gives the type given in parameter.
     *
     * @param type type to extract the bean type
     *
     * @return the extracted type
     */
    public static JClassType extractBeanType( JacksonTypeOracle typeOracle, JType type ) {
        JClassType classType = type.isClassOrInterface();
        if ( null == classType ) {
            return null;
        } else if ( typeOracle.isIterable( classType ) ) {
            return extractBeanType( typeOracle, classType.isParameterized().getTypeArgs()[0] );
        } else if ( typeOracle.isMap( classType ) ) {
            return extractBeanType( typeOracle, classType.isParameterized().getTypeArgs()[1] );
        } else {
            return classType;
        }
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

    private CreatorUtils() {
    }
}
