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

package com.github.nmorel.gwtjackson.rebind.bean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.github.nmorel.gwtjackson.rebind.CreatorUtils;
import com.github.nmorel.gwtjackson.rebind.JacksonTypeOracle;
import com.github.nmorel.gwtjackson.rebind.RebindConfiguration;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.getAnnotation;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.isAnnotationPresent;

/**
 * @author Nicolas Morel.
 */
public final class BeanProcessor {

    public static BeanInfo processBean( TreeLogger logger, JacksonTypeOracle typeOracle, RebindConfiguration configuration,
                                        JClassType beanType ) throws UnableToCompleteException {
        BeanInfoBuilder builder = new BeanInfoBuilder();
        builder.setType( beanType );

        if ( null != beanType.isGenericType() ) {
            builder.setParameterizedTypes( Arrays.<JClassType>asList( beanType.isGenericType().getTypeParameters() ) );
        }

        determineInstanceCreator( configuration, logger, beanType, builder );

        Optional<JsonAutoDetect> jsonAutoDetect = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, beanType,
                JsonAutoDetect.class );
        if ( jsonAutoDetect.isPresent() ) {
            builder.setCreatorVisibility( jsonAutoDetect.get().creatorVisibility() );
            builder.setFieldVisibility( jsonAutoDetect.get().fieldVisibility() );
            builder.setGetterVisibility( jsonAutoDetect.get().getterVisibility() );
            builder.setIsGetterVisibility( jsonAutoDetect.get().isGetterVisibility() );
            builder.setSetterVisibility( jsonAutoDetect.get().setterVisibility() );
        }

        Optional<JsonIgnoreProperties> jsonIgnoreProperties = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, beanType,
                JsonIgnoreProperties.class );
        if ( jsonIgnoreProperties.isPresent() ) {
            builder.setIgnoredFields( new LinkedHashSet<String>( Arrays.asList( jsonIgnoreProperties.get().value() ) ) );
            builder.setIgnoreUnknown( jsonIgnoreProperties.get().ignoreUnknown() );
        }

        Optional<JsonPropertyOrder> jsonPropertyOrder = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, beanType,
                JsonPropertyOrder.class );
        builder.setPropertyOrderAlphabetic( jsonPropertyOrder.isPresent() && jsonPropertyOrder.get().alphabetic() );
        if ( jsonPropertyOrder.isPresent() && jsonPropertyOrder.get().value().length > 0 ) {
            builder.setPropertyOrderList( Arrays.asList( jsonPropertyOrder.get().value() ) );
        } else if ( !builder.getCreatorParameters().isEmpty() ) {
            List<String> propertyOrderList = new ArrayList<String>( builder.getCreatorParameters().keySet() );
            builder.setPropertyOrderList( propertyOrderList );
            if ( builder.isPropertyOrderAlphabetic() ) {
                Collections.sort( propertyOrderList );
            }
        }

        builder.setIdentityInfo( processIdentity( logger, typeOracle, configuration, beanType ) );
        builder.setTypeInfo( processType( logger, typeOracle, configuration, beanType ) );

        return builder.build();
    }

    /**
     * Look for the method to create a new instance of the bean. If none are found or the bean is abstract or an interface, we considered it
     * as non instantiable.
     *
     * @param logger logger
     * @param beanType
     * @param builder current bean builder
     */
    private static void determineInstanceCreator( RebindConfiguration configuration, TreeLogger logger, JClassType beanType,
                                                  BeanInfoBuilder builder ) {
        if ( null != beanType.isInterface() || beanType.isAbstract() ) {
            return;
        }

        Optional<JClassType> mixinClass = configuration.getMixInAnnotations( beanType );

        // we search for @JsonCreator annotation
        JConstructor creatorDefaultConstructor = null;
        JConstructor creatorConstructor = null;

        // we keep the list containing the mixin creator and the real creator
        List<? extends JAbstractMethod> creators = Collections.emptyList();

        for ( JConstructor constructor : beanType.getConstructors() ) {
            if ( constructor.getParameters().length == 0 ) {
                creatorDefaultConstructor = constructor;
                continue;
            }

            // A constructor is considered as a creator if
            // - he is annotated with JsonCreator and
            //   * all its parameters are annotated with JsonProperty
            //   * or it has only one parameter
            // - or all its parameters are annotated with JsonProperty

            List<JConstructor> constructors = new ArrayList<JConstructor>();
            if ( mixinClass.isPresent() && null == mixinClass.get().isInterface() ) {
                JConstructor mixinConstructor = mixinClass.get().findConstructor( constructor.getParameterTypes() );
                if ( null != mixinConstructor ) {
                    constructors.add( mixinConstructor );
                }
            }
            constructors.add( constructor );

            Optional<JsonIgnore> jsonIgnore = getAnnotation( JsonIgnore.class, constructors );
            if ( jsonIgnore.isPresent() && jsonIgnore.get().value() ) {
                continue;
            }

            boolean isAllParametersAnnotatedWithJsonProperty = isAllParametersAnnotatedWith( constructors.get( 0 ), JsonProperty.class );
            if ( (isAnnotationPresent( JsonCreator.class, constructors ) && ((isAllParametersAnnotatedWithJsonProperty) || (constructor
                    .getParameters().length == 1))) || isAllParametersAnnotatedWithJsonProperty ) {
                creatorConstructor = constructor;
                creators = constructors;
                break;
            }
        }

        JMethod creatorFactory = null;
        if ( null == creatorConstructor ) {
            // searching for factory method
            for ( JMethod method : beanType.getMethods() ) {
                if ( method.isStatic() ) {

                    List<JMethod> methods = new ArrayList<JMethod>();
                    if ( mixinClass.isPresent() && null == mixinClass.get().isInterface() ) {
                        JMethod mixinMethod = mixinClass.get().findMethod( method.getName(), method.getParameterTypes() );
                        if ( null != mixinMethod && mixinMethod.isStatic() ) {
                            methods.add( mixinMethod );
                        }
                    }
                    methods.add( method );

                    Optional<JsonIgnore> jsonIgnore = getAnnotation( JsonIgnore.class, methods );
                    if ( jsonIgnore.isPresent() && jsonIgnore.get().value() ) {
                        continue;
                    }

                    if ( isAnnotationPresent( JsonCreator.class, methods ) && (method
                            .getParameters().length == 1 || isAllParametersAnnotatedWith( methods.get( 0 ), JsonProperty.class )) ) {
                        creatorFactory = method;
                        creators = methods;
                        break;
                    }
                }
            }
        }

        final Optional<JAbstractMethod> creatorMethod;
        boolean defaultConstructor = false;

        if ( null != creatorConstructor ) {
            creatorMethod = Optional.<JAbstractMethod>of( creatorConstructor );
        } else if ( null != creatorFactory ) {
            creatorMethod = Optional.<JAbstractMethod>of( creatorFactory );
        } else if ( null != creatorDefaultConstructor ) {
            defaultConstructor = true;
            creatorMethod = Optional.<JAbstractMethod>of( creatorDefaultConstructor );
        } else {
            creatorMethod = Optional.absent();
        }

        builder.setCreatorMethod( creatorMethod );
        builder.setCreatorDefaultConstructor( defaultConstructor );

        if ( creatorMethod.isPresent() && !defaultConstructor ) {
            if ( creatorMethod.get().getParameters().length == 1 && !isAllParametersAnnotatedWith( creators
                    .get( 0 ), JsonProperty.class ) ) {
                // delegation constructor
                builder.setCreatorDelegation( true );
            } else {
                // we want the property name define in the mixin and the parameter defined in the real creator method
                Map<String, JParameter> creatorParameters = new LinkedHashMap<String, JParameter>();
                for ( int i = 0; i < creatorMethod.get().getParameters().length; i++ ) {
                    creatorParameters.put( creators.get( 0 ).getParameters()[i].getAnnotation( JsonProperty.class ).value(), creators
                            .get( creators.size() - 1 ).getParameters()[i] );
                }
                builder.setCreatorParameters( creatorParameters );
            }
        }
    }

    private static <T extends Annotation> boolean isAllParametersAnnotatedWith( JAbstractMethod method, Class<T> annotation ) {
        for ( JParameter parameter : method.getParameters() ) {
            if ( !parameter.isAnnotationPresent( annotation ) ) {
                return false;
            }
        }

        return true;
    }

    private static Optional<BeanIdentityInfo> processIdentity( TreeLogger logger, JacksonTypeOracle typeOracle,
                                                               RebindConfiguration configuration,
                                                               JClassType type ) throws UnableToCompleteException {
        return processIdentity( logger, typeOracle, configuration, type, Optional.<JsonIdentityInfo>absent(), Optional
                .<JsonIdentityReference>absent() );
    }

    public static Optional<BeanIdentityInfo> processIdentity( TreeLogger logger, JacksonTypeOracle typeOracle,
                                                              RebindConfiguration configuration, JClassType type,
                                                              Optional<JsonIdentityInfo> jsonIdentityInfo,
                                                              Optional<JsonIdentityReference> jsonIdentityReference ) throws
            UnableToCompleteException {

        if ( !jsonIdentityInfo.isPresent() ) {
            jsonIdentityInfo = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, type, JsonIdentityInfo.class );
        }

        if ( jsonIdentityInfo.isPresent() && ObjectIdGenerators.None.class != jsonIdentityInfo.get().generator() ) {
            if ( !jsonIdentityReference.isPresent() ) {
                jsonIdentityReference = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, type, JsonIdentityReference.class );
            }

            String propertyName = jsonIdentityInfo.get().property();
            boolean alwaysAsId = jsonIdentityReference.isPresent() && jsonIdentityReference.get().alwaysAsId();
            Class<? extends ObjectIdGenerator<?>> generator = jsonIdentityInfo.get().generator();
            Class<?> scope = jsonIdentityInfo.get().scope();

            BeanIdentityInfo beanIdentityInfo;
            if ( generator.isAssignableFrom( PropertyGenerator.class ) ) {

                beanIdentityInfo = new ImmutableBeanIdentityInfo( propertyName, alwaysAsId, generator, scope );

            } else {

                JType identityType;
                if ( IntSequenceGenerator.class == generator ) {
                    identityType = typeOracle.getType( Integer.class.getName() );
                } else if ( UUIDGenerator.class == generator ) {
                    identityType = typeOracle.getType( UUID.class.getName() );
                } else {
                    JClassType generatorType = typeOracle.getType( generator.getCanonicalName() );
                    JClassType objectIdGeneratorType = generatorType.getSuperclass();
                    while ( !objectIdGeneratorType.getQualifiedSourceName().equals( ObjectIdGenerator.class.getName() ) ) {
                        objectIdGeneratorType = objectIdGeneratorType.getSuperclass();
                    }
                    identityType = objectIdGeneratorType.isParameterized().getTypeArgs()[0];
                }

                beanIdentityInfo = new ImmutableBeanIdentityInfo( propertyName, alwaysAsId, generator, scope, identityType );

            }
            return Optional.of( beanIdentityInfo );
        }
        return Optional.absent();
    }

    private static Optional<BeanTypeInfo> processType( TreeLogger logger, JacksonTypeOracle typeOracle,
                                                       RebindConfiguration configuration,
                                                       JClassType type ) throws UnableToCompleteException {
        return processType( logger, typeOracle, configuration, type, Optional.<JsonTypeInfo>absent(), Optional.<JsonSubTypes>absent() );
    }

    public static Optional<BeanTypeInfo> processType( TreeLogger logger, JacksonTypeOracle typeOracle, RebindConfiguration configuration,
                                                      JClassType type, Optional<JsonTypeInfo> jsonTypeInfo,
                                                      Optional<JsonSubTypes> propertySubTypes ) throws UnableToCompleteException {

        if ( !jsonTypeInfo.isPresent() ) {
            jsonTypeInfo = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, type, JsonTypeInfo.class );
            if ( !jsonTypeInfo.isPresent() ) {
                return Optional.absent();
            }
        }

        Id use = jsonTypeInfo.get().use();
        As include = jsonTypeInfo.get().include();
        String propertyName = jsonTypeInfo.get().property().isEmpty() ? jsonTypeInfo.get().use().getDefaultPropertyName() : jsonTypeInfo
                .get().property();

        Map<JClassType, String> classToMetadata = new HashMap<JClassType, String>();
        Optional<JsonSubTypes> typeSubTypes = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, type, JsonSubTypes.class );
        ImmutableList<JClassType> allSubtypes = CreatorUtils.filterSubtypes( type );
        classToMetadata.put( type, extractTypeMetadata( logger, configuration, type, type, jsonTypeInfo
                .get(), propertySubTypes, typeSubTypes, allSubtypes ) );

        for ( JClassType subtype : allSubtypes ) {
            classToMetadata.put( subtype, extractTypeMetadata( logger, configuration, type, subtype, jsonTypeInfo
                    .get(), propertySubTypes, typeSubTypes, allSubtypes ) );
        }

        return Optional.<BeanTypeInfo>of( new ImmutableBeanTypeInfo( use, include, propertyName, classToMetadata ) );
    }

    private static String extractTypeMetadata( TreeLogger logger, RebindConfiguration configuration, JClassType baseType,
                                               JClassType subtype, JsonTypeInfo typeInfo, Optional<JsonSubTypes> propertySubTypes,
                                               Optional<JsonSubTypes> baseSubTypes, ImmutableList<JClassType> allSubtypes ) throws
            UnableToCompleteException {
        switch ( typeInfo.use() ) {
            case NAME:
                // we first look the name on JsonSubTypes annotations. Top ones override the bottom ones.
                String name = findNameOnJsonSubTypes( baseType, subtype, allSubtypes, propertySubTypes, baseSubTypes );
                if ( null != name && !"".equals( name ) ) {
                    return name;
                }

                // we look if the name is defined on the type with JsonTypeName
                Optional<JsonTypeName> typeName = findFirstEncounteredAnnotationsOnAllHierarchy( configuration, subtype,
                        JsonTypeName.class );
                if ( typeName.isPresent() && !Strings.isNullOrEmpty( typeName.get().value() ) ) {
                    return typeName.get().value();
                }

                // we use the default name (ie simple name of the class)
                String simpleBinaryName = subtype.getQualifiedBinaryName();
                int indexLastDot = simpleBinaryName.lastIndexOf( '.' );
                if ( indexLastDot != -1 ) {
                    simpleBinaryName = simpleBinaryName.substring( indexLastDot + 1 );
                }
                return simpleBinaryName;
            case MINIMAL_CLASS:
                if ( !baseType.getPackage().isDefault() ) {
                    String basePackage = baseType.getPackage().getName();
                    if ( subtype.getQualifiedBinaryName().startsWith( basePackage + "." ) ) {
                        return subtype.getQualifiedBinaryName().substring( basePackage.length() );
                    }
                }
            case CLASS:
                return subtype.getQualifiedBinaryName();
            default:
                logger.log( TreeLogger.Type.ERROR, "JsonTypeInfo.Id." + typeInfo.use() + " is not supported" );
                throw new UnableToCompleteException();
        }
    }

    private static String findNameOnJsonSubTypes( JClassType baseType, JClassType subtype, ImmutableList<JClassType> allSubtypes,
                                                  Optional<JsonSubTypes> propertySubTypes, Optional<JsonSubTypes> baseSubTypes ) {
        JsonSubTypes.Type typeFound = findTypeOnSubTypes( subtype, propertySubTypes );
        if ( null != typeFound ) {
            return typeFound.name();
        }

        typeFound = findTypeOnSubTypes( subtype, baseSubTypes );
        if ( null != typeFound ) {
            return typeFound.name();
        }

        if ( baseType != subtype ) {
            // we look in all the hierarchy
            JClassType type = subtype;
            while ( null != type ) {
                if ( allSubtypes.contains( type ) ) {
                    JsonSubTypes.Type found = findTypeOnSubTypes( subtype, Optional.fromNullable( type
                            .getAnnotation( JsonSubTypes.class ) ) );
                    if ( null != found ) {
                        typeFound = found;
                    }
                }
                type = type.getSuperclass();
            }

            if ( null != typeFound ) {
                return typeFound.name();
            }
        }

        return null;
    }

    private static JsonSubTypes.Type findTypeOnSubTypes( JClassType subtype, Optional<JsonSubTypes> jsonSubTypes ) {
        if ( jsonSubTypes.isPresent() ) {
            for ( JsonSubTypes.Type type : jsonSubTypes.get().value() ) {
                if ( type.value().getName().equals( subtype.getQualifiedBinaryName() ) ) {
                    return type;
                }
            }
        }
        return null;
    }

}
