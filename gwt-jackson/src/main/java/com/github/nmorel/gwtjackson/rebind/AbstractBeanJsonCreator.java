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
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractDelegationBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractObjectBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractSerializableBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.PropertyIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractValueBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.ObjectIdSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.PropertyIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.map.MapJsonSerializer;
import com.github.nmorel.gwtjackson.rebind.bean.BeanIdentityInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanProcessor;
import com.github.nmorel.gwtjackson.rebind.bean.BeanTypeInfo;
import com.github.nmorel.gwtjackson.rebind.exception.UnsupportedTypeException;
import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor.Accessor;
import com.github.nmorel.gwtjackson.rebind.property.PropertiesContainer;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.github.nmorel.gwtjackson.rebind.property.processor.PropertyProcessor;
import com.github.nmorel.gwtjackson.rebind.type.JDeserializerType;
import com.github.nmorel.gwtjackson.rebind.type.JMapperType;
import com.github.nmorel.gwtjackson.rebind.type.JSerializerType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.google.gwt.user.rebind.SourceWriter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.QUOTED_FUNCTION;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.isObject;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.isSerializable;

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

    protected static final String JSON_SERIALIZER_PARAMETERS_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializerParameters";

    protected BeanJsonMapperInfo mapperInfo;

    public AbstractBeanJsonCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration, JacksonTypeOracle
            typeOracle ) {
        super( logger, context, configuration, typeOracle );
    }

    @Override
    protected Optional<BeanJsonMapperInfo> getMapperInfo() {
        return Optional.of( mapperInfo );
    }

    /**
     * Creates an implementation of {@link AbstractBeanJsonSerializer} for the type given in
     * parameter
     *
     * @param beanType type of the bean
     *
     * @return the fully qualified name of the created class
     */
    public String create( JClassType beanType ) throws UnableToCompleteException, UnsupportedTypeException {

        boolean samePackage = true;
        String packageName = beanType.getPackage().getName();
        if ( packageName.startsWith( "java." ) ) {
            packageName = "gwtjackson." + packageName;
            samePackage = false;
        }

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

        String qualifiedClassName = isSerializer() ? qualifiedSerializerClassName : qualifiedDeserializerClassName;

        PrintWriter printWriter = getPrintWriter( packageName, isSerializer() ? simpleSerializerClassName : simpleDeserializerClassName );
        // the class already exists, no need to continue
        if ( printWriter == null ) {
            return qualifiedClassName;
        }

        try {
            mapperInfo = typeOracle.getBeanJsonMapperInfo( beanType );

            if ( null == mapperInfo ) {
                // retrieve the informations on the beans and its properties
                BeanInfo beanInfo = BeanProcessor.processBean( logger, typeOracle, configuration, beanType );

                PropertiesContainer properties = PropertyProcessor
                        .findAllProperties( configuration, logger, typeOracle, beanInfo, samePackage );

                beanInfo = BeanProcessor.processProperties( configuration, logger, typeOracle, beanInfo, properties );

                mapperInfo = new BeanJsonMapperInfo( beanType, qualifiedSerializerClassName, simpleSerializerClassName,
                        qualifiedDeserializerClassName, simpleDeserializerClassName, beanInfo, properties
                        .getProperties() );

                typeOracle.addBeanJsonMapperInfo( beanType, mapperInfo );
            }

            String superclass;
            if ( isSerializer() ) {
                if ( mapperInfo.getBeanInfo().getValuePropertyInfo().isPresent() ) {
                    superclass = AbstractValueBeanJsonSerializer.class.getCanonicalName();
                } else {
                    superclass = ABSTRACT_BEAN_JSON_SERIALIZER_CLASS;
                }
                superclass = superclass + "<" + beanType.getParameterizedQualifiedSourceName() + ">";
            } else {
                if ( isObject( beanType ) ) {
                    superclass = AbstractObjectBeanJsonDeserializer.class.getCanonicalName();
                } else if ( isSerializable( beanType ) ) {
                    superclass = AbstractSerializableBeanJsonDeserializer.class.getCanonicalName();
                } else if ( mapperInfo.getBeanInfo().isCreatorDelegation() ) {
                    superclass = AbstractDelegationBeanJsonDeserializer.class.getCanonicalName() + "<" + beanType
                            .getParameterizedQualifiedSourceName() + ">";
                } else {
                    superclass = ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS + "<" + beanType.getParameterizedQualifiedSourceName() + ">";
                }
            }

            SourceWriter source = getSourceWriter( printWriter, packageName, getSimpleClassName() + getGenericClassBoundedParameters(),
                    superclass );

            writeClassBody( source, mapperInfo.getBeanInfo(), mapperInfo.getProperties() );

            source.println();
            source.commit( logger );
        } finally {
            printWriter.close();
        }

        return qualifiedClassName;
    }

    protected abstract boolean isSerializer();

    protected String getSimpleClassName() {
        if ( isSerializer() ) {
            return mapperInfo.getSimpleSerializerClassName();
        } else {
            return mapperInfo.getSimpleDeserializerClassName();
        }
    }

    protected String getGenericClassBoundedParameters() {
        return mapperInfo.getGenericClassBoundedParameters();
    }

    protected abstract void writeClassBody( SourceWriter source, BeanInfo info, ImmutableMap<String, PropertyInfo> properties ) throws
            UnableToCompleteException, UnsupportedTypeException;

    protected TypeParameters generateTypeParameterMapperFields( SourceWriter source, BeanInfo beanInfo, String mapperClass, String
            mapperNameFormat ) throws UnableToCompleteException {
        if ( beanInfo.getParameterizedTypes().isEmpty() ) {
            return null;
        }

        List<String> typeParameterMapperNames = new ArrayList<String>();
        StringBuilder joinedTypeParameterMappersWithType = new StringBuilder();

        for ( int i = 0; i < beanInfo.getParameterizedTypes().size(); i++ ) {
            if ( i > 0 ) {
                joinedTypeParameterMappersWithType.append( ", " );
            }

            JClassType argType = beanInfo.getParameterizedTypes().get( i );
            String mapperType = String.format( "%s<%s>", mapperClass, argType.getName() );
            String mapperName = String.format( mapperNameFormat, i );

            source.println( "private final %s %s;", mapperType, mapperName );

            typeParameterMapperNames.add( mapperName );
            joinedTypeParameterMappersWithType.append( String.format( "%s %s%s", mapperType, TYPE_PARAMETER_PREFIX, mapperName ) );
        }

        return new TypeParameters( typeParameterMapperNames, joinedTypeParameterMappersWithType.toString() );
    }

    protected String getParameterizedQualifiedClassName( JType type ) {
        if ( null == type.isPrimitive() ) {
            return type.getParameterizedQualifiedSourceName();
        } else {
            return type.isPrimitive().getQualifiedBoxedSourceName();
        }
    }

    protected Optional<JSerializerType> getIdentitySerializerType( BeanIdentityInfo identityInfo ) throws UnableToCompleteException,
            UnsupportedTypeException {
        if ( identityInfo.isIdABeanProperty() ) {
            return Optional.absent();
        } else {
            return Optional.of( getJsonSerializerFromType( identityInfo.getType().get() ) );
        }
    }

    protected void generateIdentifierSerializationInfo( SourceWriter source, JClassType type, BeanIdentityInfo identityInfo,
                                                        Optional<JSerializerType> serializerType ) throws UnableToCompleteException,
            UnsupportedTypeException {

        if ( identityInfo.isIdABeanProperty() ) {
            BeanJsonMapperInfo mapperInfo = typeOracle.getBeanJsonMapperInfo( type );
            PropertyInfo propertyInfo = mapperInfo.getProperties().get( identityInfo.getPropertyName() );
            JSerializerType propertySerializerType = getJsonSerializerFromType( propertyInfo.getType() );

            source.println( "new %s<%s, %s>(%s, \"%s\") {", PropertyIdentitySerializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), getParameterizedQualifiedClassName( propertyInfo.getType()), identityInfo
                    .isAlwaysAsId(), identityInfo.getPropertyName() );

            source.indent();

            generateBeanPropertySerializerBody( source, type, propertyInfo, propertySerializerType );

            source.outdent();
            source.print( "}" );

        } else {
            String qualifiedType = getParameterizedQualifiedClassName( identityInfo.getType().get() );
            String identityPropertyClass = String.format( "%s<%s, %s>", AbstractIdentitySerializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), qualifiedType );

            source.println( "new %s(%s, \"%s\") {", identityPropertyClass, identityInfo.isAlwaysAsId(), identityInfo.getPropertyName() );
            source.indent();

            source.println( "@Override" );
            source.println( "protected %s<?> newSerializer() {", JSON_SERIALIZER_CLASS );
            source.indent();
            source.println( "return %s;", serializerType.get().getInstance() );
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

    protected Optional<JDeserializerType> getIdentityDeserializerType( BeanIdentityInfo identityInfo ) throws UnableToCompleteException,
            UnsupportedTypeException {
        if ( identityInfo.isIdABeanProperty() ) {
            return Optional.absent();
        } else {
            return Optional.of( getJsonDeserializerFromType( identityInfo.getType().get() ) );
        }
    }

    protected void generateIdentifierDeserializationInfo( SourceWriter source, JClassType type, BeanIdentityInfo identityInfo,
                                                          Optional<JDeserializerType> deserializerType ) throws UnableToCompleteException {
        if ( identityInfo.isIdABeanProperty() ) {

            source.print( "new %s<%s>(\"%s\", %s.class, %s.class)", PropertyIdentityDeserializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), identityInfo.getPropertyName(), identityInfo.getGenerator()
                    .getCanonicalName(), identityInfo.getScope().getCanonicalName() );

        } else {

            String qualifiedType = getParameterizedQualifiedClassName( identityInfo.getType().get() );

            String identityPropertyClass = String.format( "%s<%s, %s>", AbstractIdentityDeserializationInfo.class.getName(), type
                    .getParameterizedQualifiedSourceName(), qualifiedType );

            source.println( "new %s(\"%s\", %s.class, %s.class) {", identityPropertyClass, identityInfo.getPropertyName(), identityInfo
                    .getGenerator().getCanonicalName(), identityInfo.getScope().getCanonicalName() );
            source.indent();

            source.println( "@Override" );
            source.println( "protected %s<?> newDeserializer() {", JSON_DESERIALIZER_CLASS );
            source.indent();
            source.println( "return %s;", deserializerType.get().getInstance() );
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

        ImmutableMap<JClassType, String> mapTypeToMetadata;
        if ( serialization ) {
            mapTypeToMetadata = typeInfo.getMapTypeToSerializationMetadata();
        } else {
            mapTypeToMetadata = typeInfo.getMapTypeToDeserializationMetadata();
        }

        for ( Entry<JClassType, String> entry : mapTypeToMetadata.entrySet() ) {
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
            } else if ( mapperType.getParameters().size() > 0 ) {
                subLevel.addAll( mapperType.getParameters() );
            }
        }

        return findFirstTypeToApplyPropertyAnnotation( subLevel );
    }

    protected void generateCommonPropertyParameters( SourceWriter source, PropertyInfo property, JMapperType mapperType ) throws
            UnableToCompleteException {
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
        }
        if ( property.getIgnoredProperties().isPresent() ) {
            for ( String ignoredProperty : property.getIgnoredProperties().get() ) {
                source.println();
                source.print( ".addIgnoredProperty(\"%s\")", ignoredProperty );
            }
        }
    }

    protected ImmutableList<JClassType> filterSubtypes( BeanInfo beanInfo ) {
        if ( isSerializer() ) {
            return CreatorUtils.filterSubtypesForSerialization( logger, configuration, beanInfo.getType() );
        } else {
            return CreatorUtils.filterSubtypesForDeserialization( logger, configuration, beanInfo.getType() );
        }
    }

    protected void generateBeanPropertySerializerBody( SourceWriter source, JClassType beanType, PropertyInfo property, JSerializerType
            serializerType ) throws UnableToCompleteException {
        Accessor getterAccessor = property.getGetterAccessor().get().getAccessor( "bean" );

        source.println( "@Override" );
        String returnType;
        if ( property.isAnyGetter() ) {
            returnType = MapJsonSerializer.class.getCanonicalName();
        } else {
            returnType = String.format( "%s<?>", JSON_SERIALIZER_CLASS );
        }
        source.println( "protected %s newSerializer() {", returnType );
        source.indent();
        source.println( "return %s;", serializerType.getInstance() );
        source.outdent();
        source.println( "}" );

        generatePropertySerializerParameters( source, property, serializerType );

        source.println();

        source.println( "@Override" );
        source.println( "public %s getValue(%s bean, %s ctx) {", getParameterizedQualifiedClassName( property
                .getType() ), getParameterizedQualifiedClassName( beanType ), JSON_SERIALIZATION_CONTEXT_CLASS );
        source.indent();
        source.println( "return %s;", getterAccessor.getAccessor() );
        source.outdent();
        source.println( "}" );

        if ( getterAccessor.getAdditionalMethod().isPresent() ) {
            source.println();
            getterAccessor.getAdditionalMethod().get().write( source );
        }
    }

    protected void generatePropertySerializerParameters( SourceWriter source, PropertyInfo property, JSerializerType serializerType )
            throws UnableToCompleteException {
        if ( property.getFormat().isPresent() || property.getIgnoredProperties().isPresent() || property.getIgnoreUnknown().isPresent() ||
                property.getIdentityInfo().isPresent() || property.getTypeInfo().isPresent() || property.getInclude().isPresent() ) {

            JClassType annotatedType = findFirstTypeToApplyPropertyAnnotation( serializerType );

            source.println();

            source.println( "@Override" );
            source.println( "protected %s newParameters() {", JSON_SERIALIZER_PARAMETERS_CLASS );
            source.indent();
            source.print( "return new %s()", JSON_SERIALIZER_PARAMETERS_CLASS );

            source.indent();

            generateCommonPropertyParameters( source, property, serializerType );

            if ( property.getFormat().isPresent() ) {
                JsonFormat format = property.getFormat().get();
                if ( !Strings.isNullOrEmpty( format.timezone() ) && !JsonFormat.DEFAULT_TIMEZONE.equals( format.timezone() ) ) {
                    source.println();
                    java.util.TimeZone timeZoneJdk = java.util.TimeZone.getTimeZone( format.timezone() );
                    // in java the offset is in milliseconds from timezone to GMT
                    // in gwt the offset is in minutes from GMT to timezone
                    // so we convert the milliseconds in minutes and invert the sign
                    int timeZoneOffsetGwt = (timeZoneJdk.getRawOffset() / 1000 / 60) * -1;
                    source.print( ".setTimezone(%s.createTimeZone( %d ))", TimeZone.class.getCanonicalName(), timeZoneOffsetGwt );
                }
            }

            if ( property.getInclude().isPresent() ) {
                source.println();
                source.print( ".setInclude(%s.%s)", Include.class.getCanonicalName(), property.getInclude().get().name() );
            }

            if ( property.getIdentityInfo().isPresent() ) {
                try {
                    Optional<JSerializerType> identitySerializerType = getIdentitySerializerType( property.getIdentityInfo().get() );
                    source.println();
                    source.print( ".setIdentityInfo(" );
                    generateIdentifierSerializationInfo( source, annotatedType, property.getIdentityInfo().get(), identitySerializerType );
                    source.print( ")" );
                } catch ( UnsupportedTypeException e ) {
                    logger.log( Type.WARN, "Identity type is not supported. We ignore it." );
                }
            }

            if ( property.getTypeInfo().isPresent() ) {
                source.println();
                source.print( ".setTypeInfo(" );
                generateTypeInfo( source, property.getTypeInfo().get(), true );
                source.print( ")" );
            }

            source.println( ";" );
            source.outdent();

            source.outdent();
            source.println( "}" );
        }
    }
}
