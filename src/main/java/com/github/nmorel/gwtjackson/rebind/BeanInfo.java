package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.google.gwt.core.ext.typeinfo.JClassType;

/** @author Nicolas Morel */
public final class BeanInfo
{
    public static BeanInfo process( JClassType beanType )
    {
        BeanInfo result = new BeanInfo();
        result.type = beanType;

        JsonIgnoreType jsonIgnoreType = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonIgnoreType.class );
        result.ignoreAllProperties = null != jsonIgnoreType && jsonIgnoreType.value();

        JsonAutoDetect jsonAutoDetect = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonAutoDetect.class );
        if ( null != jsonAutoDetect )
        {
            result.creatorVisibility = jsonAutoDetect.creatorVisibility();
            result.fieldVisibility = jsonAutoDetect.fieldVisibility();
            result.getterVisibility = jsonAutoDetect.getterVisibility();
            result.isGetterVisibility = jsonAutoDetect.isGetterVisibility();
            result.setterVisibility = jsonAutoDetect.setterVisibility();
        }

        JsonIgnoreProperties jsonIgnoreProperties = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonIgnoreProperties.class );
        if ( null != jsonIgnoreProperties )
        {
            for ( String ignoreProperty : jsonIgnoreProperties.value() )
            {
                result.addIgnoredField( ignoreProperty );
            }
            result.ignoreUnknown = jsonIgnoreProperties.ignoreUnknown();
        }
        return result;
    }

    private static <T extends Annotation> T findFirstEncounteredAnnotationsOnAllHierarchy( JClassType type, Class<T> annotation )
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

    private JClassType type;
    private boolean ignoreAllProperties;
    private Set<String> ignoredFields = new HashSet<String>();
    private JsonAutoDetect.Visibility fieldVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility getterVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility isGetterVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility creatorVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private boolean ignoreUnknown;

    private BeanInfo()
    {

    }

    public JClassType getType()
    {
        return type;
    }

    public boolean isIgnoreAllProperties()
    {
        return ignoreAllProperties;
    }

    public Set<String> getIgnoredFields()
    {
        return ignoredFields;
    }

    private void addIgnoredField( String ignoredField )
    {
        this.ignoredFields.add( ignoredField );
    }

    public JsonAutoDetect.Visibility getFieldVisibility()
    {
        return fieldVisibility;
    }

    public JsonAutoDetect.Visibility getGetterVisibility()
    {
        return getterVisibility;
    }

    public JsonAutoDetect.Visibility getIsGetterVisibility()
    {
        return isGetterVisibility;
    }

    public JsonAutoDetect.Visibility getSetterVisibility()
    {
        return setterVisibility;
    }

    public JsonAutoDetect.Visibility getCreatorVisibility()
    {
        return creatorVisibility;
    }

    public boolean isIgnoreUnknown()
    {
        return ignoreUnknown;
    }
}
