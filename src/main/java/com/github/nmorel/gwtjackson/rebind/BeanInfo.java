package com.github.nmorel.gwtjackson.rebind;

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
        if ( beanType.isAnnotationPresent( JsonIgnoreType.class ) )
        {
            result.ignoreAllProperties = true;
        }
        if ( beanType.isAnnotationPresent( JsonAutoDetect.class ) )
        {
            JsonAutoDetect autoDetect = beanType.getAnnotation( JsonAutoDetect.class );
            result.creatorVisibility = autoDetect.creatorVisibility();
            result.fieldVisibility = autoDetect.fieldVisibility();
            result.getterVisibility = autoDetect.getterVisibility();
            result.isGetterVisibility = autoDetect.isGetterVisibility();
            result.setterVisibility = autoDetect.setterVisibility();
        }
        if ( beanType.isAnnotationPresent( JsonIgnoreProperties.class ) )
        {
            JsonIgnoreProperties ignoreProperties = beanType.getAnnotation( JsonIgnoreProperties.class );
            for ( String ignoreProperty : ignoreProperties.value() )
            {
                result.addIgnoredField( ignoreProperty );
            }
        }
        return result;
    }

    private JClassType type;
    private boolean ignoreAllProperties;
    private Set<String> ignoredFields = new HashSet<String>();
    private JsonAutoDetect.Visibility fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility isGetterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility creatorVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

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
}
