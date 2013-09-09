package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;

/** @author Nicolas Morel */
public final class CreatorUtils
{
    /**
     * Browse all the hierarchy of the type and return the first corresponding annotation it found
     *
     * @param type type
     * @param annotation annotation to find
     * @param <T> type of the annotation
     * @return the annotation if found, null otherwise
     */
    public static <T extends Annotation> T findFirstEncounteredAnnotationsOnAllHierarchy( JClassType type, Class<T> annotation )
    {
        JClassType currentType = type;
        while ( null != currentType && !currentType.getQualifiedSourceName().equals( "java.lang.Object" ) )
        {
            if ( currentType.isAnnotationPresent( annotation ) )
            {
                return currentType.getAnnotation( annotation );
            }
            for ( JClassType interf : currentType.getImplementedInterfaces() )
            {
                T annot = findFirstEncounteredAnnotationsOnAllHierarchy( interf, annotation );
                if ( null != annot )
                {
                    return annot;
                }
            }
            currentType = currentType.getSuperclass();
        }
        return null;
    }

    private CreatorUtils()
    {
    }
}
