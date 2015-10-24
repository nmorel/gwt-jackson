/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.rebind.property;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.nmorel.gwtjackson.rebind.JacksonTypeOracle;
import com.github.nmorel.gwtjackson.rebind.RebindConfiguration;
import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanProcessor;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.dev.util.collect.Lists;
import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.google.gwt.thirdparty.guava.common.collect.Ordering;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

/**
 * @author Nicolas Morel.
 */
public final class PropertyProcessor {

    private static final List<Class<? extends Annotation>> AUTO_DISCOVERY_ANNOTATIONS = Arrays
            .asList( JsonProperty.class, JsonManagedReference.class, JsonBackReference.class, JsonValue.class, JsonAnySetter.class,
                    JsonAnyGetter.class );

    public static PropertiesContainer findAllProperties( RebindConfiguration configuration, TreeLogger logger, JacksonTypeOracle
            typeOracle, BeanInfo beanInfo, boolean mapperInSamePackageAsType ) throws UnableToCompleteException {

        // we first parse the bean to retrieve all the properties
        ImmutableMap<String, PropertyAccessors> fieldsMap = PropertyParser.findPropertyAccessors( configuration, logger, beanInfo );

        // value, any getter and any setter properties
        PropertyInfo valuePropertyInfo = null;
        PropertyInfo anyGetterPropertyInfo = null;
        PropertyInfo anySetterPropertyInfo = null;

        // Processing all the properties accessible via field, getter or setter
        Map<String, PropertyInfo> propertiesMap = new LinkedHashMap<String, PropertyInfo>();
        for ( PropertyAccessors propertyAccessors : fieldsMap.values() ) {

            Optional<PropertyInfo> propertyInfoOptional = processProperty( configuration, logger, typeOracle, propertyAccessors,
                    beanInfo, mapperInSamePackageAsType );

            if ( propertyInfoOptional.isPresent() ) {

                PropertyInfo propertyInfo = propertyInfoOptional.get();

                boolean put = true;

                if ( propertyInfo.isValue() ) {
                    if ( null != valuePropertyInfo ) {
                        logger.log( TreeLogger.Type.WARN, "More than one method annotated with @JsonValue on " + beanInfo.getType()
                                .getName() + ". Using the first one." );
                    } else {
                        valuePropertyInfo = propertyInfo;
                    }
                    put = false;
                }

                if ( propertyInfo.isAnyGetter() ) {
                    if ( null != anyGetterPropertyInfo ) {
                        logger.log( TreeLogger.Type.WARN, "More than one method annotated with @JsonAnyGetter on " + beanInfo.getType()
                                .getName() + ". Using the first one." );
                    } else {
                        anyGetterPropertyInfo = propertyInfo;
                    }
                    put = false;
                }

                if ( propertyInfo.isAnySetter() ) {
                    if ( null != anySetterPropertyInfo ) {
                        logger.log( TreeLogger.Type.WARN, "More than one method annotated with @JsonAnySetter on " + beanInfo.getType()
                                .getName() + ". Using the first one." );
                    } else {
                        anySetterPropertyInfo = propertyInfo;
                    }
                    put = false;
                }

                if ( put ) {
                    propertiesMap.put( propertyInfo.getPropertyName(), propertyInfo );
                }

            } else {
                logger.log( TreeLogger.Type.DEBUG, "Field " + propertyAccessors.getPropertyName() + " of type " + beanInfo
                        .getType() + " is not visible" );
            }
        }

        ImmutableMap.Builder<String, PropertyInfo> result = ImmutableMap.builder();

        // we first add the properties defined in order
        for ( String orderedProperty : beanInfo.getPropertyOrderList() ) {
            // we remove the entry to have the map with only properties with natural or alphabetic order
            PropertyInfo property = propertiesMap.remove( orderedProperty );
            if ( null != property ) {
                result.put( property.getPropertyName(), property );
            }
        }

        // if the user asked for an alphabetic order, we sort the rest of the properties
        if ( beanInfo.isPropertyOrderAlphabetic() ) {

            List<Entry<String, PropertyInfo>> entries = new ArrayList<Entry<String, PropertyInfo>>( propertiesMap.entrySet() );

            // sorting entries alphabetically on their key
            Lists.sort( entries, Ordering.natural().onResultOf( new Function<Entry<String, PropertyInfo>, String>() {
                @Override
                public String apply( @Nullable Entry<String, PropertyInfo> entry ) {
                    return null == entry ? null : entry.getKey();
                }
            } ) );

            for ( Map.Entry<String, PropertyInfo> entry : entries ) {
                result.put( entry.getKey(), entry.getValue() );
            }

        } else {

            // no specified order, we add the rest of the properties in the order we found them
            for ( Map.Entry<String, PropertyInfo> entry : propertiesMap.entrySet() ) {
                result.put( entry.getKey(), entry.getValue() );
            }
        }

        return new PropertiesContainer( result.build(), Optional.fromNullable( valuePropertyInfo ), Optional
                .fromNullable( anyGetterPropertyInfo ), Optional.fromNullable( anySetterPropertyInfo ) );
    }

    private static Optional<PropertyInfo> processProperty( RebindConfiguration configuration, TreeLogger logger, JacksonTypeOracle
            typeOracle, PropertyAccessors propertyAccessors, BeanInfo beanInfo, boolean samePackage ) throws UnableToCompleteException {

        boolean getterAutoDetected = isGetterAutoDetected( configuration, propertyAccessors, beanInfo );
        boolean setterAutoDetected = isSetterAutoDetected( configuration, propertyAccessors, beanInfo );
        boolean fieldAutoDetected = isFieldAutoDetected( configuration, propertyAccessors, beanInfo );

        if ( !getterAutoDetected && !setterAutoDetected && !fieldAutoDetected && !propertyAccessors.getParameter().isPresent() ) {
            // none of the field have been auto-detected, we ignore the field
            return Optional.absent();
        }

        final String propertyName = propertyAccessors.getPropertyName();
        final JType type = findType( logger, propertyAccessors, typeOracle );

        PropertyInfoBuilder builder = new PropertyInfoBuilder( propertyName, type );

        builder.setIgnored( isPropertyIgnored( configuration, propertyAccessors, beanInfo, type, propertyName ) );
        if ( builder.isIgnored() ) {
            return Optional.of( builder.build() );
        }

        Optional<JsonProperty> jsonProperty = propertyAccessors.getAnnotation( JsonProperty.class );
        builder.setRequired( jsonProperty.isPresent() && jsonProperty.get().required() );

        Optional<JsonManagedReference> jsonManagedReference = propertyAccessors.getAnnotation( JsonManagedReference.class );
        builder.setManagedReference( Optional.fromNullable( jsonManagedReference.isPresent() ? jsonManagedReference.get()
                .value() : null ) );

        Optional<JsonBackReference> jsonBackReference = propertyAccessors.getAnnotation( JsonBackReference.class );
        builder.setBackReference( Optional.fromNullable( jsonBackReference.isPresent() ? jsonBackReference.get().value() : null ) );

        if ( !builder.getBackReference().isPresent() ) {
            determineGetter( propertyAccessors, samePackage, getterAutoDetected, fieldAutoDetected, builder );

            Optional<JsonRawValue> jsonRawValue = propertyAccessors.getAnnotation( JsonRawValue.class );
            builder.setRawValue( jsonRawValue.isPresent() && jsonRawValue.get().value() );
        }
        determineSetter( propertyAccessors, samePackage, setterAutoDetected, fieldAutoDetected, builder );

        Optional<JsonValue> jsonValue = propertyAccessors.getAnnotation( JsonValue.class );
        if ( jsonValue.isPresent() && jsonValue.get().value() && builder.getGetterAccessor().isPresent() && builder.getGetterAccessor()
                .get().getMethod().isPresent() ) {
            builder.setValue( true );
        }

        Optional<JsonAnyGetter> jsonAnyGetter = propertyAccessors.getAnnotation( JsonAnyGetter.class );
        if ( jsonAnyGetter.isPresent() && builder.getGetterAccessor().isPresent() && builder.getGetterAccessor().get().getMethod()
                .isPresent() ) {
            builder.setAnyGetter( true );
        }

        Optional<JsonAnySetter> jsonAnySetter = propertyAccessors.getAnnotation( JsonAnySetter.class );
        if ( jsonAnySetter.isPresent() && builder.getSetterAccessor().isPresent() && builder.getSetterAccessor().get().getMethod()
                .isPresent() && builder.getSetterAccessor().get().getMethod().get().getParameterTypes().length == 2 ) {
            builder.setAnySetter( true );
        }

        Optional<JsonUnwrapped> jsonUnwrapped = propertyAccessors.getAnnotation( JsonUnwrapped.class );
        if ( jsonUnwrapped.isPresent() && jsonUnwrapped.get().enabled() ) {
            builder.setUnwrapped( true );
        }

        processBeanAnnotation( logger, typeOracle, configuration, type, propertyAccessors, builder );

        builder.setFormat( propertyAccessors.getAnnotation( JsonFormat.class ) );

        Optional<JsonInclude> jsonInclude = propertyAccessors.getAnnotation( JsonInclude.class );
        if ( jsonInclude.isPresent() ) {
            builder.setInclude( Optional.of( jsonInclude.get().value() ) );
        } else {
            builder.setInclude( beanInfo.getInclude() );
        }

        Optional<JsonIgnoreProperties> jsonIgnoreProperties = propertyAccessors.getAnnotation( JsonIgnoreProperties.class );
        if ( jsonIgnoreProperties.isPresent() ) {
            builder.setIgnoreUnknown( Optional.of( jsonIgnoreProperties.get().ignoreUnknown() ) );
            if ( null != jsonIgnoreProperties.get().value() && jsonIgnoreProperties.get().value().length > 0 ) {
                builder.setIgnoredProperties( Optional.of( jsonIgnoreProperties.get().value() ) );
            }
        }

        return Optional.of( builder.build() );
    }

    private static boolean isGetterAutoDetected( RebindConfiguration configuration, PropertyAccessors propertyAccessors, BeanInfo info ) {
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
            if ( Visibility.DEFAULT == visibility ) {
                visibility = configuration.getDefaultIsGetterVisibility();
            }

        } else if ( methodName.startsWith( "get" ) && methodName.length() > 3 ) {

            visibility = info.getGetterVisibility();
            if ( Visibility.DEFAULT == visibility ) {
                visibility = configuration.getDefaultGetterVisibility();
            }

        } else {
            // no annotation on method and the method does not follow naming convention
            return false;
        }
        return isAutoDetected( visibility, getter.isPrivate(), getter.isProtected(), getter.isPublic(), getter.isDefaultAccess() );
    }

    private static boolean isSetterAutoDetected( RebindConfiguration configuration, PropertyAccessors propertyAccessors, BeanInfo info ) {
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

        JsonAutoDetect.Visibility visibility = info.getSetterVisibility();
        if ( Visibility.DEFAULT == visibility ) {
            visibility = configuration.getDefaultSetterVisibility();
        }
        return isAutoDetected( visibility, setter.isPrivate(), setter.isProtected(), setter.isPublic(), setter
                .isDefaultAccess() );
    }

    private static boolean isFieldAutoDetected( RebindConfiguration configuration, PropertyAccessors propertyAccessors, BeanInfo info ) {
        if ( !propertyAccessors.getField().isPresent() ) {
            return false;
        }

        for ( Class<? extends Annotation> annotation : AUTO_DISCOVERY_ANNOTATIONS ) {
            if ( propertyAccessors.isAnnotationPresentOnField( annotation ) ) {
                return true;
            }
        }

        JField field = propertyAccessors.getField().get();

        JsonAutoDetect.Visibility visibility = info.getFieldVisibility();
        if ( Visibility.DEFAULT == visibility ) {
            visibility = configuration.getDefaultFieldVisibility();
        }
        return isAutoDetected( visibility, field.isPrivate(), field.isProtected(), field.isPublic(), field
                .isDefaultAccess() );
    }

    private static boolean isAutoDetected( JsonAutoDetect.Visibility visibility, boolean isPrivate, boolean isProtected, boolean
            isPublic, boolean isDefaultAccess ) {
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

    private static JType findType( TreeLogger logger, PropertyAccessors fieldAccessors, JacksonTypeOracle typeOracle ) throws
            UnableToCompleteException {
        JType type;
        if ( fieldAccessors.getGetter().isPresent() ) {
            type = fieldAccessors.getGetter().get().getReturnType();
        } else if ( fieldAccessors.getSetter().isPresent() ) {
            type = fieldAccessors.getSetter().get().getParameters()[0].getType();
        } else if ( fieldAccessors.getField().isPresent() ) {
            type = fieldAccessors.getField().get().getType();
        } else if ( fieldAccessors.getParameter().isPresent() ) {
            type = fieldAccessors.getParameter().get().getType();
        } else {
            logger.log( Type.ERROR, "Cannot find the type of the property " + fieldAccessors.getPropertyName() );
            throw new UnableToCompleteException();
        }

        try {
            Class clazz = Class.forName( "com.fasterxml.jackson.databind.annotation.JsonDeserialize" );
            Optional<Annotation> jd = fieldAccessors.getAnnotation( clazz );
            if ( jd.isPresent() ) {
                return typeOracle.replaceType( logger, type, jd.get() );
            }
        } catch ( ClassNotFoundException e ) {
            logger.log( Type.DEBUG, "com.fasterxml.jackson.databind.annotation.JsonDeserialize not found" );
        }

        return type;
    }

    private static boolean isPropertyIgnored( RebindConfiguration configuration, PropertyAccessors propertyAccessors, BeanInfo beanInfo,
                                              JType type, String propertyName ) {
        // we first check if the property is ignored
        Optional<JsonIgnore> jsonIgnore = propertyAccessors.getAnnotation( JsonIgnore.class );
        if ( jsonIgnore.isPresent() && jsonIgnore.get().value() ) {
            return true;
        }

        // if type is ignored, we ignore the property
        if ( null != type.isClassOrInterface() ) {
            Optional<JsonIgnoreType> jsonIgnoreType = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, type
                    .isClassOrInterface(), JsonIgnoreType.class );
            if ( jsonIgnoreType.isPresent() && jsonIgnoreType.get().value() ) {
                return true;
            }
        }

        // we check if it's not in the ignored properties
        return beanInfo.getIgnoredFields().contains( propertyName );

    }

    private static void determineGetter( final PropertyAccessors propertyAccessors, final boolean samePackage, final boolean
            getterAutoDetect, boolean fieldAutoDetect, final PropertyInfoBuilder builder ) {
        // if one of field/getter is present and the property has an annotation like JsonProperty or field/getter is auto detected
        if ( (propertyAccessors.getGetter().isPresent() || propertyAccessors.getField()
                .isPresent()) && (fieldAutoDetect || getterAutoDetect) ) {
            builder.setGetterAccessor( Optional.of( new FieldReadAccessor( builder
                    .getPropertyName(), samePackage, fieldAutoDetect, propertyAccessors.getField(), getterAutoDetect, propertyAccessors
                    .getGetter() ) ) );
        }
    }

    private static void determineSetter( final PropertyAccessors propertyAccessors, final boolean samePackage, final boolean
            setterAutoDetect, final boolean fieldAutoDetect, final PropertyInfoBuilder builder ) {
        // if one of field/setter is present and the property has an annotation like JsonProperty or field/setter is auto detected
        if ( (propertyAccessors.getSetter().isPresent() || propertyAccessors.getField()
                .isPresent()) && (fieldAutoDetect || setterAutoDetect || builder.getBackReference().isPresent()) ) {
            builder.setSetterAccessor( Optional.of( new FieldWriteAccessor( builder
                    .getPropertyName(), samePackage, fieldAutoDetect, propertyAccessors.getField(), setterAutoDetect, propertyAccessors
                    .getSetter() ) ) );
        }
    }

    private static void processBeanAnnotation( TreeLogger logger, JacksonTypeOracle typeOracle, RebindConfiguration configuration, JType
            type, PropertyAccessors propertyAccessors, PropertyInfoBuilder builder ) throws UnableToCompleteException {

        // identity
        Optional<JsonIdentityInfo> jsonIdentityInfo = propertyAccessors.getAnnotation( JsonIdentityInfo.class );
        Optional<JsonIdentityReference> jsonIdentityReference = propertyAccessors.getAnnotation( JsonIdentityReference.class );

        // type info
        Optional<JsonTypeInfo> jsonTypeInfo = propertyAccessors.getAnnotation( JsonTypeInfo.class );
        Optional<JsonSubTypes> propertySubTypes = propertyAccessors.getAnnotation( JsonSubTypes.class );

        // if no annotation is present that overrides bean processing, we just stop now
        if ( !jsonIdentityInfo.isPresent() && !jsonIdentityReference.isPresent() && !jsonTypeInfo.isPresent() && !propertySubTypes
                .isPresent() ) {
            // no override on field
            return;
        }

        // we need to find the bean to apply annotation on
        Optional<JClassType> beanType = extractBeanType( logger, typeOracle, type, builder.getPropertyName() );

        if ( beanType.isPresent() ) {
            if ( jsonIdentityInfo.isPresent() || jsonIdentityReference.isPresent() ) {
                builder.setIdentityInfo( BeanProcessor.processIdentity( logger, typeOracle, configuration, beanType
                        .get(), jsonIdentityInfo, jsonIdentityReference ) );
            }

            if ( jsonTypeInfo.isPresent() || propertySubTypes.isPresent() ) {
                builder.setTypeInfo( BeanProcessor.processType( logger, typeOracle, configuration, beanType
                        .get(), jsonTypeInfo, propertySubTypes ) );
            }
        } else {
            logger.log( Type.WARN, "Annotation present on property " + builder.getPropertyName() + " but no valid bean has been found." );
        }
    }

    /**
     * Extract the bean type from the type given in parameter. For {@link java.util.Collection}, it gives the bounded type. For {@link
     * java.util.Map}, it gives the second bounded type. Otherwise, it gives the type given in parameter.
     *
     * @param type type to extract the bean type
     * @param propertyName name of the property
     *
     * @return the extracted type
     */
    private static Optional<JClassType> extractBeanType( TreeLogger logger, JacksonTypeOracle typeOracle, JType type, String propertyName
    ) {
        if ( null != type.isWildcard() ) {
            // we use the base type to find the serializer to use
            type = type.isWildcard().getBaseType();
        }

        if ( null != type.isRawType() ) {
            type = type.isRawType().getBaseType();
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            return extractBeanType( logger, typeOracle, arrayType.getComponentType(), propertyName );
        }

        JClassType classType = type.isClassOrInterface();
        if ( null == classType ) {
            return Optional.absent();
        } else if ( typeOracle.isIterable( classType ) ) {
            if ( (null == classType.isParameterized() || classType.isParameterized().getTypeArgs().length != 1) && (null == classType
                    .isGenericType() || classType.isGenericType().getTypeParameters().length != 1) ) {
                logger.log( Type.INFO, "Expected one argument for the java.lang.Iterable '" + propertyName + "'. Applying annotations to " +
                        "type " + classType.getParameterizedQualifiedSourceName() );
                return Optional.of( classType );
            }
            if ( null != classType.isParameterized() ) {
                return extractBeanType( logger, typeOracle, classType.isParameterized().getTypeArgs()[0], propertyName );
            } else {
                return extractBeanType( logger, typeOracle, classType.isGenericType().getTypeParameters()[0].getBaseType(), propertyName );
            }
        } else if ( typeOracle.isMap( classType ) ) {
            if ( (null == classType.isParameterized() || classType.isParameterized().getTypeArgs().length != 2) && (null == classType
                    .isGenericType() || classType.isGenericType().getTypeParameters().length != 2) ) {
                logger.log( Type.INFO, "Expected two arguments for the java.util.Map '" + propertyName + "'. Applying annotations to " +
                        "type " + classType.getParameterizedQualifiedSourceName() );
                return Optional.of( classType );
            }
            if ( null != classType.isParameterized() ) {
                return extractBeanType( logger, typeOracle, classType.isParameterized().getTypeArgs()[1], propertyName );
            } else {
                return extractBeanType( logger, typeOracle, classType.isGenericType().getTypeParameters()[1].getBaseType(), propertyName );
            }
        } else {
            return Optional.of( classType );
        }
    }
}
