package com.github.nmorel.gwtjackson.rebind;

import java.util.Map;

/** @author Nicolas Morel */
public class BeanJsonMapperInfo
{
    private String qualifiedMapperClassName;
    private BeanInfo beanInfo;
    private Map<String, PropertyInfo> properties;

    public BeanJsonMapperInfo( String qualifiedMapperClassName, BeanInfo beanInfo, Map<String, PropertyInfo> properties )
    {
        this.qualifiedMapperClassName = qualifiedMapperClassName;
        this.beanInfo = beanInfo;
        this.properties = properties;
    }

    public String getQualifiedMapperClassName()
    {
        return qualifiedMapperClassName;
    }

    public BeanInfo getBeanInfo()
    {
        return beanInfo;
    }

    public Map<String, PropertyInfo> getProperties()
    {
        return properties;
    }
}
