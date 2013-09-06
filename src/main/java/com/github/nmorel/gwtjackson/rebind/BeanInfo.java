package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;

/** @author Nicolas Morel */
public final class BeanInfo
{
    public static BeanInfo process( TreeLogger logger, JClassType beanType, String qualifiedMapperClassName, String mapperClassSimpleName )
    {
        BeanInfo result = new BeanInfo();
        result.type = beanType;
        result.qualifiedMapperClassName = qualifiedMapperClassName;
        result.mapperClassSimpleName = mapperClassSimpleName;
        result.superclass = beanType
            .getSubtypes().length == 0 ? AbstractJsonMapperCreator.ABSTRACT_BEAN_JSON_MAPPER_CLASS : AbstractJsonMapperCreator
            .ABSTRACT_SUPERCLASS_JSON_MAPPER_CLASS;
        result.hasSubtypes = beanType.getSubtypes().length > 0;
        determineInstanceCreator( logger, result );

        result.typeInfo = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonTypeInfo.class );

        JsonIgnoreType jsonIgnoreType = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonIgnoreType.class );
        result.ignoreAllProperties = null != jsonIgnoreType && jsonIgnoreType.value();

        JsonAutoDetect jsonAutoDetect = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonAutoDetect.class );
        if ( null != jsonAutoDetect )
        {
            result.creatorVisibility = jsonAutoDetect.creatorVisibility();
            result.fieldVisibility = jsonAutoDetect.fieldVisibility();
            result.getterVisibility = jsonAutoDetect.getterVisibility();
            result.isGetterVisibility = jsonAutoDetect.isGetterVisibility();
            result.setterVisibility = jsonAutoDetect.setterVisibility();
        }

        JsonIgnoreProperties jsonIgnoreProperties = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonIgnoreProperties.class );
        if ( null != jsonIgnoreProperties )
        {
            for ( String ignoreProperty : jsonIgnoreProperties.value() )
            {
                result.addIgnoredField( ignoreProperty );
            }
            result.ignoreUnknown = jsonIgnoreProperties.ignoreUnknown();
        }

        JsonPropertyOrder jsonPropertyOrder = findFirstEncounteredAnnotationsOnAllHierarchy( beanType, JsonPropertyOrder.class );
        if ( null == jsonPropertyOrder )
        {
            result.propertyOrderList = Collections.emptyList();
        }
        else
        {
            result.propertyOrderList = Arrays.asList( jsonPropertyOrder.value() );
            result.propertyOrderAlphabetic = jsonPropertyOrder.alphabetic();
        }
        return result;
    }

    /**
     * Look for the method to create a new instance of the bean. If none are found or the bean is abstract or an interface, we considered it
     * as non instantiable.
     *
     * @param logger logger
     * @param info current bean info
     */
    private static void determineInstanceCreator( TreeLogger logger, BeanInfo info )
    {
        if ( null != info.getType().isInterface() || info.getType().isAbstract() )
        {
            info.instantiable = false;
        }
        else
        {
            // we search for @JsonCreator annotation
            JConstructor creatorDefaultConstructor = null;
            JConstructor creatorConstructor = null;
            for ( JConstructor constructor : info.getType().getConstructors() )
            {
                if ( constructor.getParameters().length == 0 )
                {
                    creatorDefaultConstructor = constructor;
                }

                // A constructor is considered as a creator if
                // - he is annotated with JsonCreator and
                //   * all its parameters are annotated with JsonProperty
                //   * or it has only one parameter
                // - or all its parameters are annotated with JsonProperty
                boolean isAllParametersAnnotatedWithJsonProperty = isAllParametersAnnotatedWith( constructor, JsonProperty.class );
                if ( (constructor.isAnnotationPresent( JsonCreator.class ) && ((isAllParametersAnnotatedWithJsonProperty) || (constructor
                    .getParameters().length == 1))) || isAllParametersAnnotatedWithJsonProperty )
                {
                    if ( null != creatorConstructor )
                    {
                        // Jackson fails with an ArrayIndexOutOfBoundsException when it's the case, let's be more flexible
                        logger.log( TreeLogger.Type.WARN, "More than one constructor annotated with @JsonCreator, " +
                            "we use " + creatorConstructor );
                        break;
                    }
                    else
                    {
                        creatorConstructor = constructor;
                    }
                }
            }

            JMethod creatorFactory = null;
            if ( null == creatorConstructor )
            {
                // searching for factory method
                for ( JMethod method : info.getType().getMethods() )
                {
                    if ( method.isStatic() && method.isAnnotationPresent( JsonCreator.class ) && (method
                        .getParameters().length == 1 || isAllParametersAnnotatedWith( method, JsonProperty.class )) )
                    {
                        if ( null != creatorFactory )
                        {

                            // Jackson fails with an ArrayIndexOutOfBoundsException when it's the case, let's be more flexible
                            logger.log( TreeLogger.Type.WARN, "More than one factory method annotated with @JsonCreator, " +
                                "we use " + creatorFactory );
                            break;
                        }
                        else
                        {
                            creatorFactory = method;
                        }
                    }
                }
            }

            info.instantiable = true;
            if ( null != creatorConstructor )
            {
                info.creatorConstructor = creatorConstructor;
            }
            else if ( null != creatorFactory )
            {
                info.creatorFactory = creatorFactory;
            }
            else if ( null != creatorDefaultConstructor )
            {
                info.creatorDefaultConstructor = creatorDefaultConstructor;
            }
            else
            {
                info.instantiable = false;
            }
        }

        if ( info.instantiable )
        {
            info.instanceBuilderSimpleName = info.getType().getSimpleSourceName() + "InstanceBuilder";
            info.instanceBuilderQualifiedName = info.qualifiedMapperClassName + "." + info.instanceBuilderSimpleName;
        }
        else
        {
            info.instanceBuilderQualifiedName = AbstractJsonMapperCreator.INSTANCE_BUILDER_CLASS + "<" + info.getType()
                .getParameterizedQualifiedSourceName() + ">";
        }
    }

    private static <T extends Annotation> boolean isAllParametersAnnotatedWith( JAbstractMethod method, Class<T> annotation )
    {
        for ( JParameter parameter : method.getParameters() )
        {
            if ( !parameter.isAnnotationPresent( annotation ) )
            {
                return false;
            }
        }

        return true;
    }

    private static <T extends Annotation> T findFirstEncounteredAnnotationsOnAllHierarchy( JClassType type, Class<T> annotation )
    {
        JClassType currentType = type;
        while ( null != currentType && !currentType.getQualifiedSourceName().equals( "java.lang.Object" ) )
        {
            if ( currentType.isAnnotationPresent( annotation ) )
            {
                return currentType.getAnnotation( annotation );
            }
            for ( JClassType interf : currentType.getImplementedInterfaces() )
            {
                T annot = findFirstEncounteredAnnotationsOnAllHierarchy( interf, annotation );
                if ( null != annot )
                {
                    return annot;
                }
            }
            currentType = currentType.getSuperclass();
        }
        return null;
    }

    private JClassType type;
    private String qualifiedMapperClassName;
    private String mapperClassSimpleName;
    private String superclass;
    private String instanceBuilderQualifiedName;
    private String instanceBuilderSimpleName;
    private boolean hasSubtypes;
    private boolean instantiable;
    private JConstructor creatorConstructor;
    private JMethod creatorFactory;
    private JConstructor creatorDefaultConstructor;
    private JsonTypeInfo typeInfo;
    private boolean ignoreAllProperties;
    private Set<String> ignoredFields = new HashSet<String>();
    private JsonAutoDetect.Visibility fieldVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility getterVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility isGetterVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private JsonAutoDetect.Visibility creatorVisibility = JsonAutoDetect.Visibility.DEFAULT;
    private boolean ignoreUnknown;
    private List<String> propertyOrderList;
    private boolean propertyOrderAlphabetic;

    private BeanInfo()
    {

    }

    public JClassType getType()
    {
        return type;
    }

    public String getQualifiedMapperClassName()
    {
        return qualifiedMapperClassName;
    }

    public String getMapperClassSimpleName()
    {
        return mapperClassSimpleName;
    }

    public String getSuperclass()
    {
        return superclass;
    }

    public String getInstanceBuilderQualifiedName()
    {
        return instanceBuilderQualifiedName;
    }

    public String getInstanceBuilderSimpleName()
    {
        return instanceBuilderSimpleName;
    }

    public boolean isHasSubtypes()
    {
        return hasSubtypes;
    }

    public boolean isInstantiable()
    {
        return instantiable;
    }

    public JConstructor getCreatorConstructor()
    {
        return creatorConstructor;
    }

    public JMethod getCreatorFactory()
    {
        return creatorFactory;
    }

    public JConstructor getCreatorDefaultConstructor()
    {
        return creatorDefaultConstructor;
    }

    public JsonTypeInfo getTypeInfo()
    {
        return typeInfo;
    }

    public boolean isIgnoreAllProperties()
    {
        return ignoreAllProperties;
    }

    public Set<String> getIgnoredFields()
    {
        return ignoredFields;
    }

    private void addIgnoredField( String ignoredField )
    {
        this.ignoredFields.add( ignoredField );
    }

    public JsonAutoDetect.Visibility getFieldVisibility()
    {
        return fieldVisibility;
    }

    public JsonAutoDetect.Visibility getGetterVisibility()
    {
        return getterVisibility;
    }

    public JsonAutoDetect.Visibility getIsGetterVisibility()
    {
        return isGetterVisibility;
    }

    public JsonAutoDetect.Visibility getSetterVisibility()
    {
        return setterVisibility;
    }

    public JsonAutoDetect.Visibility getCreatorVisibility()
    {
        return creatorVisibility;
    }

    public boolean isIgnoreUnknown()
    {
        return ignoreUnknown;
    }

    public List<String> getPropertyOrderList()
    {
        return propertyOrderList;
    }

    public boolean isPropertyOrderAlphabetic()
    {
        return propertyOrderAlphabetic;
    }
}
