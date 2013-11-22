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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

/**
 * @author Nicolas Morel
 */
public final class BeanInfo {

    public static BeanInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, BeanJsonMapperInfo mapperInfo ) throws
        UnableToCompleteException {
        BeanInfo result = new BeanInfo();
        result.type = mapperInfo.getType();

        result.parameterizedTypes = null == mapperInfo.getType().isGenericType() ? new JClassType[0] : mapperInfo.getType().isGenericType()
            .getTypeParameters();

        determineInstanceCreator( logger, result );

        JsonAutoDetect jsonAutoDetect = findFirstEncounteredAnnotationsOnAllHierarchy( mapperInfo.getType(), JsonAutoDetect.class );
        if ( null != jsonAutoDetect ) {
            result.creatorVisibility = jsonAutoDetect.creatorVisibility();
            result.fieldVisibility = jsonAutoDetect.fieldVisibility();
            result.getterVisibility = jsonAutoDetect.getterVisibility();
            result.isGetterVisibility = jsonAutoDetect.isGetterVisibility();
            result.setterVisibility = jsonAutoDetect.setterVisibility();
        }

        JsonIgnoreProperties jsonIgnoreProperties = findFirstEncounteredAnnotationsOnAllHierarchy( mapperInfo
            .getType(), JsonIgnoreProperties.class );
        if ( null != jsonIgnoreProperties ) {
            for ( String ignoreProperty : jsonIgnoreProperties.value() ) {
                result.addIgnoredField( ignoreProperty );
            }
            result.ignoreUnknown = jsonIgnoreProperties.ignoreUnknown();
        }

        JsonPropertyOrder jsonPropertyOrder = findFirstEncounteredAnnotationsOnAllHierarchy( mapperInfo
            .getType(), JsonPropertyOrder.class );
        if ( null != jsonPropertyOrder && jsonPropertyOrder.value().length > 0 ) {
            result.propertyOrderList = Arrays.asList( jsonPropertyOrder.value() );
        } else if ( !result.creatorParameters.isEmpty() ) {
            result.propertyOrderList = new ArrayList<String>( result.creatorParameters.keySet() );
        } else {
            result.propertyOrderList = Collections.emptyList();
        }
        result.propertyOrderAlphabetic = null != jsonPropertyOrder && jsonPropertyOrder.alphabetic();

        result.identityInfo = Optional.fromNullable( BeanIdentityInfo.process( logger, typeOracle, mapperInfo.getType() ) );
        result.typeInfo = Optional.fromNullable( BeanTypeInfo.process( logger, typeOracle, mapperInfo.getType() ) );

        return result;
    }

    /**
     * Look for the method to create a new instance of the bean. If none are found or the bean is abstract or an interface, we considered it
     * as non instantiable.
     *
     * @param logger logger
     * @param info current bean info
     */
    private static void determineInstanceCreator( TreeLogger logger, BeanInfo info ) {
        if ( null != info.getType().isInterface() || info.getType().isAbstract() ) {
            return;
        }

        // we search for @JsonCreator annotation
        JConstructor creatorDefaultConstructor = null;
        JConstructor creatorConstructor = null;
        for ( JConstructor constructor : info.getType().getConstructors() ) {
            if ( constructor.getParameters().length == 0 ) {
                creatorDefaultConstructor = constructor;
                continue;
            }

            // A constructor is considered as a creator if
            // - he is annotated with JsonCreator and
            //   * all its parameters are annotated with JsonProperty
            //   * or it has only one parameter
            // - or all its parameters are annotated with JsonProperty
            boolean isAllParametersAnnotatedWithJsonProperty = isAllParametersAnnotatedWith( constructor, JsonProperty.class );
            if ( (constructor.isAnnotationPresent( JsonCreator.class ) && ((isAllParametersAnnotatedWithJsonProperty) || (constructor
                .getParameters().length == 1))) || isAllParametersAnnotatedWithJsonProperty ) {
                if ( null != creatorConstructor ) {
                    // Jackson fails with an ArrayIndexOutOfBoundsException when it's the case, let's be more flexible
                    logger.log( TreeLogger.Type.WARN, "More than one constructor annotated with @JsonCreator, " +
                        "we use " + creatorConstructor );
                    break;
                } else {
                    creatorConstructor = constructor;
                }
            }
        }

        JMethod creatorFactory = null;
        if ( null == creatorConstructor ) {
            // searching for factory method
            for ( JMethod method : info.getType().getMethods() ) {
                if ( method.isStatic() && method.isAnnotationPresent( JsonCreator.class ) && (method
                    .getParameters().length == 1 || isAllParametersAnnotatedWith( method, JsonProperty.class )) ) {
                    if ( null != creatorFactory ) {

                        // Jackson fails with an ArrayIndexOutOfBoundsException when it's the case, let's be more flexible
                        logger.log( TreeLogger.Type.WARN, "More than one factory method annotated with @JsonCreator, " +
                            "we use " + creatorFactory );
                        break;
                    } else {
                        creatorFactory = method;
                    }
                }
            }
        }

        if ( null != creatorConstructor ) {
            info.creatorMethod = Optional.<JAbstractMethod>of( creatorConstructor );
        } else if ( null != creatorFactory ) {
            info.creatorMethod = Optional.<JAbstractMethod>of( creatorFactory );
        } else if ( null != creatorDefaultConstructor ) {
            info.creatorDefaultConstructor = true;
            info.creatorMethod = Optional.<JAbstractMethod>of( creatorDefaultConstructor );
        }

        if ( info.creatorMethod.isPresent() ) {
            if ( !info.isCreatorDefaultConstructor() ) {
                if ( info.creatorMethod.get().getParameters().length == 1 && !isAllParametersAnnotatedWith( info.creatorMethod
                    .get(), JsonProperty.class ) ) {
                    // delegation constructor
                    info.creatorDelegation = true;
                } else {
                    for ( JParameter parameter : info.creatorMethod.get().getParameters() ) {
                        info.creatorParameters.put( parameter.getAnnotation( JsonProperty.class ).value(), parameter );
                    }
                }
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

    private JClassType type;

    private JClassType[] parameterizedTypes;

    /*####  Instantiation properties  ####*/
    private Optional<JAbstractMethod> creatorMethod = Optional.absent();

    private Map<String, JParameter> creatorParameters = new LinkedHashMap<String, JParameter>();

    private boolean creatorDefaultConstructor;

    private boolean creatorDelegation;

    private Optional<BeanTypeInfo> typeInfo = Optional.absent();

    /*####  Visibility properties  ####*/
    private Set<String> ignoredFields = new HashSet<String>();

    private JsonAutoDetect.Visibility fieldVisibility = JsonAutoDetect.Visibility.DEFAULT;

    private JsonAutoDetect.Visibility getterVisibility = JsonAutoDetect.Visibility.DEFAULT;

    private JsonAutoDetect.Visibility isGetterVisibility = JsonAutoDetect.Visibility.DEFAULT;

    private JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Visibility.DEFAULT;

    private JsonAutoDetect.Visibility creatorVisibility = JsonAutoDetect.Visibility.DEFAULT;

    private boolean ignoreUnknown;

    /*####  Ordering properties  ####*/
    private List<String> propertyOrderList;

    private boolean propertyOrderAlphabetic;

    /*####  Identity info  ####*/
    private Optional<BeanIdentityInfo> identityInfo = Optional.absent();

    private BeanInfo() {

    }

    public JClassType getType() {
        return type;
    }

    public JClassType[] getParameterizedTypes() {
        return parameterizedTypes;
    }

    public boolean isCreatorDefaultConstructor() {
        return creatorDefaultConstructor;
    }

    public Optional<JAbstractMethod> getCreatorMethod() {
        return creatorMethod;
    }

    public Map<String, JParameter> getCreatorParameters() {
        return creatorParameters;
    }

    public boolean isCreatorDelegation() {
        return creatorDelegation;
    }

    public Optional<BeanTypeInfo> getTypeInfo() {
        return typeInfo;
    }

    public Set<String> getIgnoredFields() {
        return ignoredFields;
    }

    private void addIgnoredField( String ignoredField ) {
        this.ignoredFields.add( ignoredField );
    }

    public JsonAutoDetect.Visibility getFieldVisibility() {
        return fieldVisibility;
    }

    public JsonAutoDetect.Visibility getGetterVisibility() {
        return getterVisibility;
    }

    public JsonAutoDetect.Visibility getIsGetterVisibility() {
        return isGetterVisibility;
    }

    public JsonAutoDetect.Visibility getSetterVisibility() {
        return setterVisibility;
    }

    public JsonAutoDetect.Visibility getCreatorVisibility() {
        return creatorVisibility;
    }

    public boolean isIgnoreUnknown() {
        return ignoreUnknown;
    }

    public List<String> getPropertyOrderList() {
        return propertyOrderList;
    }

    public boolean isPropertyOrderAlphabetic() {
        return propertyOrderAlphabetic;
    }

    public Optional<BeanIdentityInfo> getIdentityInfo() {
        return identityInfo;
    }
}
