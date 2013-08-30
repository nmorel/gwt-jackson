package com.github.nmorel.gwtjackson.rebind;

import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;

/**
 * Used to aggregate field, getter method and setter method of the same field
 *
 * @author Nicolas Morel
 */
public class FieldAccessors
{
    private String fieldName;
    private JField field;
    private JMethod getter;
    private JMethod setter;

    public FieldAccessors( String fieldName )
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
}
