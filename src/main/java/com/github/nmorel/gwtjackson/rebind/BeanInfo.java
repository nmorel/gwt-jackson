package com.github.nmorel.gwtjackson.rebind;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/** @author Nicolas Morel */
public class BeanInfo
{
    private boolean ignoreAllProperties;
    private Set<String> ignoredFields = new HashSet<String>();
    private JsonAutoDetect.Visibility fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility isGetterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;
    private JsonAutoDetect.Visibility creatorVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

    public boolean isIgnoreAllProperties()
    {
        return ignoreAllProperties;
    }

    public void setIgnoreAllProperties( boolean ignoreAllProperties )
    {
        this.ignoreAllProperties = ignoreAllProperties;
    }

    public Set<String> getIgnoredFields()
    {
        return ignoredFields;
    }

    public void addIgnoredField( String ignoredField )
    {
        this.ignoredFields.add( ignoredField );
    }

    public JsonAutoDetect.Visibility getFieldVisibility()
    {
        return fieldVisibility;
    }

    public void setFieldVisibility( JsonAutoDetect.Visibility fieldVisibility )
    {
        this.fieldVisibility = fieldVisibility;
    }

    public JsonAutoDetect.Visibility getGetterVisibility()
    {
        return getterVisibility;
    }

    public void setGetterVisibility( JsonAutoDetect.Visibility getterVisibility )
    {
        this.getterVisibility = getterVisibility;
    }

    public JsonAutoDetect.Visibility getIsGetterVisibility()
    {
        return isGetterVisibility;
    }

    public void setIsGetterVisibility( JsonAutoDetect.Visibility isGetterVisibility )
    {
        this.isGetterVisibility = isGetterVisibility;
    }

    public JsonAutoDetect.Visibility getSetterVisibility()
    {
        return setterVisibility;
    }

    public void setSetterVisibility( JsonAutoDetect.Visibility setterVisibility )
    {
        this.setterVisibility = setterVisibility;
    }

    public JsonAutoDetect.Visibility getCreatorVisibility()
    {
        return creatorVisibility;
    }

    public void setCreatorVisibility( JsonAutoDetect.Visibility creatorVisibility )
    {
        this.creatorVisibility = creatorVisibility;
    }
}
