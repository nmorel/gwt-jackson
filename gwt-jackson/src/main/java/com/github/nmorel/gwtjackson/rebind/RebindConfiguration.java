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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.AbstractConfiguration;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns.JsonMixIn;
import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracleException;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.util.tools.shared.Md5Utils;

/**
 * Wrap the default configuration + user configuration. It reads the configuration from {@link DefaultConfiguration} and any {@link
 * AbstractConfiguration} the user defined with :
 * <pre>
 * &lt;extend-configuration-property name="gwtjackson.configuration.extension" value="com.example.MyAbstractConfiguration" /&gt;
 * </pre>
 *
 * @author Nicolas Morel
 */
public final class RebindConfiguration {

    public enum MapperType {
        KEY_SERIALIZER( true, true ) {
            @Override
            protected Map<Class, Class> getMapperTypeConfiguration( AbstractConfiguration configuration ) {
                return configuration.getMapTypeToKeySerializer();
            }
        }, KEY_DESERIALIZER( false, true ) {
            @Override
            protected Map<Class, Class> getMapperTypeConfiguration( AbstractConfiguration configuration ) {
                return configuration.getMapTypeToKeyDeserializer();
            }
        }, JSON_SERIALIZER( true, false ) {
            @Override
            protected Map<Class, Class> getMapperTypeConfiguration( AbstractConfiguration configuration ) {
                return configuration.getMapTypeToSerializer();
            }
        }, JSON_DESERIALIZER( false, false ) {
            @Override
            protected Map<Class, Class> getMapperTypeConfiguration( AbstractConfiguration configuration ) {
                return configuration.getMapTypeToDeserializer();
            }
        };

        private final boolean serializer;

        private final boolean key;

        private MapperType( boolean serializer, boolean key ) {
            this.serializer = serializer;
            this.key = key;
        }

        abstract Map<Class, Class> getMapperTypeConfiguration( final AbstractConfiguration configuration );

        boolean isSerializer() {
            return serializer;
        }

        boolean isKey() {
            return key;
        }
    }

    public static class MapperInstance {

        private final JClassType mapperType;

        private final String instanceCreation;

        private final MapperType[] parameters;

        private MapperInstance( JClassType mapperType, String instanceCreation ) {
            this( mapperType, instanceCreation, new MapperType[0] );
        }

        private MapperInstance( JClassType mapperType, String instanceCreation, MapperType[] parameters ) {
            this.mapperType = mapperType;
            this.instanceCreation = instanceCreation;
            this.parameters = parameters;
        }

        public JClassType getMapperType() {
            return mapperType;
        }

        public String getInstanceCreation() {
            return instanceCreation;
        }

        public MapperType[] getParameters() {
            return parameters;
        }
    }

    private static final String CONFIGURATION_EXTENSION_PROPERTY = "gwtjackson.configuration.extension";

    private final TreeLogger logger;

    private final GeneratorContext context;

    private final JacksonTypeOracle typeOracle;

    private final Map<String, MapperInstance> serializers = new HashMap<String, MapperInstance>();

    private final Map<String, MapperInstance> deserializers = new HashMap<String, MapperInstance>();

    private final Map<String, MapperInstance> keySerializers = new HashMap<String, MapperInstance>();

    private final Map<String, MapperInstance> keyDeserializers = new HashMap<String, MapperInstance>();

    private final Map<String, JClassType> mixInAnnotations = new HashMap<String, JClassType>();

    private final JClassType rootMapperClass;

    private final String rootMapperHash;

    // If the user adds an annotation on mapper, we have to make distinct serializer/deserializer for the impacted types.
    // For now, it means any types and associated subtypes targeted by a mix-in annotation
    private final Set<JClassType> specificTypes = new HashSet<JClassType>();

    public RebindConfiguration( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle, JClassType rootMapperClass )
            throws UnableToCompleteException {
        this.logger = logger;
        this.context = context;
        this.typeOracle = typeOracle;
        this.rootMapperClass = rootMapperClass;
        this.rootMapperHash = new BigInteger( 1, Md5Utils.getMd5Digest( rootMapperClass.getQualifiedSourceName().getBytes() ) )
                .toString( 16 );

        List<AbstractConfiguration> configurations = getAllConfigurations();

        for ( AbstractConfiguration configuration : configurations ) {
            for ( MapperType mapperType : MapperType.values() ) {
                addMappers( configuration, mapperType );
            }
            addMixInAnnotations( configuration.getMapMixInAnnotations(), rootMapperClass.getAnnotation( JsonMixIns.class ) );
        }
    }

    /**
     * @return the list of default configuration + user configurations
     */
    private List<AbstractConfiguration> getAllConfigurations() {
        ImmutableList.Builder<AbstractConfiguration> builder = ImmutableList.builder();
        builder.add( new DefaultConfiguration() );

        ConfigurationProperty property = null;
        try {
            property = context.getPropertyOracle().getConfigurationProperty( CONFIGURATION_EXTENSION_PROPERTY );
        } catch ( BadPropertyValueException e ) {
            logger.log( Type.WARN, "Cannot find the property " + CONFIGURATION_EXTENSION_PROPERTY );
        }

        if ( null != property && !property.getValues().isEmpty() ) {
            for ( String value : property.getValues() ) {
                try {
                    builder.add( (AbstractConfiguration) Class.forName( value ).newInstance() );
                } catch ( Exception e ) {
                    logger.log( Type.WARN, "Cannot instantiate the configuration class " + value );
                }
            }
        }

        return builder.build();
    }

    /**
     * Parse the configured serializer/deserializer configuration and put them into the corresponding map
     *
     * @param configuration configuration
     * @param mapperType type of the mapper
     */
    private void addMappers( final AbstractConfiguration configuration, final MapperType mapperType ) {
        Map<Class, Class> configuredMapper = mapperType.getMapperTypeConfiguration( configuration );

        for ( Entry<Class, Class> entry : configuredMapper.entrySet() ) {

            JType mappedType = findType( entry.getKey() );
            if ( null == mappedType ) {
                continue;
            }

            JClassType mapperClassType = findClassType( entry.getValue() );
            if ( null == mapperClassType ) {
                continue;
            }

            if ( mapperType.isKey() ) {
                MapperInstance keyMapperInstance = getKeyInstance( mapperClassType );
                if ( mapperType.isSerializer() ) {
                    keySerializers.put( mappedType.getQualifiedSourceName(), keyMapperInstance );
                } else {
                    keyDeserializers.put( mappedType.getQualifiedSourceName(), keyMapperInstance );
                }
            } else {
                MapperInstance mapperInstance = getInstance( mappedType, mapperClassType, mapperType.isSerializer() );
                if ( null != mapperInstance ) {
                    if ( mapperType.isSerializer() ) {
                        serializers.put( mappedType.getQualifiedSourceName(), mapperInstance );
                    } else {
                        deserializers.put( mappedType.getQualifiedSourceName(), mapperInstance );
                    }
                }
            }
        }
    }

    /**
     * @param clazz class to find the type
     *
     * @return the {@link JType} denoted by the class given in parameter
     */

    private JType findType( Class<?> clazz ) {
        if ( clazz.isPrimitive() ) {

            return JPrimitiveType.parse( clazz.getCanonicalName() );

        } else if ( clazz.isArray() ) {

            try {
                return context.getTypeOracle().parse( clazz.getCanonicalName() );
            } catch ( TypeOracleException e ) {
                logger.log( TreeLogger.WARN, "Cannot find the array denoted by the class " + clazz.getCanonicalName() );
                return null;
            }

        } else {
            return findClassType( clazz );
        }
    }

    /**
     * @param clazz class to find the type
     *
     * @return the {@link JClassType} denoted by the class given in parameter
     */
    private JClassType findClassType( Class<?> clazz ) {
        JClassType mapperType = context.getTypeOracle().findType( clazz.getCanonicalName() );
        if ( null == mapperType ) {
            logger.log( Type.WARN, "Cannot find the type denoted by the class " + clazz.getCanonicalName() );
            return null;
        }
        return mapperType;
    }

    /**
     * Search a static method or constructor to instantiate the mapper and return a {@link String} calling it.
     */
    private MapperInstance getInstance( JType mappedType, JClassType classType, boolean isSerializers ) {
        int nbParam = 0;
        if ( null != mappedType.isGenericType() ) {
            nbParam = mappedType.isGenericType().getTypeParameters().length;
        }

        // we first look at static method
        for ( JMethod method : classType.getMethods() ) {
            // method must be public static, return the instance type and take no parameters
            if ( method.isStatic() && method.getReturnType().getQualifiedSourceName().equals( classType.getQualifiedSourceName() ) && method
                    .getParameters().length == nbParam && method.isPublic() ) {
                MapperType[] parameters = getParameters( method, isSerializers );
                if ( null == parameters ) {
                    continue;
                }

                StringBuilder builder = new StringBuilder();
                builder.append( classType.getQualifiedSourceName() ).append( '.' ).append( method.getName() ).append( '(' );
                for ( int i = 0; i < nbParam; i++ ) {
                    if ( i > 0 ) {
                        builder.append( ", %s" );
                    } else {
                        builder.append( "%s" );
                    }
                }
                builder.append( ')' );
                return new MapperInstance( classType, builder.toString(), parameters );
            }
        }

        // then we search the default constructor
        for ( JConstructor constructor : classType.getConstructors() ) {
            if ( constructor.isPublic() && constructor.getParameters().length == nbParam ) {
                MapperType[] parameters = getParameters( constructor, isSerializers );
                if ( null == parameters ) {
                    continue;
                }

                StringBuilder builder = new StringBuilder();
                builder.append( "new " ).append( classType.getQualifiedSourceName() ).append( '(' );
                for ( int i = 0; i < nbParam; i++ ) {
                    if ( i > 0 ) {
                        builder.append( ", %s" );
                    } else {
                        builder.append( "%s" );
                    }
                }
                builder.append( ')' );
                return new MapperInstance( classType, builder.toString(), parameters );
            }
        }

        logger.log( Type.WARN, "Cannot instantiate the custom serializer/deserializer " + classType
                .getQualifiedSourceName() + ". It will be ignored" );
        return null;
    }

    private MapperType[] getParameters( JAbstractMethod method, boolean isSerializers ) {
        MapperType[] parameters = new MapperType[method.getParameters().length];
        for ( int i = 0; i < method.getParameters().length; i++ ) {
            JParameter parameter = method.getParameters()[i];
            if ( isSerializers ) {
                if ( typeOracle.isKeySerializer( parameter.getType() ) ) {
                    parameters[i] = MapperType.KEY_SERIALIZER;
                } else if ( typeOracle.isJsonSerializer( parameter.getType() ) ) {
                    parameters[i] = MapperType.JSON_SERIALIZER;
                } else {
                    // the parameter is unknown, we ignore this method
                    return null;
                }
            } else {
                if ( typeOracle.isKeyDeserializer( parameter.getType() ) ) {
                    parameters[i] = MapperType.KEY_DESERIALIZER;
                } else if ( typeOracle.isJsonDeserializer( parameter.getType() ) ) {
                    parameters[i] = MapperType.JSON_DESERIALIZER;
                } else {
                    // the parameter is unknown, we ignore this method
                    return null;
                }
            }
        }
        return parameters;
    }

    /**
     * Search a static method or constructor to instantiate the key mapper and return a {@link String} calling it.
     */
    private MapperInstance getKeyInstance( JClassType classType ) {
        // we first look at static method
        for ( JMethod method : classType.getMethods() ) {
            // method must be public static, return the instance type and take no parameters
            if ( method.isStatic() && method.getReturnType().getQualifiedSourceName().equals( classType.getQualifiedSourceName() ) && method
                    .getParameters().length == 0 && method.isPublic() ) {
                StringBuilder builder = new StringBuilder();
                builder.append( classType.getQualifiedSourceName() ).append( '.' ).append( method.getName() ).append( "()" );
                return new MapperInstance( classType, builder.toString() );
            }
        }

        // then we search the default constructor
        for ( JConstructor constructor : classType.getConstructors() ) {
            if ( constructor.isPublic() && constructor.getParameters().length == 0 ) {
                StringBuilder builder = new StringBuilder();
                builder.append( "new " ).append( classType.getQualifiedSourceName() ).append( "()" );
                return new MapperInstance( classType, builder.toString() );
            }
        }

        logger.log( Type.WARN, "Cannot instantiate the custom key serializer/deserializer " + classType
                .getQualifiedSourceName() + ". It will be ignored" );
        return null;
    }

    /**
     * Adds to {@link #mixInAnnotations} the configured mix-in annotations passed in parameters
     *
     * @param mapMixInAnnotations mix-ins annotations to add
     * @param mapperMixIns Annotation defined on mapper
     */
    private void addMixInAnnotations( Map<Class, Class> mapMixInAnnotations, JsonMixIns mapperMixIns ) {
        if ( null != mapperMixIns ) {
            for ( JsonMixIn jsonMixIn : mapperMixIns.value() ) {
                JClassType targetType = findClassType( jsonMixIn.target() );
                if ( null == targetType ) {
                    continue;
                }
                specificTypes.add( targetType );
                specificTypes.addAll( Arrays.asList( targetType.getSubtypes() ) );

                mapMixInAnnotations.put( jsonMixIn.target(), jsonMixIn.mixIn() );
            }
        }

        if ( !mapMixInAnnotations.isEmpty() ) {
            for ( Entry<Class, Class> entry : mapMixInAnnotations.entrySet() ) {
                JClassType targetType = findClassType( entry.getKey() );
                if ( null == targetType ) {
                    continue;
                }

                JClassType mixInType = findClassType( entry.getValue() );
                if ( null == mixInType ) {
                    continue;
                }

                mixInAnnotations.put( targetType.getQualifiedSourceName(), mixInType );
            }
        }
    }

    /**
     * Return a {@link MapperInstance} instantiating the serializer for the given type
     */
    public Optional<MapperInstance> getSerializer( JType type ) {
        return Optional.fromNullable( serializers.get( type.getQualifiedSourceName() ) );
    }

    /**
     * Return a {@link MapperInstance} instantiating the deserializer for the given type
     */
    public Optional<MapperInstance> getDeserializer( JType type ) {
        return Optional.fromNullable( deserializers.get( type.getQualifiedSourceName() ) );
    }

    /**
     * Return a {@link MapperInstance} instantiating the key serializer for the given type
     */
    public Optional<MapperInstance> getKeySerializer( JType type ) {
        return Optional.fromNullable( keySerializers.get( type.getQualifiedSourceName() ) );
    }

    /**
     * Return a {@link MapperInstance} instantiating the key deserializer for the given type
     */
    public Optional<MapperInstance> getKeyDeserializer( JType type ) {
        return Optional.fromNullable( keyDeserializers.get( type.getQualifiedSourceName() ) );
    }

    /**
     * Return the mixin type for the given type
     */
    public Optional<JClassType> getMixInAnnotations( JType type ) {
        return Optional.fromNullable( mixInAnnotations.get( type.getQualifiedSourceName() ) );
    }

    /**
     * @return the root mapper class that is currently generated
     */
    public JClassType getRootMapperClass() {
        return rootMapperClass;
    }

    public String getRootMapperHash() {
        return rootMapperHash;
    }

    /**
     * @param beanType type
     *
     * @return true if beanType is specific to the mapper
     */
    public boolean isSpecificToMapper( JClassType beanType ) {
        return specificTypes.contains( beanType );
    }
}
