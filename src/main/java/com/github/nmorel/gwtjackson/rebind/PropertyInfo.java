package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private boolean ignored;
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
    public void process( BeanInfo beanInfo )
    {
        // we first check if the property is ignored
        processIgnore( beanInfo );
        if ( ignored )
        {
            return;
        }

        type = findType();
        propertyName = field.getName();

        JsonProperty jsonProperty = isAnnotationPresentOnAnyAccessor( JsonProperty.class );
        if ( null == jsonProperty )
        {
            boolean getterAutoDetect = isGetterAutoDetected( beanInfo );
            boolean setterAutoDetect = isSetterAutoDetected( beanInfo );
            boolean fieldAutoDetect = isFieldAutoDetected( beanInfo );
            ignored = !getterAutoDetect && !setterAutoDetect && !fieldAutoDetect;
            if ( ignored )
            {
                return;
            }
        }
        else
        {
            if ( null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) )
            {
                propertyName = jsonProperty.value();
            }
        }

        getterAccessor = determineGetter();
        setterAccessor = determineSetter();
    }

    private String determineGetter()
    {
        if ( null != getter && !getter.isPrivate() )
        {
            return getter.getName() + "()";
        }
        if ( null != field && !field.isPrivate() )
        {
            return field.getName();
        }

        // field and getter are null or private

        if ( null != field )
        {
            // TODO use jsni to set the field
        }
        if ( null != getter )
        {
            // TODO use jsni to call the getter
        }

        // both null
        return null;
    }

    private String determineSetter()
    {
        if ( null != setter && !setter.isPrivate() )
        {
            return setter.getName() + "(%s)";
        }
        if ( null != field && !field.isPrivate() )
        {
            return field.getName() + " = %s";
        }

        // field and setter are null or private

        if ( null != field )
        {
            // TODO use jsni to set the field
        }
        if ( null != setter )
        {
            // TODO use jsni to call the setter
        }

        // both null
        return null;
    }

    private void processIgnore( BeanInfo beanInfo )
    {
        if ( beanInfo.getIgnoredFields().contains( fieldName ) )
        {
            ignored = true;
        }
        else if ( null != isAnnotationPresentOnAnyAccessor( JsonIgnore.class ) )
        {
            ignored = true;
        }
    }

    private <T extends Annotation> T isAnnotationPresentOnAnyAccessor( Class<T> annotation )
    {
        if ( null != getter && getter.isAnnotationPresent( annotation ) )
        {
            return getter.getAnnotation( annotation );
        }
        if ( null != field && field.isAnnotationPresent( annotation ) )
        {
            return field.getAnnotation( annotation );
        }
        if ( null != setter && setter.isAnnotationPresent( annotation ) )
        {
            return setter.getAnnotation( annotation );
        }
        return null;
    }

    private JType findType()
    {
        if ( null != getter )
        {
            return getter.getReturnType();
        }
        else if ( null != setter )
        {
            return setter.getParameters()[0].getType();
        }
        else
        {
            return field.getType();
        }
    }

    private boolean isGetterAutoDetected( BeanInfo info )
    {
        if ( null == getter )
        {
            return false;
        }
        JsonAutoDetect.Visibility visibility;
        if ( getter.getName().startsWith( "is" ) )
        {
            visibility = info.getIsGetterVisibility();
        }
        else
        {
            visibility = info.getGetterVisibility();
        }
        return isAutoDetected( visibility, getter.isPrivate(), getter.isProtected(), getter.isPublic(), getter.isDefaultAccess() );
    }

    private boolean isSetterAutoDetected( BeanInfo info )
    {
        return null != setter && isAutoDetected( info.getSetterVisibility(), setter.isPrivate(), setter.isProtected(), setter
            .isPublic(), setter.isDefaultAccess() );
    }

    private boolean isFieldAutoDetected( BeanInfo info )
    {
        return null != info && isAutoDetected( info.getFieldVisibility(), field.isPrivate(), field.isProtected(), field.isPublic(), field
            .isDefaultAccess() );
    }

    private boolean isAutoDetected( JsonAutoDetect.Visibility visibility, boolean isPrivate, boolean isProtected, boolean isPublic,
                                    boolean isDefaultAccess )
    {
        switch ( visibility )
        {
            case ANY:
                return true;
            case NONE:
                return false;
            case NON_PRIVATE:
                return !isPrivate;
            case PROTECTED_AND_PUBLIC:
                return isProtected || isPublic;
            case PUBLIC_ONLY:
                return isPublic;
            case DEFAULT:
                return isDefaultAccess || isProtected || isPublic;
            default:
                return false;
        }
    }

    public boolean isIgnored()
    {
        return ignored;
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
