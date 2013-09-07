package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.SourceWriter;

/** @author Nicolas Morel */
public final class PropertyInfo
{
    public static interface AdditionalMethod
    {
        void write( SourceWriter source );
    }

    public static PropertyInfo process( FieldAccessors fieldAccessors, BeanInfo beanInfo )
    {
        PropertyInfo result = new PropertyInfo();

        // we first check if the property is ignored
        JsonIgnore jsonIgnore = findAnnotationOnAnyAccessor( fieldAccessors, JsonIgnore.class );
        result.ignored = null != jsonIgnore && jsonIgnore.value();
        if ( result.ignored )
        {
            return result;
        }

        // find the type of the property
        result.type = findType( fieldAccessors );

        // determine the property name
        JsonProperty jsonProperty = findAnnotationOnAnyAccessor( fieldAccessors, JsonProperty.class );
        if ( null != jsonProperty && null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) )
        {
            result.propertyName = jsonProperty.value();
        }
        else
        {
            result.propertyName = fieldAccessors.getFieldName();
        }

        // now that we have the property name, we check if it's not in the ignored properties
        result.ignored = beanInfo.getIgnoredFields().contains( result.propertyName );
        if ( result.ignored )
        {
            return result;
        }

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

        determineGetter( fieldAccessors, getterAutoDetected, fieldAutoDetected, beanInfo, result );
        determineSetter( fieldAccessors, setterAutoDetected, fieldAutoDetected, beanInfo, result );

        return result;
    }

    /** Processes and construct a {@link PropertyInfo} for a constructor parameter. */
    public static PropertyInfo process( String propertyName, JParameter constructorParameter, BeanInfo beanInfo )
    {
        PropertyInfo result = new PropertyInfo();
        result.type = constructorParameter.getType();
        result.propertyName = propertyName;
        // TODO find a better way. If we let null, the decoder won't be added. But the setterAccessor is never used for constructor fields.
        result.setterAccessor = "";
        return result;
    }

    private static <T extends Annotation> T findAnnotationOnAnyAccessor( FieldAccessors fieldAccessors, Class<T> annotation )
    {
        // TODO with this current setup, an annotation present on a getter method in superclass will be returned instead of the same
        // annotation present on field in the child class. Test the behaviour in jackson.

        if ( null != fieldAccessors.getGetter() && fieldAccessors.getGetter().isAnnotationPresent( annotation ) )
        {
            return fieldAccessors.getGetter().getAnnotation( annotation );
        }
        if ( null != fieldAccessors.getSetter() && fieldAccessors.getSetter().isAnnotationPresent( annotation ) )
        {
            return fieldAccessors.getSetter().getAnnotation( annotation );
        }
        if ( null != fieldAccessors.getField() && fieldAccessors.getField().isAnnotationPresent( annotation ) )
        {
            return fieldAccessors.getField().getAnnotation( annotation );
        }

        for ( JMethod method : fieldAccessors.getGetters() )
        {
            if ( method.isAnnotationPresent( annotation ) )
            {
                return method.getAnnotation( annotation );
            }
        }

        for ( JMethod method : fieldAccessors.getSetters() )
        {
            if ( method.isAnnotationPresent( annotation ) )
            {
                return method.getAnnotation( annotation );
            }
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
            case DEFAULT:
                return isPublic;
            default:
                return false;
        }
    }

    private static void determineGetter( final FieldAccessors fieldAccessors, final boolean getterAutoDetect, boolean fieldAutoDetect,
                                         final BeanInfo beanInfo, final PropertyInfo result )
    {
        if ( !getterAutoDetect && !fieldAutoDetect )
        {
            // we can't get the value
            return;
        }

        // The mapper is in same package as bean so we can directly access all fields/getters but private ones. In case
        // the getter or field is in a different package because it is on a superclass and is not public, we use jsni.

        JPackage beanPackage = beanInfo.getType().getPackage();

        // We first test if we can use the getter
        final JMethod getter = fieldAccessors.getGetter();
        boolean getterInSamePackage = getterAutoDetect && getter.getEnclosingType().getPackage().equals( beanPackage );

        if ( getterAutoDetect && ((getterInSamePackage && !getter.isPrivate()) || (!getterInSamePackage && getter.isPublic())) )
        {
            result.getterAccessor = "bean." + getter.getName() + "()";
            return;
        }

        // Then the field

        final JField field = fieldAccessors.getField();
        boolean fieldInSamePackage = fieldAutoDetect && field.getEnclosingType().getPackage().equals( beanPackage );

        if ( fieldAutoDetect && ((fieldInSamePackage && !field.isPrivate()) || (fieldInSamePackage && field.isPublic())) )
        {
            result.getterAccessor = "bean." + field.getName();
            return;
        }

        // field/getter has not been detected or is private or is in a different package. We use JSNI to access private getter/field.

        final String methodName = "get" + result.propertyName.substring( 0, 1 ).toUpperCase() + result.propertyName.substring( 1 );
        result.getterAccessor = beanInfo.getMapperClassSimpleName() + "." + methodName + "(bean)";

        result.addAdditionalMethod( new AdditionalMethod()
        {
            @Override
            public void write( SourceWriter source )
            {
                source.println( "private static native %s %s(%s bean) /*-{", result.type
                    .getParameterizedQualifiedSourceName(), methodName, beanInfo.getType().getParameterizedQualifiedSourceName() );
                source.indent();
                if ( getterAutoDetect )
                {
                    source.println( "return bean.@%s::%s()();", beanInfo.getType().getQualifiedSourceName(), getter.getName() );
                }
                else
                {
                    source.println( "return bean.@%s::%s;", beanInfo.getType().getQualifiedSourceName(), field.getName() );
                }
                source.outdent();
                source.println( "}-*/;" );
            }
        } );
    }

    private static void determineSetter( final FieldAccessors fieldAccessors, final boolean setterAutoDetect,
                                         final boolean fieldAutoDetect, final BeanInfo beanInfo, final PropertyInfo result )
    {
        if ( !setterAutoDetect && !fieldAutoDetect )
        {
            // we can't set the value
        }

        // The mapper is in same package as bean so we can directly access all fields/setters but private ones. In case
        // the setter or field is in a different package because it is on a superclass and is not public, we use jsni.

        JPackage beanPackage = beanInfo.getType().getPackage();

        // We first test if we can use the setter
        final JMethod setter = fieldAccessors.getSetter();
        boolean setterInSamePackage = setterAutoDetect && setter.getEnclosingType().getPackage().equals( beanPackage );

        if ( setterAutoDetect && ((setterInSamePackage && !setter.isPrivate()) || (!setterInSamePackage && setter.isPublic())) )
        {
            result.setterAccessor = AbstractJsonMapperCreator.BEAN_INSTANCE_NAME + "." + fieldAccessors.getSetter().getName() + "(%s)";
            return;
        }

        // Then the field

        final JField field = fieldAccessors.getField();
        boolean fieldInSamePackage = fieldAutoDetect && field.getEnclosingType().getPackage().equals( beanPackage );

        if ( fieldAutoDetect && ((fieldInSamePackage && !field.isPrivate()) || (fieldInSamePackage && field.isPublic())) )
        {
            result.setterAccessor = AbstractJsonMapperCreator.BEAN_INSTANCE_NAME + "." + fieldAccessors.getField().getName() + " = %s";
            return;
        }

        // field/setter has not been detected or is private or is in a different package. We use JSNI to access private setter/field.

        final String methodName = "set" + result.propertyName.substring( 0, 1 ).toUpperCase() + result.propertyName.substring( 1 );
        result.setterAccessor = beanInfo
            .getMapperClassSimpleName() + "." + methodName + "(" + AbstractJsonMapperCreator.BEAN_INSTANCE_NAME + ", %s)";

        result.addAdditionalMethod( new AdditionalMethod()
        {
            @Override
            public void write( SourceWriter source )
            {
                source.println( "private static native void %s(%s bean, %s value) /*-{", methodName, beanInfo.getType()
                    .getParameterizedQualifiedSourceName(), result.type.getParameterizedQualifiedSourceName() );
                source.indent();
                if ( setterAutoDetect )
                {
                    source.println( "bean.@%s::%s(%s)(value);", beanInfo.getType().getQualifiedSourceName(), setter.getName(), result.type
                        .getJNISignature() );
                }
                else
                {
                    source.println( "bean.@%s::%s = value;", beanInfo.getType().getQualifiedSourceName(), field.getName() );
                }
                source.outdent();
                source.println( "}-*/;" );
            }
        } );
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
