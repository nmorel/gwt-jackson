package com.github.nmorel.gwtjackson.rebind;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

/** @author Nicolas Morel */
public class JsonMapperGenerator extends Generator
{
    @Override
    public String generate( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException
    {
        JsonMapperCreator creator = new JsonMapperCreator( logger, context );
        return creator.create(typeName);
    }
}
