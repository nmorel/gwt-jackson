package com.github.nmorel.gwtjackson.rebind;

import java.util.UUID;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;

/** @author Nicolas Morel */
public class BeanIdentityInfo
{
    private String propertyName;
    private boolean alwaysAsId;
    private Class<? extends ObjectIdGenerator<?>> generator;
    private Class<?> scope;
    private PropertyInfo property;
    private JType type;

    public BeanIdentityInfo( String propertyName, boolean alwaysAsId, Class<? extends ObjectIdGenerator<?>> generator, Class<?> scope,
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
