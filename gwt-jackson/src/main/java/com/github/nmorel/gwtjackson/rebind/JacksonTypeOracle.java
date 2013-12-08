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

    private final JClassType jObjectReaderType;

    private final JClassType jObjectWriterType;

    private final JClassType jKeySerializerType;

    private final JClassType jKeyDeserializerType;

    private final JClassType jJsonSerializerType;

    private final JClassType jJsonDeserializerType;

    private final JClassType jMapType;

    private final JClassType jEnumMapType;

    private final JClassType jIterableType;

    private final JClassType jEnumSetType;

    private final Map<JClassType, BeanJsonMapperInfo> typeToMapperInfo = new HashMap<JClassType, BeanJsonMapperInfo>();

    public JacksonTypeOracle( TreeLogger logger, TypeOracle typeOracle ) {
        this.logger = logger;
        this.typeOracle = typeOracle;

        this.jObjectReaderType = typeOracle.findType( ObjectReader.class.getCanonicalName() );
        this.jObjectWriterType = typeOracle.findType( ObjectWriter.class.getCanonicalName() );
        this.jKeySerializerType = typeOracle.findType( KeySerializer.class.getCanonicalName() );
        this.jKeyDeserializerType = typeOracle.findType( KeyDeserializer.class.getCanonicalName() );
        this.jJsonSerializerType = typeOracle.findType( JsonSerializer.class.getCanonicalName() );
        this.jJsonDeserializerType = typeOracle.findType( JsonDeserializer.class.getCanonicalName() );
        this.jEnumSetType = typeOracle.findType( EnumSet.class.getCanonicalName() );
        this.jMapType = typeOracle.findType( Map.class.getCanonicalName() );
        this.jEnumMapType = typeOracle.findType( EnumMap.class.getCanonicalName() );
        this.jIterableType = typeOracle.findType( Iterable.class.getCanonicalName() );
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
        return type.isAssignableTo( jObjectReaderType );
    }

    public boolean isObjectWriter( JClassType type ) {
        return type.isAssignableTo( jObjectWriterType );
    }

    public boolean isEnumSet( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( jEnumSetType );
    }

    public boolean isEnumMap( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( jEnumMapType );
    }

    public boolean isMap( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( jMapType );
    }

    public boolean isIterable( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( jIterableType );
    }

    public boolean isObject( JType type ) {
        return typeOracle.getJavaLangObject().equals( type );
    }

    public boolean isKeySerializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jKeySerializerType );
    }

    public boolean isKeyDeserializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jKeyDeserializerType );
    }

    public boolean isJsonSerializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jJsonSerializerType );
    }

    public boolean isJsonDeserializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jJsonDeserializerType );
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
