package com.github.nmorel.gwtjackson.rebind;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.SourceWriter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findAnnotationOnAnyAccessor;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

/**
 * @author Nicolas Morel
 */
public final class PropertyInfo {

    public static interface AdditionalMethod {

        void write( SourceWriter source );
    }

    public static PropertyInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, FieldAccessors fieldAccessors,
                                        BeanJsonMapperInfo mapperInfo ) throws UnableToCompleteException {
        PropertyInfo result = new PropertyInfo();

        // we first check if the property is ignored
        JsonIgnore jsonIgnore = findAnnotationOnAnyAccessor( fieldAccessors, JsonIgnore.class );
        result.ignored = null != jsonIgnore && jsonIgnore.value();
        if ( result.ignored ) {
            return result;
        }

        // find the type of the property
        result.type = findType( fieldAccessors );

        // if type is ignored, we ignore the property
        if ( null != result.type.isClassOrInterface() ) {
            JsonIgnoreType jsonIgnoreType = findFirstEncounteredAnnotationsOnAllHierarchy( result.type
                .isClassOrInterface(), JsonIgnoreType.class );
            result.ignored = null != jsonIgnoreType && jsonIgnoreType.value();
            if ( result.ignored ) {
                return result;
            }
        }

        // determine the property name
        JsonProperty jsonProperty = findAnnotationOnAnyAccessor( fieldAccessors, JsonProperty.class );
        result.required = null != jsonProperty && jsonProperty.required();
        if ( null != jsonProperty && null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) ) {
            result.propertyName = jsonProperty.value();
        } else {
            result.propertyName = fieldAccessors.getFieldName();
        }

        // now that we have the property name, we check if it's not in the ignored properties
        result.ignored = mapperInfo.getBeanInfo().getIgnoredFields().contains( result.propertyName );
        if ( result.ignored ) {
            return result;
        }

        JsonManagedReference jsonManagedReference = findAnnotationOnAnyAccessor( fieldAccessors, JsonManagedReference.class );
        result.managedReference = null == jsonManagedReference ? null : jsonManagedReference.value();

        JsonBackReference jsonBackReference = findAnnotationOnAnyAccessor( fieldAccessors, JsonBackReference.class );
        result.backReference = null == jsonBackReference ? null : jsonBackReference.value();

        // if an accessor has jackson annotation, the property is considered auto detected.
        // TODO can we do a search on @JacksonAnnotation instead of enumerating all of them ?
        boolean hasAnyAnnotation = null != jsonProperty || null != jsonManagedReference || null != jsonBackReference;

        boolean getterAutoDetected = null != fieldAccessors.getGetter() && (hasAnyAnnotation || isGetterAutoDetected( fieldAccessors
            .getGetter(), mapperInfo.getBeanInfo() ));
        boolean setterAutoDetected = null != fieldAccessors.getSetter() && (hasAnyAnnotation || isSetterAutoDetected( fieldAccessors
            .getSetter(), mapperInfo.getBeanInfo() ));
        boolean fieldAutoDetected = null != fieldAccessors.getField() && (hasAnyAnnotation || isFieldAutoDetected( fieldAccessors
            .getField(), mapperInfo.getBeanInfo() ));

        if ( !getterAutoDetected && !setterAutoDetected && !fieldAutoDetected ) {
            // none of the field have been auto-detected, we ignore the field
            result.ignored = true;
            return result;
        }

        if ( null == result.backReference ) {
            determineGetter( fieldAccessors, getterAutoDetected, fieldAutoDetected, mapperInfo, result );
        }
        determineSetter( fieldAccessors, setterAutoDetected, fieldAutoDetected, mapperInfo, result );

        result.identityInfo = BeanIdentityInfo.process( logger, typeOracle, result.type, fieldAccessors );

        return result;
    }

    /**
     * Processes and construct a {@link PropertyInfo} for a constructor parameter.
     */
    public static PropertyInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, String propertyName,
                                        JParameter constructorParameter, BeanInfo beanInfo ) {
        PropertyInfo result = new PropertyInfo();
        result.type = constructorParameter.getType();
        result.required = constructorParameter.getAnnotation( JsonProperty.class ).required();
        result.propertyName = propertyName;
        // TODO find a better way. If we let null, the decoder won't be added. But the setterAccessor is never used for constructor fields.
        result.setterAccessor = "";
        return result;
    }

    private static JType findType( FieldAccessors fieldAccessors ) {
        if ( null != fieldAccessors.getGetter() ) {
            return fieldAccessors.getGetter().getReturnType();
        } else if ( null != fieldAccessors.getSetter() ) {
            return fieldAccessors.getSetter().getParameters()[0].getType();
        } else {
            return fieldAccessors.getField().getType();
        }
    }

    private static boolean isGetterAutoDetected( JMethod getter, BeanInfo info ) {
        JsonAutoDetect.Visibility visibility;
        if ( getter.getName().startsWith( "is" ) ) {
            visibility = info.getIsGetterVisibility();
        } else {
            visibility = info.getGetterVisibility();
        }
        return isAutoDetected( visibility, getter.isPrivate(), getter.isProtected(), getter.isPublic(), getter.isDefaultAccess() );
    }

    private static boolean isSetterAutoDetected( JMethod setter, BeanInfo info ) {
        return isAutoDetected( info.getSetterVisibility(), setter.isPrivate(), setter.isProtected(), setter.isPublic(), setter
            .isDefaultAccess() );
    }

    private static boolean isFieldAutoDetected( JField field, BeanInfo info ) {
        return isAutoDetected( info.getFieldVisibility(), field.isPrivate(), field.isProtected(), field.isPublic(), field
            .isDefaultAccess() );
    }

    private static boolean isAutoDetected( JsonAutoDetect.Visibility visibility, boolean isPrivate, boolean isProtected,
                                           boolean isPublic, boolean isDefaultAccess ) {
        switch ( visibility ) {
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
                                         final BeanJsonMapperInfo mapperInfo, final PropertyInfo result ) {
        if ( !getterAutoDetect && !fieldAutoDetect ) {
            // we can't get the value
            return;
        }

        // The mapper is in same package as bean so we can directly access all fields/getters but private ones. In case
        // the getter or field is in a different package because it is on a superclass and is not public, we use jsni.

        JPackage beanPackage = mapperInfo.getType().getPackage();

        // We first test if we can use the getter
        final JMethod getter = fieldAccessors.getGetter();
        boolean getterInSamePackage = getterAutoDetect && getter.getEnclosingType().getPackage().equals( beanPackage );

        if ( getterAutoDetect && ((getterInSamePackage && !getter.isPrivate()) || (!getterInSamePackage && getter.isPublic())) ) {
            result.getterAccessor = "bean." + getter.getName() + "()";
            return;
        }

        // Then the field

        final JField field = fieldAccessors.getField();
        boolean fieldInSamePackage = fieldAutoDetect && field.getEnclosingType().getPackage().equals( beanPackage );

        if ( fieldAutoDetect && ((fieldInSamePackage && !field.isPrivate()) || (fieldInSamePackage && field.isPublic())) ) {
            result.getterAccessor = "bean." + field.getName();
            return;
        }

        // field/getter has not been detected or is private or is in a different package. We use JSNI to access private getter/field.

        final String methodName = "get" + result.propertyName.substring( 0, 1 ).toUpperCase() + result.propertyName.substring( 1 );
        result.getterAccessor = mapperInfo.getSimpleSerializerClassName() + "." + methodName + "(bean)";

        result.additionalSerializationMethods.add( new AdditionalMethod() {
            @Override
            public void write( SourceWriter source ) {
                source.println( "private static native %s %s(%s bean) /*-{", result.type
                    .getParameterizedQualifiedSourceName(), methodName, mapperInfo.getType().getParameterizedQualifiedSourceName() );
                source.indent();
                if ( getterAutoDetect ) {
                    source.println( "return bean.@%s::%s()();", mapperInfo.getType().getQualifiedSourceName(), getter.getName() );
                } else {
                    source.println( "return bean.@%s::%s;", mapperInfo.getType().getQualifiedSourceName(), field.getName() );
                }
                source.outdent();
                source.println( "}-*/;" );
            }
        } );
    }

    private static void determineSetter( final FieldAccessors fieldAccessors, final boolean setterAutoDetect,
                                         final boolean fieldAutoDetect, final BeanJsonMapperInfo mapperInfo, final PropertyInfo result ) {
        if ( !setterAutoDetect && !fieldAutoDetect ) {
            // we can't set the value
        }

        // The mapper is in same package as bean so we can directly access all fields/setters but private ones. In case
        // the setter or field is in a different package because it is on a superclass and is not public, we use jsni.

        JPackage beanPackage = mapperInfo.getType().getPackage();

        // We first test if we can use the setter
        final JMethod setter = fieldAccessors.getSetter();
        boolean setterInSamePackage = setterAutoDetect && setter.getEnclosingType().getPackage().equals( beanPackage );

        if ( setterAutoDetect && ((setterInSamePackage && !setter.isPrivate()) || (!setterInSamePackage && setter.isPublic())) ) {
            result.setterAccessor = AbstractCreator.BEAN_INSTANCE_NAME + "." + fieldAccessors.getSetter().getName() + "(%s)";
            return;
        }

        // Then the field

        final JField field = fieldAccessors.getField();
        boolean fieldInSamePackage = fieldAutoDetect && field.getEnclosingType().getPackage().equals( beanPackage );

        if ( fieldAutoDetect && ((fieldInSamePackage && !field.isPrivate()) || (fieldInSamePackage && field.isPublic())) ) {
            result.setterAccessor = AbstractCreator.BEAN_INSTANCE_NAME + "." + fieldAccessors.getField().getName() + " = %s";
            return;
        }

        // field/setter has not been detected or is private or is in a different package. We use JSNI to access private setter/field.

        final String methodName = "set" + result.propertyName.substring( 0, 1 ).toUpperCase() + result.propertyName.substring( 1 );
        result.setterAccessor = mapperInfo
            .getSimpleDeserializerClassName() + "." + methodName + "(" + AbstractCreator.BEAN_INSTANCE_NAME + ", %s)";

        result.additionalDeserializationMethods.add( new AdditionalMethod() {
            @Override
            public void write( SourceWriter source ) {
                source.println( "private static native void %s(%s bean, %s value) /*-{", methodName, mapperInfo.getType()
                    .getParameterizedQualifiedSourceName(), result.type.getParameterizedQualifiedSourceName() );
                source.indent();
                if ( setterAutoDetect ) {
                    source.println( "bean.@%s::%s(%s)(value);", mapperInfo.getType().getQualifiedSourceName(), setter.getName(), result.type
                        .getJNISignature() );
                } else {
                    source.println( "bean.@%s::%s = value;", mapperInfo.getType().getQualifiedSourceName(), field.getName() );
                }
                source.outdent();
                source.println( "}-*/;" );
            }
        } );
    }

    private boolean ignored;

    private JType type;

    private boolean required;

    private String propertyName;

    private String managedReference;

    private String backReference;

    private String getterAccessor;

    private String setterAccessor;

    private List<AdditionalMethod> additionalDeserializationMethods = new ArrayList<AdditionalMethod>();

    private List<AdditionalMethod> additionalSerializationMethods = new ArrayList<AdditionalMethod>();

    private BeanIdentityInfo identityInfo;

    private PropertyInfo() {
    }

    public boolean isIgnored() {
        return ignored;
    }

    public JType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getManagedReference() {
        return managedReference;
    }

    public String getBackReference() {
        return backReference;
    }

    public String getGetterAccessor() {
        return getterAccessor;
    }

    public String getSetterAccessor() {
        return setterAccessor;
    }

    public List<AdditionalMethod> getAdditionalDeserializationMethods() {
        return additionalDeserializationMethods;
    }

    public List<AdditionalMethod> getAdditionalSerializationMethods() {
        return additionalSerializationMethods;
    }

    public void setRequired( boolean required ) {
        this.required = required;
    }

    public BeanIdentityInfo getIdentityInfo() {
        return identityInfo;
    }
}
