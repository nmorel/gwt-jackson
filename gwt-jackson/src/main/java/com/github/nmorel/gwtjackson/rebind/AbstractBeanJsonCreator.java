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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.PropertyIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.ObjectIdSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.PropertyIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.rebind.property.PropertyAccessors;
import com.github.nmorel.gwtjackson.rebind.property.PropertyParser;
import com.github.nmorel.gwtjackson.rebind.type.JMapperType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.google.gwt.user.rebind.SourceWriter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.QUOTED_FUNCTION;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonCreator extends AbstractCreator {

    protected static class TypeParameters {

        private final List<String> typeParameterMapperNames;

        private final String joinedTypeParameterMappersWithType;

        public TypeParameters( List<String> typeParameterMapperNames, String joinedTypeParameterMappersWithType ) {
            this.typeParameterMapperNames = typeParameterMapperNames;
            this.joinedTypeParameterMappersWithType = joinedTypeParameterMappersWithType;
        }

        public List<String> getTypeParameterMapperNames() {
            return typeParameterMapperNames;
        }

        public String getJoinedTypeParameterMappersWithType() {
            return joinedTypeParameterMappersWithType;
        }
    }

    protected static final String TYPE_PARAMETER_PREFIX = "p_";

    protected static final String ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" +
            ".AbstractBeanJsonDeserializer";

    protected static final String ABSTRACT_BEAN_JSON_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean" + "" +
            ".AbstractBeanJsonSerializer";

    protected static final String TYPE_DESERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" +
            ".TypeDeserializationInfo";

    protected static final String TYPE_SERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean" + "" +
            ".TypeSerializationInfo";

    protected BeanJsonMapperInfo mapperInfo;

    public AbstractBeanJsonCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration,
                                    JacksonTypeOracle typeOracle ) {
        super( logger, context, configuration, typeOracle );
    }

    /**
     * Creates an implementation of {@link AbstractBeanJsonSerializer} for the type given in
     * parameter
     *
     * @param beanType type of the bean
     *
     * @return the fully qualified name of the created class
     * @throws com.google.gwt.core.ext.UnableToCompleteException
     */
    public String create( JClassType beanType ) throws UnableToCompleteException {

        mapperInfo = typeOracle.getBeanJsonMapperInfo( beanType );

        String packageName = beanType.getPackage().getName();

        if ( null == mapperInfo ) {

            // we concatenate the name of all the enclosing class
            StringBuilder builder = new StringBuilder( beanType.getSimpleSourceName() );
            JClassType enclosingType = beanType.getEnclosingType();
            while ( null != enclosingType ) {
                builder.insert( 0, enclosingType.getSimpleSourceName() + "_" );
                enclosingType = enclosingType.getEnclosingType();
            }

            // if the type is specific to the mapper, we concatenate the name and hash of the mapper to it
            if ( configuration.isSpecificToMapper( beanType ) ) {
                JClassType rootMapperClass = configuration.getRootMapperClass();
                builder.insert( 0, '_' ).insert( 0, configuration.getRootMapperHash() ).insert( 0, '_' ).insert( 0, rootMapperClass
                        .getSimpleSourceName() );
            }

            String simpleSerializerClassName = builder.toString() + "BeanJsonSerializerImpl";
            String qualifiedSerializerClassName = packageName + "." + simpleSerializerClassName;
            String simpleDeserializerClassName = builder.toString() + "BeanJsonDeserializerImpl";
            String qualifiedDeserializerClassName = packageName + "." + simpleDeserializerClassName;

            mapperInfo = new BeanJsonMapperInfo( beanType, qualifiedSerializerClassName, simpleSerializerClassName,
                    qualifiedDeserializerClassName, simpleDeserializerClassName );
        }

        PrintWriter printWriter = getPrintWriter( packageName, getSimpleClassName() );
        // the class already exists, no need to continue
        if ( printWriter == null ) {
            return getQualifiedClassName();
        }

        if ( null == mapperInfo.getBeanInfo() ) {
            // retrieve the informations on the beans and its properties
            BeanInfo info = BeanInfo.process( logger, typeOracle, configuration, mapperInfo );
            mapperInfo.setBeanInfo( info );

            Map<String, PropertyInfo> properties = findAllProperties( info );
            mapperInfo.setProperties( properties );

            typeOracle.addBeanJsonMapperInfo( beanType, mapperInfo );
        }

        String parameterizedTypes = beanType.getParameterizedQualifiedSourceName();

        SourceWriter source = getSourceWriter( printWriter, packageName, getSimpleClassName() + getGenericClassBoundedParameters(),
                getSuperclass() + "<" +
                        parameterizedTypes + ">" );

        writeClassBody( source, mapperInfo.getBeanInfo(), mapperInfo.getProperties() );

        source.println();
        source.commit( logger );

        return getQualifiedClassName();
    }

    protected abstract boolean isSerializer();

    protected String getSimpleClassName() {
        if ( isSerializer() ) {
            return mapperInfo.getSimpleSerializerClassName();
        } else {
            return mapperInfo.getSimpleDeserializerClassName();
        }
    }

    protected String getQualifiedClassName() {
        if ( isSerializer() ) {
            return mapperInfo.getQualifiedSerializerClassName();
        } else {
            return mapperInfo.getQualifiedDeserializerClassName();
        }
    }

    protected String getGenericClassBoundedParameters() {
        return mapperInfo.getGenericClassBoundedParameters();
    }

    protected String getSuperclass() {
        if ( isSerializer() ) {
            return ABSTRACT_BEAN_JSON_SERIALIZER_CLASS;
        } else {
            return ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS;
        }
    }

    protected abstract void writeClassBody( SourceWriter source, BeanInfo info, Map<String,
            PropertyInfo> properties ) throws UnableToCompleteException;

    private Map<String, PropertyInfo> findAllProperties( BeanInfo info ) throws UnableToCompleteException {
        Map<String, PropertyInfo> result = new LinkedHashMap<String, PropertyInfo>();
        if ( null != info.getType().isInterface() || info.getType().isAbstract() ) {
            // no properties on interface and abstract class
            return result;
        }

        ImmutableMap<String, PropertyAccessors> fieldsMap = PropertyParser.findPropertyAccessors( configuration, logger, info );

        // Processing all the properties accessible via field, getter or setter
        Map<String, PropertyInfo> propertiesMap = new LinkedHashMap<String, PropertyInfo>();
        for ( PropertyAccessors field : fieldsMap.values() ) {
            PropertyInfo property = PropertyInfo.process( logger, typeOracle, configuration, field, mapperInfo );
            if ( !property.isVisible() ) {
                logger.log( TreeLogger.Type.DEBUG, "Field " + field.getPropertyName() + " of type " + info.getType() + " is not visible" );
            } else {
                propertiesMap.put( property.getPropertyName(), property );
            }
        }

        // we first add the properties defined in order
        for ( String orderedProperty : info.getPropertyOrderList() ) {
            // we remove the entry to have the map with only properties with natural or alphabetic order
            PropertyInfo property = propertiesMap.remove( orderedProperty );
            if ( null != property ) {
                result.put( property.getPropertyName(), property );
            }
        }

        // if the user asked for an alphabetic order, we sort the rest of the properties
        if ( info.isPropertyOrderAlphabetic() ) {
            List<Map.Entry<String, PropertyInfo>> entries = new ArrayList<Map.Entry<String, PropertyInfo>>( propertiesMap.entrySet() );
            Collections.sort( entries, new Comparator<Map.Entry<String, PropertyInfo>>() {
                public int compare( Map.Entry<String, PropertyInfo> a, Map.Entry<String, PropertyInfo> b ) {
                    return a.getKey().compareTo( b.getKey() );
                }
            } );
            for ( Map.Entry<String, PropertyInfo> entry : entries ) {
                result.put( entry.getKey(), entry.getValue() );
            }
        } else {
            for ( Map.Entry<String, PropertyInfo> entry : propertiesMap.entrySet() ) {
                result.put( entry.getKey(), entry.getValue() );
            }
        }

        return result;
    }

    protected TypeParameters generateTypeParameterMapperFields( SourceWriter source, BeanInfo beanInfo, String mapperClass,
                                                                String mapperNameFormat ) throws UnableToCompleteException {
        if ( null == beanInfo.getParameterizedTypes() || beanInfo.getParameterizedTypes().length == 0 ) {
            return null;
        }

        List<String> typeParameterMapperNames = new ArrayList<String>();
        StringBuilder joinedTypeParameterMappersWithType = new StringBuilder();

        for ( int i = 0; i < beanInfo.getParameterizedTypes().length; i++ ) {
            if ( i > 0 ) {
                joinedTypeParameterMappersWithType.append( ", " );
            }

            JClassType argType = beanInfo.getParameterizedTypes()[i];
            String mapperType = String.format( "%s<%s>", mapperClass, argType.getName() );
            String mapperName = String.format( mapperNameFormat, i );

            source.println( "private final %s %s;", mapperType, mapperName );

            typeParameterMapperNames.add( mapperName );
            joinedTypeParameterMappersWithType.append( String.format( "%s %s%s", mapperType, TYPE_PARAMETER_PREFIX, mapperName ) );
        }

        return new TypeParameters( typeParameterMapperNames, joinedTypeParameterMappersWithType.toString() );
    }

    protected String getQualifiedClassName( JType type ) {
        if ( null == type.isPrimitive() ) {
            return type.getParameterizedQualifiedSourceName();
        } else {
            return type.isPrimitive().getQualifiedBoxedSourceName();
        }
    }

    protected void generateIdentifierSerializationInfo( SourceWriter source, JClassType type, BeanIdentityInfo identityInfo ) throws
            UnableToCompleteException {

        if ( identityInfo.isIdABeanProperty() ) {
            source.print( "new %s<%s>(%s, \"%s\")", PropertyIdentitySerializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), identityInfo.isAlwaysAsId(), identityInfo.getPropertyName() );
        } else {
            String qualifiedType = getQualifiedClassName( identityInfo.getType() );
            String identityPropertyClass = String.format( "%s<%s, %s>", AbstractIdentitySerializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), qualifiedType );

            source.println( "new %s(%s, \"%s\") {", identityPropertyClass, identityInfo.isAlwaysAsId(), identityInfo.getPropertyName() );
            source.indent();

            source.println( "@Override" );
            source.println( "protected %s<?> newSerializer() {", JSON_SERIALIZER_CLASS );
            source.indent();
            source.println( "return %s;", getJsonSerializerFromType( identityInfo.getType() ).getInstance() );
            source.outdent();
            source.println( "}" );
            source.println();

            source.println( "@Override" );
            source.println( "public %s<%s> getObjectId(%s bean, %s ctx) {", ObjectIdSerializer.class.getName(), qualifiedType, type
                    .getParameterizedQualifiedSourceName(), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();

            String generatorType = String.format( "%s<%s>", ObjectIdGenerator.class.getName(), qualifiedType );
            source.println( "%s generator = new %s().forScope(%s.class);", generatorType, identityInfo.getGenerator()
                    .getCanonicalName(), identityInfo.getScope().getName() );
            source.println( "%s scopedGen = ctx.findObjectIdGenerator(generator);", generatorType );
            source.println( "if(null == scopedGen) {" );
            source.indent();
            source.println( "scopedGen = generator.newForSerialization(ctx);" );
            source.println( "ctx.addGenerator(scopedGen);" );
            source.outdent();
            source.println( "}" );
            source.println( "return new %s<%s>(scopedGen.generateId(bean), getSerializer());", ObjectIdSerializer.class
                    .getName(), qualifiedType );

            source.outdent();
            source.println( "}" );

            source.outdent();
            source.print( "}" );
        }
    }

    protected void generateIdentifierDeserializationInfo( SourceWriter source, JClassType type, BeanIdentityInfo identityInfo ) throws
            UnableToCompleteException {
        if ( identityInfo.isIdABeanProperty() ) {

            source.print( "new %s<%s>(\"%s\", %s.class, %s.class)", PropertyIdentityDeserializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), identityInfo.getPropertyName(), identityInfo.getGenerator()
                    .getCanonicalName(), identityInfo.getScope().getCanonicalName() );

        } else {

            String qualifiedType = getQualifiedClassName( identityInfo.getType() );

            String identityPropertyClass = String.format( "%s<%s, %s>", AbstractIdentityDeserializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), qualifiedType );

            source.println( "new %s(\"%s\", %s.class, %s.class) {", identityPropertyClass, identityInfo.getPropertyName(), identityInfo
                    .getGenerator().getCanonicalName(), identityInfo.getScope().getCanonicalName() );
            source.indent();

            source.println( "@Override" );
            source.println( "protected %s<?> newDeserializer() {", JSON_DESERIALIZER_CLASS );
            source.indent();
            source.println( "return %s;", getJsonDeserializerFromType( identityInfo.getType() ).getInstance() );
            source.outdent();
            source.println( "}" );

            source.outdent();
            source.print( "}" );
        }
    }

    protected void generateTypeInfo( SourceWriter source, BeanTypeInfo typeInfo, boolean serialization ) throws UnableToCompleteException {
        String typeInfoProperty = null;
        if ( null != typeInfo.getPropertyName() ) {
            typeInfoProperty = QUOTED_FUNCTION.apply( typeInfo.getPropertyName() );
        }
        source.println( "new %s(%s.%s, %s)", serialization ? TYPE_SERIALIZATION_INFO_CLASS : TYPE_DESERIALIZATION_INFO_CLASS, As.class
                .getCanonicalName(), typeInfo.getInclude(), typeInfoProperty );
        source.indent();

        for ( Entry<JClassType, String> entry : typeInfo.getMapTypeToMetadata().entrySet() ) {
            source.println( ".addTypeInfo(%s.class, \"%s\")", entry.getKey().getQualifiedSourceName(), entry.getValue() );
        }

        source.outdent();
    }

    protected JClassType findFirstTypeToApplyPropertyAnnotation( JMapperType mapperType ) {
        return findFirstTypeToApplyPropertyAnnotation( Arrays.asList( mapperType ) );
    }

    private JClassType findFirstTypeToApplyPropertyAnnotation( List<JMapperType> mapperTypeList ) {
        if ( mapperTypeList.isEmpty() ) {
            return null;
        }

        List<JMapperType> subLevel = new ArrayList<JMapperType>();
        for ( JMapperType mapperType : mapperTypeList ) {
            if ( mapperType.isBeanMapper() ) {
                return mapperType.getType().isClass();
            } else if ( mapperType.getParameters().length > 0 ) {
                subLevel.addAll( Arrays.asList( mapperType.getParameters() ) );
            }
        }

        return findFirstTypeToApplyPropertyAnnotation( subLevel );
    }

    protected void generateCommonPropertyParameters( SourceWriter source, PropertyInfo property,
                                                     JMapperType mapperType ) throws UnableToCompleteException {
        if ( property.getFormat().isPresent() ) {
            JsonFormat format = property.getFormat().get();
            if ( !Strings.isNullOrEmpty( format.pattern() ) ) {
                source.println();
                source.print( ".setPattern(\"%s\")", format.pattern() );
            }
            source.println();
            source.print( ".setShape(%s.%s)", Shape.class.getCanonicalName(), format.shape().name() );
            if ( !Strings.isNullOrEmpty( format.locale() ) && !JsonFormat.DEFAULT_LOCALE.equals( format.locale() ) ) {
                logger.log( Type.WARN, "JsonFormat.locale is not supported by default" );
                source.println();
                source.print( ".setLocale(\"%s\")", format.locale() );
            }
            if ( !Strings.isNullOrEmpty( format.timezone() ) && !JsonFormat.DEFAULT_TIMEZONE.equals( format.timezone() ) ) {
                logger.log( Type.WARN, "JsonFormat.timezone is not supported by default" );
                source.println();
                source.print( ".setTimezone(\"%s\")", format.timezone() );
            }
        }
        if ( property.getIgnoredProperties().isPresent() ) {
            for ( String ignoredProperty : property.getIgnoredProperties().get() ) {
                source.println();
                source.print( ".addIgnoredProperty(\"%s\")", ignoredProperty );
            }
        }
    }
}
