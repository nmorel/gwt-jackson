package com.github.nmorel.gwtjackson.rebind;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.extractBeanType;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findAnnotationOnAnyAccessor;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

/** @author Nicolas Morel */
public class BeanIdentityInfo
{
    public static BeanIdentityInfo process( TreeLogger logger, JacksonTypeOracle typeOracle,
                                            JClassType type ) throws UnableToCompleteException
    {
        return process( logger, typeOracle, type, null, false );
    }

    public static BeanIdentityInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, JType type,
                                            FieldAccessors fieldAccessors ) throws UnableToCompleteException
    {
        JClassType classType = extractBeanType( typeOracle, type );
        if ( null == classType )
        {
            return null;
        }
        else
        {
            return process( logger, typeOracle, classType, fieldAccessors, true );
        }
    }

    private static BeanIdentityInfo process( TreeLogger logger, JacksonTypeOracle typeOracle, JClassType type,
                                             FieldAccessors fieldAccessors, boolean property ) throws UnableToCompleteException
    {
        JsonIdentityInfo jsonIdentityInfo = null;
        JsonIdentityReference jsonIdentityReference = null;
        if ( property )
        {
            jsonIdentityInfo = findAnnotationOnAnyAccessor( fieldAccessors, JsonIdentityInfo.class );
            jsonIdentityReference = findAnnotationOnAnyAccessor( fieldAccessors, JsonIdentityReference.class );
            if ( null == jsonIdentityInfo && null == jsonIdentityReference )
            {
                // no override on field
                return null;
            }
        }

        if ( null == jsonIdentityInfo )
        {
            jsonIdentityInfo = findFirstEncounteredAnnotationsOnAllHierarchy( type, JsonIdentityInfo.class );
        }

        if ( null != jsonIdentityInfo && ObjectIdGenerators.None.class != jsonIdentityInfo.generator() )
        {
            if ( null == jsonIdentityReference )
            {
                jsonIdentityReference = findFirstEncounteredAnnotationsOnAllHierarchy( type, JsonIdentityReference.class );
            }
            return new BeanIdentityInfo( jsonIdentityInfo.property(), null != jsonIdentityReference && jsonIdentityReference
                .alwaysAsId(), jsonIdentityInfo.generator(), jsonIdentityInfo.scope(), typeOracle );
        }
        return null;
    }

    private String propertyName;
    private boolean alwaysAsId;
    private Class<? extends ObjectIdGenerator<?>> generator;
    private Class<?> scope;
    private PropertyInfo property;
    private JType type;

    private BeanIdentityInfo( String propertyName, boolean alwaysAsId, Class<? extends ObjectIdGenerator<?>> generator, Class<?> scope,
                              JacksonTypeOracle typeOracle ) throws UnableToCompleteException
    {
        this.propertyName = propertyName;
        this.alwaysAsId = alwaysAsId;
        this.generator = generator;
        this.scope = scope;

        if ( !isIdABeanProperty() )
        {
            if ( IntSequenceGenerator.class == generator )
            {
                type = typeOracle.getType( Integer.class.getName() );
            }
            else if ( UUIDGenerator.class == generator )
            {
                type = typeOracle.getType( UUID.class.getName() );
            }
            else
            {
                JClassType generatorType = typeOracle.getType( generator.getCanonicalName() );
                JClassType objectIdGeneratorType = generatorType.getSuperclass();
                while ( !objectIdGeneratorType.getQualifiedSourceName().equals( ObjectIdGenerator.class.getName() ) )
                {
                    objectIdGeneratorType = objectIdGeneratorType.getSuperclass();
                }
                type = objectIdGeneratorType.isParameterized().getTypeArgs()[0];
            }
        }
    }

    public boolean isIdABeanProperty()
    {
        return generator.isAssignableFrom( PropertyGenerator.class );
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public boolean isAlwaysAsId()
    {
        return alwaysAsId;
    }

    public Class<? extends ObjectIdGenerator<?>> getGenerator()
    {
        return generator;
    }

    public Class<?> getScope()
    {
        return scope;
    }

    public void setProperty( PropertyInfo property )
    {
        this.property = property;
        this.type = property.getType();
    }

    public PropertyInfo getProperty()
    {
        return property;
    }

    public JType getType()
    {
        return type;
    }
}
