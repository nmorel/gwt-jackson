package com.github.nmorel.gwtjackson.rebind;

import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;

/**
 * Used to aggregate field, getter method and setter method of the same property
 *
 * @author Nicolas Morel
 */
public class PropertyInfo
{
    private String fieldName;
    private JField field;
    private JMethod getter;
    private JMethod setter;
    private JType type;
    private String propertyName;
    private String getterAccessor;
    private String setterAccessor;

    public PropertyInfo( String fieldName )
    {
        this.fieldName = fieldName;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName( String fieldName )
    {
        this.fieldName = fieldName;
    }

    public JField getField()
    {
        return field;
    }

    public void setField( JField field )
    {
        this.field = field;
    }

    public JMethod getGetter()
    {
        return getter;
    }

    public void setGetter( JMethod getter )
    {
        this.getter = getter;
    }

    public JMethod getSetter()
    {
        return setter;
    }

    public void setSetter( JMethod setter )
    {
        this.setter = setter;
    }

    /** Process annotations and determine property name, accessors methods, type... */
    public void process()
    {
        type = field.getType();
        propertyName = field.getName();
        getterAccessor = getter.getName() + "()";
        setterAccessor = setter.getName() + "(%s)";
    }

    public JType getType()
    {
        return type;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public String getGetterAccessor()
    {
        return getterAccessor;
    }

    public String getSetterAccessor()
    {
        return setterAccessor;
    }
}
