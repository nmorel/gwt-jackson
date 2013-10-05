package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.SourceWriter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonCreator extends AbstractCreator {

    protected static final String ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" +
        ".AbstractBeanJsonDeserializer";

    protected static final String ABSTRACT_BEAN_JSON_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean" + "" +
        ".AbstractBeanJsonSerializer";

    protected BeanJsonMapperInfo mapperInfo;

    public AbstractBeanJsonCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle ) {
        super( logger, context, typeOracle );
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
    public BeanJsonMapperInfo create( JClassType beanType ) throws UnableToCompleteException {

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

            String simpleSerializerClassName = builder.toString() + "BeanJsonSerializerImpl";
            String qualifiedSerializerClassName = packageName + "." + simpleSerializerClassName;
            String simpleDeserializerClassName = builder.toString() + "BeanJsonDeserializerImpl";
            String qualifiedDeserializerClassName = packageName + "." + simpleDeserializerClassName;

            mapperInfo = new BeanJsonMapperInfo( beanType, qualifiedSerializerClassName, simpleSerializerClassName,
                qualifiedDeserializerClassName, simpleDeserializerClassName );

            // retrieve the informations on the beans and its properties
            BeanInfo info = BeanInfo.process( logger, typeOracle, mapperInfo );
            mapperInfo.setBeanInfo( info );

            Map<String, PropertyInfo> properties = findAllProperties( info );
            mapperInfo.setProperties( properties );

            typeOracle.addBeanJsonMapperInfo( beanType, mapperInfo );
        }

        PrintWriter printWriter = getPrintWriter( packageName, getSimpleClassName() );
        // the class already exists, no need to continue
        if ( printWriter == null ) {
            return mapperInfo;
        }

        String parameterizedTypes = beanType.getParameterizedQualifiedSourceName();
        if ( !isSerializer() ) {
            parameterizedTypes = parameterizedTypes + ", " + mapperInfo.getBeanInfo().getInstanceBuilderQualifiedName();
        }

        SourceWriter source = getSourceWriter( printWriter, packageName, getSimpleClassName(), getSuperclass() + "<" +
            parameterizedTypes + ">" );

        writeClassBody( source, mapperInfo.getBeanInfo(), mapperInfo.getProperties() );

        return mapperInfo;
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

    protected String getSuperclass() {
        if ( isSerializer() ) {
            return ABSTRACT_BEAN_JSON_SERIALIZER_CLASS;
        } else {
            return ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS;
        }
    }

    protected abstract void writeClassBody( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException;

    protected String extractTypeMetadata( BeanInfo info, JClassType subtype ) throws UnableToCompleteException {
        switch ( info.getTypeInfo().use() ) {
            case NAME:
                JsonSubTypes jsonSubTypes = findFirstEncounteredAnnotationsOnAllHierarchy( info.getType(), JsonSubTypes.class );
                if ( null != jsonSubTypes && jsonSubTypes.value().length > 0 ) {
                    for ( JsonSubTypes.Type type : jsonSubTypes.value() ) {
                        if ( !type.name().isEmpty() && type.value().getName().equals( subtype.getQualifiedBinaryName() ) ) {
                            return type.name();
                        }
                    }
                }
                JsonTypeName typeName = findFirstEncounteredAnnotationsOnAllHierarchy( subtype, JsonTypeName.class );
                if ( null != typeName && null != typeName.value() && !typeName.value().isEmpty() ) {
                    return typeName.value();
                } else {
                    String simpleBinaryName = subtype.getQualifiedBinaryName();
                    int indexLastDot = simpleBinaryName.lastIndexOf( '.' );
                    if ( indexLastDot != -1 ) {
                        simpleBinaryName = simpleBinaryName.substring( indexLastDot + 1 );
                    }
                    return simpleBinaryName;
                }
            case MINIMAL_CLASS:
                if ( !info.getType().getPackage().isDefault() ) {
                    String basePackage = info.getType().getPackage().getName();
                    if ( subtype.getQualifiedBinaryName().startsWith( basePackage + "." ) ) {
                        return subtype.getQualifiedBinaryName().substring( basePackage.length() );
                    }
                }
            case CLASS:
                return subtype.getQualifiedBinaryName();
            default:
                logger.log( TreeLogger.Type.ERROR, "JsonTypeInfo.Id." + info.getTypeInfo().use() + " is not supported" );
                throw new UnableToCompleteException();
        }
    }

    private Map<String, PropertyInfo> findAllProperties( BeanInfo info ) throws UnableToCompleteException {
        Map<String, FieldAccessors> fieldsMap = new LinkedHashMap<String, FieldAccessors>();
        parseFields( info.getType(), fieldsMap );
        parseMethods( info.getType(), fieldsMap );

        // Processing all the properties accessible via field, getter or setter
        Map<String, PropertyInfo> propertiesMap = new LinkedHashMap<String, PropertyInfo>();
        for ( FieldAccessors field : fieldsMap.values() ) {
            PropertyInfo property = PropertyInfo.process( logger, typeOracle, field, mapperInfo );
            if ( !property.isVisible() ) {
                logger.log( TreeLogger.Type.DEBUG, "Field " + field.getFieldName() + " of type " + info.getType() + " is not visible" );
            } else {
                propertiesMap.put( property.getPropertyName(), property );
            }
        }

        // We look if there is any constructor parameters not found yet
        if ( !info.getCreatorParameters().isEmpty() ) {
            for ( Map.Entry<String, JParameter> entry : info.getCreatorParameters().entrySet() ) {
                PropertyInfo property = propertiesMap.get( entry.getKey() );
                if ( null == property ) {
                    propertiesMap.put( entry.getKey(), PropertyInfo.process( logger, typeOracle, entry.getKey(), entry.getValue(), info ) );
                } else if ( entry.getValue().getAnnotation( JsonProperty.class ).required() ) {
                    property.setRequired( true );
                }
            }
        }

        Map<String, PropertyInfo> result = new LinkedHashMap<String, PropertyInfo>();

        // we first add the properties defined in order
        for ( String orderedProperty : info.getPropertyOrderList() ) {
            // we remove the entry to have the map with only properties with natural or alphabetic order
            PropertyInfo property = propertiesMap.remove( orderedProperty );
            if ( null != property ) {
                result.put( property.getPropertyName(), property );
            }
        }

        // if the user asked for an alphbetic order, we sort the rest of the properties
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

        findIdPropertyInfo( result, info.getIdentityInfo() );

        return result;
    }

    private void parseFields( JClassType type, Map<String, FieldAccessors> propertiesMap ) {
        if ( null == type || type.getQualifiedSourceName().equals( "java.lang.Object" ) ) {
            return;
        }

        for ( JField field : type.getFields() ) {
            String fieldName = field.getName();
            FieldAccessors property = propertiesMap.get( fieldName );
            if ( null == property ) {
                property = new FieldAccessors( fieldName );
                propertiesMap.put( fieldName, property );
            }
            if ( null == property.getField() ) {
                property.setField( field );
            } else {
                // we found an other field with the same name on a superclass. we ignore it
                logger.log( TreeLogger.Type.WARN, "A field with the same name as " + field
                    .getName() + " has already been found on child class" );
            }
        }
        parseFields( type.getSuperclass(), propertiesMap );
    }

    private void parseMethods( JClassType type, Map<String, FieldAccessors> propertiesMap ) {
        if ( null == type || type.getQualifiedSourceName().equals( "java.lang.Object" ) ) {
            return;
        }

        for ( JMethod method : type.getMethods() ) {
            if ( null != method.isConstructor() || method.isStatic() ) {
                continue;
            }

            JType returnType = method.getReturnType();
            if ( null != returnType.isPrimitive() && JPrimitiveType.VOID.equals( returnType.isPrimitive() ) ) {
                // might be a setter
                if ( method.getParameters().length == 1 ) {
                    String methodName = method.getName();
                    if ( methodName.startsWith( "set" ) && methodName.length() > 3 ) {
                        // it's a setter method
                        String fieldName = extractFieldNameFromGetterSetterMethodName( methodName );
                        FieldAccessors property = propertiesMap.get( fieldName );
                        if ( null == property ) {
                            property = new FieldAccessors( fieldName );
                            propertiesMap.put( fieldName, property );
                        }
                        property.addSetter( method );
                    }
                }
            } else {
                // might be a getter
                if ( method.getParameters().length == 0 ) {
                    String methodName = method.getName();
                    if ( (methodName.startsWith( "get" ) && methodName.length() > 3) || (methodName.startsWith( "is" ) && methodName
                        .length() > 2 && null != returnType.isPrimitive() && JPrimitiveType.BOOLEAN.equals( returnType.isPrimitive() )) ) {
                        // it's a getter method
                        String fieldName = extractFieldNameFromGetterSetterMethodName( methodName );
                        FieldAccessors property = propertiesMap.get( fieldName );
                        if ( null == property ) {
                            property = new FieldAccessors( fieldName );
                            propertiesMap.put( fieldName, property );
                        }
                        property.addGetter( method );
                    }
                }
            }
        }

        for ( JClassType interf : type.getImplementedInterfaces() ) {
            parseMethods( interf, propertiesMap );
        }

        parseMethods( type.getSuperclass(), propertiesMap );
    }

    private String extractFieldNameFromGetterSetterMethodName( String methodName ) {
        if ( methodName.startsWith( "is" ) ) {
            return methodName.substring( 2, 3 ).toLowerCase() + methodName.substring( 3 );
        } else {
            return methodName.substring( 3, 4 ).toLowerCase() + methodName.substring( 4 );
        }
    }
}
