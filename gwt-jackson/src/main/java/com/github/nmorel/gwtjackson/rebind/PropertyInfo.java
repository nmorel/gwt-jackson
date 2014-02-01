/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.github.nmorel.gwtjackson.rebind.property.PropertyAccessors;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.user.rebind.SourceWriter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

/**
 * @author Nicolas Morel
 */
public final class PropertyInfo {

    private static final List<Class<? extends Annotation>> AUTO_DISCOVERY_ANNOTATIONS = Arrays
            .asList( JsonProperty.class, JsonManagedReference.class, JsonBackReference.class );

    public static interface AdditionalMethod {

        void write( SourceWriter source );
    }

    public static PropertyInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, RebindConfiguration configuration,
                                        PropertyAccessors propertyAccessors, BeanJsonMapperInfo mapperInfo ) throws
            UnableToCompleteException {
        PropertyInfo result = new PropertyInfo();

        boolean getterAutoDetected = isGetterAutoDetected( propertyAccessors, mapperInfo.getBeanInfo() );
        boolean setterAutoDetected = isSetterAutoDetected( propertyAccessors, mapperInfo.getBeanInfo() );
        boolean fieldAutoDetected = isFieldAutoDetected( propertyAccessors, mapperInfo.getBeanInfo() );

        if ( !getterAutoDetected && !setterAutoDetected && !fieldAutoDetected && !propertyAccessors.getParameter().isPresent() ) {
            // none of the field have been auto-detected, we ignore the field
            result.visible = false;
            return result;
        }

        // find the type of the property
        result.type = findType( logger, propertyAccessors );
        result.propertyName = propertyAccessors.getPropertyName();

        // determine the property name
        JsonProperty jsonProperty = propertyAccessors.getAnnotation( JsonProperty.class );
        result.required = null != jsonProperty && jsonProperty.required();

        result.ignored = isPropertyIgnored( configuration, propertyAccessors, mapperInfo, result.type, result.propertyName );
        if ( result.ignored ) {
            return result;
        }

        JsonManagedReference jsonManagedReference = propertyAccessors.getAnnotation( JsonManagedReference.class, true );
        result.managedReference = Optional.fromNullable( null == jsonManagedReference ? null : jsonManagedReference.value() );

        JsonBackReference jsonBackReference = propertyAccessors.getAnnotation( JsonBackReference.class, true );
        result.backReference = Optional.fromNullable( null == jsonBackReference ? null : jsonBackReference.value() );

        if ( !result.backReference.isPresent() ) {
            determineGetter( propertyAccessors, getterAutoDetected, fieldAutoDetected, result );

            JsonRawValue jsonRawValue = propertyAccessors.getAnnotation( JsonRawValue.class );
            result.rawValue = null != jsonRawValue && jsonRawValue.value();
        }
        determineSetter( propertyAccessors, setterAutoDetected, fieldAutoDetected, result );

        result.identityInfo = Optional.fromNullable( BeanIdentityInfo
                .process( logger, typeOracle, configuration, result.type, propertyAccessors ) );
        result.typeInfo = Optional.fromNullable( BeanTypeInfo
                .process( logger, typeOracle, configuration, result.type, propertyAccessors ) );

        result.format = Optional.fromNullable( propertyAccessors.getAnnotation( JsonFormat.class ) );

        JsonInclude jsonInclude = propertyAccessors.getAnnotation( JsonInclude.class );
        if ( null != jsonInclude ) {
            result.include = Optional.of( jsonInclude.value() );
        }

        JsonIgnoreProperties jsonIgnoreProperties = propertyAccessors.getAnnotation( JsonIgnoreProperties.class );
        if ( null != jsonIgnoreProperties ) {
            result.ignoreUnknown = Optional.of( jsonIgnoreProperties.ignoreUnknown() );
            if ( null != jsonIgnoreProperties.value() && jsonIgnoreProperties.value().length > 0 ) {
                result.ignoredProperties = Optional.of( jsonIgnoreProperties.value() );
            }
        }

        return result;
    }

    private static JType findType( TreeLogger logger, PropertyAccessors fieldAccessors ) throws UnableToCompleteException {
        if ( fieldAccessors.getGetter().isPresent() ) {
            return fieldAccessors.getGetter().get().getReturnType();
        } else if ( fieldAccessors.getSetter().isPresent() ) {
            return fieldAccessors.getSetter().get().getParameters()[0].getType();
        } else if ( fieldAccessors.getField().isPresent() ) {
            return fieldAccessors.getField().get().getType();
        } else if ( fieldAccessors.getParameter().isPresent() ) {
            return fieldAccessors.getParameter().get().getType();
        } else {
            logger.log( Type.ERROR, "Cannot find the type of the property " + fieldAccessors.getPropertyName() );
            throw new UnableToCompleteException();
        }
    }

    private static boolean isPropertyIgnored( RebindConfiguration configuration, PropertyAccessors propertyAccessors,
                                              BeanJsonMapperInfo mapperInfo, JType type, String propertyName ) {
        // we first check if the property is ignored
        JsonIgnore jsonIgnore = propertyAccessors.getAnnotation( JsonIgnore.class );
        if ( null != jsonIgnore && jsonIgnore.value() ) {
            return true;
        }

        // if type is ignored, we ignore the property
        if ( null != type.isClassOrInterface() ) {
            JsonIgnoreType jsonIgnoreType = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, type
                    .isClassOrInterface(), JsonIgnoreType.class );
            if ( null != jsonIgnoreType && jsonIgnoreType.value() ) {
                return true;
            }
        }

        // we check if it's not in the ignored properties
        return mapperInfo.getBeanInfo().getIgnoredFields().contains( propertyName );

    }

    private static boolean isGetterAutoDetected( PropertyAccessors propertyAccessors, BeanInfo info ) {
        if ( !propertyAccessors.getGetter().isPresent() ) {
            return false;
        }

        for ( Class<? extends Annotation> annotation : AUTO_DISCOVERY_ANNOTATIONS ) {
            if ( propertyAccessors.isAnnotationPresentOnGetter( annotation ) ) {
                return true;
            }
        }

        JMethod getter = propertyAccessors.getGetter().get();

        String methodName = getter.getName();
        JsonAutoDetect.Visibility visibility;
        if ( methodName.startsWith( "is" ) && methodName.length() > 2 && JPrimitiveType.BOOLEAN.equals( getter.getReturnType()
                .isPrimitive() ) ) {

            // getter method for a boolean
            visibility = info.getIsGetterVisibility();

        } else if ( methodName.startsWith( "get" ) && methodName.length() > 3 ) {

            visibility = info.getGetterVisibility();

        } else {
            // no annotation on method and the method does not follow naming convention
            return false;
        }
        return isAutoDetected( visibility, getter.isPrivate(), getter.isProtected(), getter.isPublic(), getter.isDefaultAccess() );
    }

    private static boolean isSetterAutoDetected( PropertyAccessors propertyAccessors, BeanInfo info ) {
        if ( !propertyAccessors.getSetter().isPresent() ) {
            return false;
        }

        for ( Class<? extends Annotation> annotation : AUTO_DISCOVERY_ANNOTATIONS ) {
            if ( propertyAccessors.isAnnotationPresentOnSetter( annotation ) ) {
                return true;
            }
        }

        JMethod setter = propertyAccessors.getSetter().get();

        String methodName = setter.getName();
        if ( !methodName.startsWith( "set" ) || methodName.length() <= 3 ) {
            // no annotation on method and the method does not follow naming convention
            return false;
        }

        return isAutoDetected( info.getSetterVisibility(), setter.isPrivate(), setter.isProtected(), setter.isPublic(), setter
                .isDefaultAccess() );
    }

    private static boolean isFieldAutoDetected( PropertyAccessors propertyAccessors, BeanInfo info ) {
        if ( !propertyAccessors.getField().isPresent() ) {
            return false;
        }

        for ( Class<? extends Annotation> annotation : AUTO_DISCOVERY_ANNOTATIONS ) {
            if ( propertyAccessors.isAnnotationPresentOnField( annotation ) ) {
                return true;
            }
        }

        JField field = propertyAccessors.getField().get();

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

    private static void determineGetter( final PropertyAccessors propertyAccessors, final boolean getterAutoDetect,
                                         boolean fieldAutoDetect, final PropertyInfo result ) {
        // if one of field/getter is present and the property has an annotation like JsonProperty or field/getter is auto detected
        if ( (propertyAccessors.getGetter().isPresent() || propertyAccessors.getField()
                .isPresent()) && (fieldAutoDetect || getterAutoDetect) ) {
            result.getterAccessor = Optional.of( new FieldReadAccessor( result.propertyName, fieldAutoDetect, propertyAccessors
                    .getField(), getterAutoDetect, propertyAccessors.getGetter() ) );
        }
    }

    private static void determineSetter( final PropertyAccessors propertyAccessors, final boolean setterAutoDetect,
                                         final boolean fieldAutoDetect, final PropertyInfo result ) {
        // if one of field/setter is present and the property has an annotation like JsonProperty or field/setter is auto detected
        if ( (propertyAccessors.getSetter().isPresent() || propertyAccessors.getField()
                .isPresent()) && (fieldAutoDetect || setterAutoDetect) ) {
            result.setterAccessor = Optional.of( new FieldWriteAccessor( result.propertyName, fieldAutoDetect, propertyAccessors
                    .getField(), setterAutoDetect, propertyAccessors.getSetter() ) );
        }
    }

    private boolean ignored;

    private boolean visible = true;

    private JType type;

    private boolean required;

    private String propertyName;

    private boolean rawValue = false;

    private Optional<String> managedReference = Optional.absent();

    private Optional<String> backReference = Optional.absent();

    private Optional<FieldReadAccessor> getterAccessor = Optional.absent();

    private Optional<FieldWriteAccessor> setterAccessor = Optional.absent();

    private Optional<BeanIdentityInfo> identityInfo = Optional.absent();

    private Optional<BeanTypeInfo> typeInfo = Optional.absent();

    private Optional<JsonFormat> format = Optional.absent();

    private Optional<Include> include = Optional.absent();

    private Optional<Boolean> ignoreUnknown = Optional.absent();

    private Optional<String[]> ignoredProperties = Optional.absent();

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

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isRawValue() {
        return rawValue;
    }

    public Optional<String> getManagedReference() {
        return managedReference;
    }

    public Optional<String> getBackReference() {
        return backReference;
    }

    public Optional<FieldReadAccessor> getGetterAccessor() {
        return getterAccessor;
    }

    public Optional<FieldWriteAccessor> getSetterAccessor() {
        return setterAccessor;
    }

    public Optional<BeanIdentityInfo> getIdentityInfo() {
        return identityInfo;
    }

    public Optional<BeanTypeInfo> getTypeInfo() {
        return typeInfo;
    }

    public Optional<JsonFormat> getFormat() {
        return format;
    }

    public Optional<Include> getInclude() {
        return include;
    }

    public Optional<Boolean> getIgnoreUnknown() {
        return ignoreUnknown;
    }

    public Optional<String[]> getIgnoredProperties() {
        return ignoredProperties;
    }
}
