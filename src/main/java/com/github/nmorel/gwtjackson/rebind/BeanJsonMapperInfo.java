package com.github.nmorel.gwtjackson.rebind;

import java.util.Map;

/** @author Nicolas Morel */
public class BeanJsonMapperInfo
{
    private String qualifiedMapperClassName;
    private BeanInfo beanInfo;
    private Map<String, PropertyInfo> properties;

    public String getQualifiedMapperClassName()
    {
        return qualifiedMapperClassName;
    }

    public void setQualifiedMapperClassName( String qualifiedMapperClassName )
    {
        this.qualifiedMapperClassName = qualifiedMapperClassName;
    }

    public BeanInfo getBeanInfo()
    {
        return beanInfo;
    }

    public void setBeanInfo( BeanInfo beanInfo )
    {
        this.beanInfo = beanInfo;
    }

    public Map<String, PropertyInfo> getProperties()
    {
        return properties;
    }

    public void setProperties( Map<String, PropertyInfo> properties )
    {
        this.properties = properties;
    }
}
