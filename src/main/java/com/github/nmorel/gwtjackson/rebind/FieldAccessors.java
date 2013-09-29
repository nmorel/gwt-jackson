package com.github.nmorel.gwtjackson.rebind;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;

/**
 * Used to aggregate field, getter method and setter method of the same field
 *
 * @author Nicolas Morel
 */
public class FieldAccessors {

    // name fo the field
    private String fieldName;

    // field
    private JField field;

    // getter method that will be called
    private JMethod getter;

    // additionnal getters found on superclass or interfaces that may contains annotations
    private List<JMethod> getters = new ArrayList<JMethod>();

    // setter method that will be called
    private JMethod setter;

    // additionnal setters found on superclass or interfaces that may contains annotations
    private List<JMethod> setters = new ArrayList<JMethod>();

    public FieldAccessors( String fieldName ) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName( String fieldName ) {
        this.fieldName = fieldName;
    }

    public JField getField() {
        return field;
    }

    public void setField( JField field ) {
        this.field = field;
    }

    public JMethod getGetter() {
        return getter;
    }

    public void addGetter( JMethod getter ) {
        if ( null == this.getter && !getter.isAbstract() ) {
            this.getter = getter;
        } else {
            this.getters.add( getter );
        }
    }

    public JMethod getSetter() {
        return setter;
    }

    public void addSetter( JMethod setter ) {
        if ( null == this.setter && !setter.isAbstract() ) {
            this.setter = setter;
        } else {
            this.setters.add( setter );
        }
    }

    public List<JMethod> getGetters() {
        return getters;
    }

    public List<JMethod> getSetters() {
        return setters;
    }
}
