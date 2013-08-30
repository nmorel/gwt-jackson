package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import  com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;

/** @author Nicolas Morel */
public final class PropertyInfo
{
    public static interface AdditionalMethod
    {
        void write( SourceWriter writer );
    }

    public static PropertyInfo process( FieldAccessors fieldAccessors, BeanInfo beanInfo )
    {
        PropertyInfo result = new PropertyInfo();

        // we first check if the property is ignored
        boolean ignored = processIgnore( fieldAccessors, beanInfo );
        if ( ignored )
        {
            result.ignored = true;
            return result;
        }

        result.type = findType( fieldAccessors );
        result.propertyName = fieldAccessors.getFieldName();

        JsonProperty jsonProperty = isAnnotationPresentOnAnyAccessor( fieldAccessors, JsonProperty.class );

        boolean getterAutoDetected = null != fieldAccessors.getGetter() && (null != jsonProperty || isGetterAutoDetected( fieldAccessors
            .getGetter(), beanInfo ));
        boolean setterAutoDetected = null != fieldAccessors.getSetter() && (null != jsonProperty || isSetterAutoDetected( fieldAccessors
            .getSetter(), beanInfo ));
        boolean fieldAutoDetected = null != fieldAccessors.getField() && (null != jsonProperty || isFieldAutoDetected( fieldAccessors
            .getField(), beanInfo ));

        if ( !getterAutoDetected && !setterAutoDetected && !fieldAutoDetected )
        {
            // none of the field have been auto-detected, we ignore the field
            result.ignored = true;
            return result;
        }

        if ( null != jsonProperty && null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) )
        {
            result.propertyName = jsonProperty.value();
        }

        determineGetter( fieldAccessors, getterAutoDetected, fieldAutoDetected, beanInfo, result );
        determineSetter( fieldAccessors, setterAutoDetected, fieldAutoDetected, beanInfo, result );

        return result;
    }

    private static boolean processIgnore( FieldAccessors fieldAccessors, BeanInfo beanInfo )
    {
        if ( beanInfo.getIgnoredFields().contains( fieldAccessors.getFieldName() ) )
        {
            return true;
        }
        else if ( null != isAnnotationPresentOnAnyAccessor( fieldAccessors, JsonIgnore.class ) )
        {
            return true;
        }
        return false;
    }

    private static <T extends Annotation> T isAnnotationPresentOnAnyAccessor( FieldAccessors fieldAccessors, Class<T> annotation )
    {
        if ( null != fieldAccessors.getGetter() && fieldAccessors.getGetter().isAnnotationPresent( annotation ) )
        {
            return fieldAccessors.getGetter().getAnnotation( annotation );
        }
        if ( null != fieldAccessors.getField() && fieldAccessors.getField().isAnnotationPresent( annotation ) )
        {
            return fieldAccessors.getField().getAnnotation( annotation );
        }
        if ( null != fieldAccessors.getSetter() && fieldAccessors.getSetter().isAnnotationPresent( annotation ) )
        {
            return fieldAccessors.getSetter().getAnnotation( annotation );
        }
        return null;
    }

    private static JType findType( FieldAccessors fieldAccessors )
    {
        if ( null != fieldAccessors.getGetter() )
        {
            return fieldAccessors.getGetter().getReturnType();
        }
        else if ( null != fieldAccessors.getSetter() )
        {
            return fieldAccessors.getSetter().getParameters()[0].getType();
        }
        else
        {
            return fieldAccessors.getField().getType();
        }
    }

    private static boolean isGetterAutoDetected( JMethod getter, BeanInfo info )
    {
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

    private static boolean isSetterAutoDetected( JMethod setter, BeanInfo info )
    {
        return isAutoDetected( info.getSetterVisibility(), setter.isPrivate(), setter.isProtected(), setter.isPublic(), setter
            .isDefaultAccess() );
    }

    private static boolean isFieldAutoDetected( JField field, BeanInfo info )
    {
        return isAutoDetected( info.getFieldVisibility(), field.isPrivate(), field.isProtected(), field.isPublic(), field
            .isDefaultAccess() );
    }

    private static boolean isAutoDetected( JsonAutoDetect.Visibility visibility, boolean isPrivate, boolean isProtected,
                                           boolean isPublic, boolean isDefaultAccess )
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

    private static void determineGetter( FieldAccessors fieldAccessors, boolean getterAutoDetect, boolean fieldAutoDetect,
                                         BeanInfo beanInfo, PropertyInfo result )
    {
        if ( !getterAutoDetect && !fieldAutoDetect )
        {
            // we can't get the value
            return;
        }

        if ( getterAutoDetect && !fieldAccessors.getGetter().isPrivate() )
        {
            result.getterAccessor = fieldAccessors.getGetter().getName() + "()";
        }
        else if ( fieldAutoDetect && !fieldAccessors.getField().isPrivate() )
        {
            result.getterAccessor = fieldAccessors.getField().getName();
        }

        // field/getter has not been detected or is private

        else if ( getterAutoDetect )
        {
            // TODO use jsni to call the getter
        }
        else
        {
            // TODO use jsni to get the field
        }

    }

    private static void determineSetter( FieldAccessors fieldAccessors, boolean setterAutoDetect, boolean fieldAutoDetect,
                                         BeanInfo beanInfo, PropertyInfo result )
    {
        if ( !setterAutoDetect && !fieldAutoDetect )
        {
            // we can't set the value
        }

        if ( setterAutoDetect && !fieldAccessors.getSetter().isPrivate() )
        {
            result.setterAccessor = fieldAccessors.getSetter().getName() + "(%s)";
        }
        else if ( fieldAutoDetect && !fieldAccessors.getField().isPrivate() )
        {
            result.setterAccessor = fieldAccessors.getField().getName() + " = %s";
        }

        // field/setter has not been detected or is private

        else if ( setterAutoDetect )
        {
            // TODO use jsni to call the setter
        }
        else
        {
            // TODO use jsni to set the field
        }
    }

    private boolean ignored;
    private JType type;
    private String propertyName;
    private String getterAccessor;
    private String setterAccessor;
    private List<AdditionalMethod> additionalMethods = new ArrayList<AdditionalMethod>();

    private PropertyInfo()
    {
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

    private void addAdditionalMethod( AdditionalMethod method )
    {
        additionalMethods.add( method );
    }

    public List<AdditionalMethod> getAdditionalMethods()
    {
        return additionalMethods;
    }
}
