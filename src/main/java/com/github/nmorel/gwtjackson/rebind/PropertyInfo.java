package com.github.nmorel.gwtjackson.rebind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
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

        // find the type of the property
        result.type = findType( fieldAccessors );

        // determine the property name
        JsonProperty jsonProperty = findAnnotationOnAnyAccessor( fieldAccessors, JsonProperty.class );
        result.required = null != jsonProperty && jsonProperty.required();
        if ( null != jsonProperty && null != jsonProperty.value() && !JsonProperty.USE_DEFAULT_NAME.equals( jsonProperty.value() ) ) {
            result.propertyName = jsonProperty.value();
        } else {
            result.propertyName = fieldAccessors.getFieldName();
        }

        result.ignored = isPropertyIgnored( fieldAccessors, mapperInfo, result.type, result.propertyName );
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
            result.visible = false;
            return result;
        }

        if ( null == result.backReference ) {
            determineGetter( fieldAccessors, getterAutoDetected, fieldAutoDetected, result );

            JsonRawValue jsonRawValue = findAnnotationOnAnyAccessor( fieldAccessors, JsonRawValue.class );
            result.rawValue = null != jsonRawValue && jsonRawValue.value();
        }
        determineSetter( fieldAccessors, setterAutoDetected, fieldAutoDetected, result );

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

    private static boolean isPropertyIgnored( FieldAccessors fieldAccessors, BeanJsonMapperInfo mapperInfo, JType type,
                                              String propertyName ) {
        // we first check if the property is ignored
        JsonIgnore jsonIgnore = findAnnotationOnAnyAccessor( fieldAccessors, JsonIgnore.class );
        if ( null != jsonIgnore && jsonIgnore.value() ) {
            return true;
        }

        // if type is ignored, we ignore the property
        if ( null != type.isClassOrInterface() ) {
            JsonIgnoreType jsonIgnoreType = findFirstEncounteredAnnotationsOnAllHierarchy( type
                .isClassOrInterface(), JsonIgnoreType.class );
            if ( null != jsonIgnoreType && jsonIgnoreType.value() ) {
                return true;
            }
        }

        // we check if it's not in the ignored properties
        return mapperInfo.getBeanInfo().getIgnoredFields().contains( propertyName );

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
                                         final PropertyInfo result ) {
        if ( getterAutoDetect || fieldAutoDetect ) {
            result.getterAccessor = Optional.of( new FieldReadAccessor( result.propertyName, fieldAutoDetect ? fieldAccessors
                .getField() : null, getterAutoDetect ? fieldAccessors.getGetter() : null ) );
        }
    }

    private static void determineSetter( final FieldAccessors fieldAccessors, final boolean setterAutoDetect,
                                         final boolean fieldAutoDetect, final PropertyInfo result ) {
        if ( setterAutoDetect || fieldAutoDetect ) {
            result.setterAccessor = Optional.of( new FieldWriteAccessor( result.propertyName, fieldAutoDetect ? fieldAccessors
                .getField() : null, setterAutoDetect ? fieldAccessors.getSetter() : null ) );
        }
    }

    private boolean ignored;

    private boolean visible = true;

    private JType type;

    private boolean required;

    private String propertyName;

    private boolean rawValue;

    private String managedReference;

    private String backReference;

    private Optional<FieldReadAccessor> getterAccessor = Optional.absent();

    private Optional<FieldWriteAccessor> setterAccessor = Optional.absent();

    private BeanIdentityInfo identityInfo;

    private PropertyInfo() {
    }

    public boolean isIgnored() {
        return ignored;
    }

    public boolean isVisible() {
        return visible;
    }

    public JType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired( boolean required ) {
        this.required = required;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isRawValue() {
        return rawValue;
    }

    public String getManagedReference() {
        return managedReference;
    }

    public String getBackReference() {
        return backReference;
    }

    public Optional<FieldReadAccessor> getGetterAccessor() {
        return getterAccessor;
    }

    public Optional<FieldWriteAccessor> getSetterAccessor() {
        return setterAccessor;
    }

    public BeanIdentityInfo getIdentityInfo() {
        return identityInfo;
    }
}
