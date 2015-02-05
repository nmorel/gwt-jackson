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

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * @author Nicolas Morel
 */
public class ObjectMapperGenerator extends Generator {

    @Override
    public final String generate( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException {
        JacksonTypeOracle typeOracle = new JacksonTypeOracle( logger, context.getTypeOracle() );
        JClassType rootMapperClass = typeOracle.getType( typeName );
        RebindConfiguration configuration = new RebindConfiguration( logger, context, typeOracle, rootMapperClass );
        ObjectMapperCreator creator = new ObjectMapperCreator( logger, context, configuration, typeOracle );
        return creator.create( rootMapperClass );
    }
}
