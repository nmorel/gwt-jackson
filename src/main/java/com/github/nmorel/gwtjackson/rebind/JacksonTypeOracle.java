package com.github.nmorel.gwtjackson.rebind;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/** @author Nicolas Morel */
public class JacksonTypeOracle
{
    private final TreeLogger logger;
    private final TypeOracle typeOracle;
    private final JClassType jSetType;
    private final JClassType jListType;
    private final JClassType jCollectionType;
    private final JClassType jIterableType;

    public JacksonTypeOracle( TreeLogger logger, TypeOracle typeOracle )
    {
        this.logger = logger;
        this.typeOracle = typeOracle;

        this.jSetType = typeOracle.findType( "java.util.Set" );
        this.jListType = typeOracle.findType( "java.util.List" );
        this.jCollectionType = typeOracle.findType( "java.util.Collection" );
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

    public boolean isSet( JParameterizedType parameterizedType )
    {
        return parameterizedType.isAssignableTo( jSetType );
    }

    public boolean isList( JParameterizedType parameterizedType )
    {
        return parameterizedType.isAssignableTo( jListType );
    }

    public boolean isCollection( JParameterizedType parameterizedType )
    {
        return parameterizedType.isAssignableTo( jCollectionType );
    }

    public boolean isIterable( JParameterizedType parameterizedType )
    {
        return parameterizedType.isAssignableTo( jIterableType );
    }
}
