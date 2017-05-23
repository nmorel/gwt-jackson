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

import java.lang.annotation.Annotation;
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
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.thirdparty.guava.common.base.Optional;

/**
 * <p>JacksonTypeOracle class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
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

    private final JClassType iterableType;

    private final JClassType jsoType;

    private final JClassType enumType;

    private final JClassType stringType;

    private final Map<JClassType, BeanJsonMapperInfo> typeToMapperInfo = new HashMap<JClassType, BeanJsonMapperInfo>();

    /**
     * <p>Constructor for JacksonTypeOracle.</p>
     *
     * @param logger a {@link com.google.gwt.core.ext.TreeLogger} object.
     * @param typeOracle a {@link com.google.gwt.core.ext.typeinfo.TypeOracle} object.
     */
    public JacksonTypeOracle( TreeLogger logger, TypeOracle typeOracle ) {
        this.logger = logger;
        this.typeOracle = typeOracle;

        this.objectReaderType = typeOracle.findType( ObjectReader.class.getCanonicalName() );
        this.objectWriterType = typeOracle.findType( ObjectWriter.class.getCanonicalName() );
        this.keySerializerType = typeOracle.findType( KeySerializer.class.getCanonicalName() );
        this.keyDeserializerType = typeOracle.findType( KeyDeserializer.class.getCanonicalName() );
        this.jsonSerializerType = typeOracle.findType( JsonSerializer.class.getCanonicalName() );
        this.jsonDeserializerType = typeOracle.findType( JsonDeserializer.class.getCanonicalName() );
        this.mapType = typeOracle.findType( Map.class.getCanonicalName() );
        this.iterableType = typeOracle.findType( Iterable.class.getCanonicalName() );
        this.jsoType = typeOracle.findType( JavaScriptObject.class.getCanonicalName() );
        this.enumType = typeOracle.findType( Enum.class.getCanonicalName() );
        this.stringType = typeOracle.findType( String.class.getCanonicalName() );
    }

    /**
     * <p>getType</p>
     *
     * @param type a {@link java.lang.String} object.
     * @return a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @throws com.google.gwt.core.ext.UnableToCompleteException if any.
     */
    public JClassType getType( String type ) throws UnableToCompleteException {
        try {
            return typeOracle.getType( type );
        } catch ( NotFoundException e ) {
            logger.log( TreeLogger.ERROR, "TypeOracle could not find " + type );
            throw new UnableToCompleteException();
        }
    }

    /**
     * <p>isObjectReader</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @return a boolean.
     */
    public boolean isObjectReader( JClassType type ) {
        return type.isAssignableTo( objectReaderType );
    }

    /**
     * <p>isObjectWriter</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @return a boolean.
     */
    public boolean isObjectWriter( JClassType type ) {
        return type.isAssignableTo( objectWriterType );
    }

    /**
     * <p>isMap</p>
     *
     * @param parameterizedType a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @return a boolean.
     */
    public boolean isMap( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( mapType );
    }

    /**
     * <p>isIterable</p>
     *
     * @param parameterizedType a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @return a boolean.
     */
    public boolean isIterable( JClassType parameterizedType ) {
        return parameterizedType.isAssignableTo( iterableType );
    }

    /**
     * <p>isKeySerializer</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @return a boolean.
     */
    public boolean isKeySerializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( keySerializerType );
    }

    /**
     * <p>isKeyDeserializer</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @return a boolean.
     */
    public boolean isKeyDeserializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( keyDeserializerType );
    }

    /**
     * <p>isJsonSerializer</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @return a boolean.
     */
    public boolean isJsonSerializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jsonSerializerType );
    }

    /**
     * <p>isJsonDeserializer</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @return a boolean.
     */
    public boolean isJsonDeserializer( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jsonDeserializerType );
    }

    /**
     * <p>isJavaScriptObject</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @return a boolean.
     */
    public boolean isJavaScriptObject( JType type ) {
        return null != type.isClass() && type.isClass().isAssignableTo( jsoType );
    }

    /**
     * <p>getJavaScriptObject</p>
     *
     * @return a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     */
    public JClassType getJavaScriptObject() {
        return jsoType;
    }

    /**
     * <p>getJavaLangObject</p>
     *
     * @return a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     */
    public JClassType getJavaLangObject() {
        return typeOracle.getJavaLangObject();
    }

    /**
     * <p>getString</p>
     *
     * @return a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     */
    public JClassType getString() {
        return stringType;
    }

    /**
     * <p>isEnumSupertype</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @return a boolean.
     */
    public boolean isEnumSupertype( JType type ) {
        return Enum.class.getName().equals( type.getQualifiedSourceName() );
    }

    /**
     * <p>isEnum</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @return a boolean.
     */
    public boolean isEnum( JType type ) {
        return null != type.isEnum();
    }

    /**
     * <p>getEnum</p>
     *
     * @return a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     */
    public JClassType getEnum() {
        return enumType;
    }

    /**
     * <p>getBeanJsonMapperInfo</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @return a {@link com.github.nmorel.gwtjackson.rebind.BeanJsonMapperInfo} object.
     */
    public BeanJsonMapperInfo getBeanJsonMapperInfo( JClassType type ) {
        return typeToMapperInfo.get( type );
    }

    /**
     * <p>addBeanJsonMapperInfo</p>
     *
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @param info a {@link com.github.nmorel.gwtjackson.rebind.BeanJsonMapperInfo} object.
     */
    public void addBeanJsonMapperInfo( JClassType type, BeanJsonMapperInfo info ) {
        typeToMapperInfo.put( type, info );
    }

    /**
     * <p>replaceType</p>
     *
     * @param logger a {@link com.google.gwt.core.ext.TreeLogger} object.
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @param deserializeAs a {@link java.lang.annotation.Annotation} object.
     * @return a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @throws com.google.gwt.core.ext.UnableToCompleteException if any.
     */
    public JType replaceType( TreeLogger logger, JType type, Annotation deserializeAs ) throws UnableToCompleteException {
        JClassType classType = type.isClassOrInterface();
        if ( null == classType ) {
            return type;
        }

        Optional<JClassType> typeAs = getClassFromJsonDeserializeAnnotation( logger, deserializeAs, "as" );
        Optional<JClassType> keyAs = getClassFromJsonDeserializeAnnotation( logger, deserializeAs, "keyAs" );
        Optional<JClassType> contentAs = getClassFromJsonDeserializeAnnotation( logger, deserializeAs, "contentAs" );

        if ( !typeAs.isPresent() && !keyAs.isPresent() && !contentAs.isPresent() ) {
            return type;
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            if ( contentAs.isPresent() ) {
                return typeOracle.getArrayType( contentAs.get() );
            } else if ( typeAs.isPresent() ) {
                return typeOracle.getArrayType( typeAs.get() );
            } else {
                return classType;
            }
        }

        JParameterizedType parameterizedType = type.isParameterized();
        if ( null != parameterizedType ) {
            JGenericType genericType;
            if ( typeAs.isPresent() ) {
                genericType = typeAs.get().isGenericType();
            } else {
                genericType = parameterizedType.getBaseType();
            }

            if ( !keyAs.isPresent() && !contentAs.isPresent() ) {
                return typeOracle.getParameterizedType( genericType, parameterizedType.getTypeArgs() );
            } else if ( contentAs.isPresent() && isIterable( parameterizedType ) ) {
                return typeOracle.getParameterizedType( genericType, new JClassType[]{contentAs.get()} );
            } else if ( isMap( parameterizedType ) ) {
                JClassType key;
                if ( keyAs.isPresent() ) {
                    key = keyAs.get();
                } else {
                    key = parameterizedType.getTypeArgs()[0];
                }

                JClassType content;
                if ( contentAs.isPresent() ) {
                    content = contentAs.get();
                } else {
                    content = parameterizedType.getTypeArgs()[1];
                }

                return typeOracle.getParameterizedType( genericType, new JClassType[]{key, content} );
            }
        }

        if ( typeAs.isPresent() ) {
            return typeAs.get();
        }

        return type;
    }

    /**
     * <p>getClassFromJsonDeserializeAnnotation</p>
     *
     * @param logger a {@link com.google.gwt.core.ext.TreeLogger} object.
     * @param annotation a {@link java.lang.annotation.Annotation} object.
     * @param name a {@link java.lang.String} object.
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<JClassType> getClassFromJsonDeserializeAnnotation( TreeLogger logger, Annotation annotation, String name ) {
        try {
            Class asClass = (Class) annotation.getClass().getDeclaredMethod( name ).invoke( annotation );
            if ( asClass != Void.class ) {
                return Optional.fromNullable( getType( asClass.getCanonicalName() ) );
            }
        } catch ( Exception e ) {
            logger.log( Type.INFO, "Cannot find method " + name + " on JsonDeserialize annotation", e );
        }
        return Optional.absent();
    }
}
