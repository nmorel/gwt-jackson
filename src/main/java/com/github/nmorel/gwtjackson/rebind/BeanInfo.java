package com.github.nmorel.gwtjackson.rebind;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gwt.core.ext.typeinfo.JClassType;

/** @author Nicolas Morel */
public final class BeanInfo
{
    public static BeanInfo process( JClassType beanType, String qualifiedMapperClassName, String mapperClassSimpleName )
    {
        BeanInfo result = new BeanInfo();
        result.type = beanType;
        result.qualifiedMapperClassName = qualifiedMapperClassName;
        result.mapperClassSimpleName = mapperClassSimpleName;
        result.superclass = beanType
            .getSubtypes().length == 0 ? AbstractJsonMapperCreator.ABSTRACT_BEAN_JSON_MAPPER_CLASS : AbstractJsonMapperCreator
            .ABSTRACT_SUPERCLASS_JSON_MAPPER_CLASS;
        result.hasSubtypes = beanType.getSubtypes().length > 0;
        result.instantiable = beanType.isDefaultInstantiable();
        if ( result.instantiable )
        {
            result.instanceBuilderSimpleName = beanType.getSimpleSourceName() + "InstanceBuilder";
            result.instanceBuilderQualifiedName = qualifiedMapperClassName + "." + result.instanceBuilderSimpleName;
        }
        else
        {
            result.instanceBuilderQualifiedName = AbstractJsonMapperCreator.INSTANCE_BUILDER_CLASS + "<" + beanType
                .getParameterizedQualifiedSourceName() + ">";
        }

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
