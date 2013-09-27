package com.github.nmorel.gwtjackson.rebind;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/** @author Nicolas Morel */
public class JacksonTypeOracle
{
    private final TreeLogger logger;
    private final TypeOracle typeOracle;
    private final JClassType jMapType;
    private final JClassType jIterableType;
    private final JClassType jEnumSetType;
    private final Map<JClassType, BeanJsonMapperInfo> typeToMapperInfo = new HashMap<JClassType, BeanJsonMapperInfo>();

    public JacksonTypeOracle( TreeLogger logger, TypeOracle typeOracle )
    {
        this.logger = logger;
        this.typeOracle = typeOracle;

        this.jEnumSetType = typeOracle.findType( "java.util.EnumSet" );
        this.jMapType = typeOracle.findType( "java.util.Map" );
        this.jIterableType = typeOracle.findType( "java.lang.Iterable" );
    }

    public JClassType getType( String type ) throws UnableToCompleteException
    {
        try
        {
            return typeOracle.getType( type );
        }
        catch ( NotFoundException e )
        {
            logger.log( TreeLogger.ERROR, "TypeOracle could not find " + type );
            throw new UnableToCompleteException();
        }
    }

    public boolean isEnumSet( JClassType parameterizedType )
    {
        return parameterizedType.isAssignableTo( jEnumSetType );
    }

    public boolean isMap( JClassType parameterizedType )
    {
        return parameterizedType.isAssignableTo( jMapType );
    }

    public boolean isIterable( JClassType parameterizedType )
    {
        return parameterizedType.isAssignableTo( jIterableType );
    }

    public BeanJsonMapperInfo getBeanJsonMapperInfo( JClassType type )
    {
        return typeToMapperInfo.get( type );
    }

    public void addBeanJsonMapperInfo( JClassType type, BeanJsonMapperInfo info )
    {
        typeToMapperInfo.put( type, info );
    }
}
