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

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/**
 * @author Nicolas Morel
 */
public class JacksonTypeOracle {

    private final TreeLogger logger;

    private final TypeOracle typeOracle;

    private final JClassType objectReaderType;

    private final JClassType objectWriterType;

    private final JClassType keySerializerType;

    private final JClassType keyDeserializerType;

    private final JClassType jsonSerializerType;

    private final JClassType jsonDeserializerType;

    private final JClassType mapType;

    private final JClassType enumMapType;

    private final JClassType iterableType;

    private final JClassType enumSetType;

    private final JClassType jsoType;

    private final Map<JClassType, BeanJsonMapperInfo> typeToMapperInfo = new HashMap<JClassType, BeanJsonMapperInfo>();

    public JacksonTypeOracle( TreeLogger logger, TypeOracle typeOracle ) {
        this.logger = logger;
        this.typeOracle = typeOracle;

        this.objectReaderType = typeOracle.findType( ObjectReader.class.getCanonicalName() );
        this.objectWriterType = typeOracle.findType( ObjectWriter.class.getCanonicalName() );
        this.keySerializerType = typeOracle.findType( KeySerializer.class.getCanonicalName() );
        this.keyDeserializerType = typeOracle.findType( KeyDeserializer.class.getCanonicalName() );
        this.jsonSerializerType = typeOracle.findType( JsonSerializer.class.getCanonicalName() );
        this.jsonDeserializerType = typeOracle.findType( JsonDeserializer.class.getCanonicalName() );
        this.enumSetType = typeOracle.findType( EnumSet.class.getCanonicalName() );
        this.mapType = typeOracle.findType( Map.class.getCanonicalName() );
        this.enumMapType = typeOracle.findType( EnumMap.class.getCanonicalName() );
        this.iterableType = typeOracle.findType( Iterable.class.getCanonicalName() );
        this.jsoType = typeOracle.findType( JavaScriptObject.class.getCanonicalName() );
    }

    public JClassType getType( String type ) throws UnableToCompleteException {
        try {
            return typeOracle.getType( type );
        } catch ( NotFoundException e ) {
            logger.log( TreeLogger.ERROR, "TypeOracle could not find " + type );
            throw new UnableToCompleteException();
        }
    }

    public boolean isObjectReader( JClassType type ) {
        return type.isAssignableTo( objectReaderType );
    }

    public boolean isObjectWriter( JClassType type ) {
        return type.isAssignableTo( objectWriterType );
    }

    public boolean isEnumSet( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( enumSetType );
    }

    public boolean isEnumMap( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( enumMapType );
    }

    public boolean isMap( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( mapType );
    }

    public boolean isIterable( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( iterableType );
    }

    public boolean isObject( JType type ) {
        return typeOracle.getJavaLangObject().equals( type );
    }

    public boolean isKeySerializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( keySerializerType );
    }

    public boolean isKeyDeserializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( keyDeserializerType );
    }

    public boolean isJsonSerializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jsonSerializerType );
    }

    public boolean isJsonDeserializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jsonDeserializerType );
    }

    public boolean isJavaScriptObject( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jsoType );
    }

    public JClassType getJavaScriptObject() {
        return jsoType;
    }

    public BeanJsonMapperInfo getBeanJsonMapperInfo( JClassType type ) {
        return typeToMapperInfo.get( type );
    }

    public void addBeanJsonMapperInfo( JClassType type, BeanJsonMapperInfo info ) {
        typeToMapperInfo.put( type, info );
    }

    public JClassType findGenericType( JParameterizedType parameterizedType ) {
        return typeOracle.findType( parameterizedType.getQualifiedSourceName() );
    }
}
