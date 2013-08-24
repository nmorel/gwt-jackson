package com.github.nmorel.gwtjackson.rebind;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/** @author Nicolas Morel */
public class JsonMapperGenerator extends Generator
{
    @Override
    public String generate( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException
    {
        TypeOracle typeOracle = context.getTypeOracle();
        JClassType typeClass;
        try
        {
            typeClass = typeOracle.getType( typeName );
        }
        catch ( NotFoundException e )
        {
            logger.log( TreeLogger.ERROR, "TypeOracle could not find " + typeName );
            throw new UnableToCompleteException();
        }

        JsonMapperCreator creator = new JsonMapperCreator( logger, context, typeClass, typeOracle );
        return creator.create();
    }
}
