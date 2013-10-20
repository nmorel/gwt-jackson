package com.github.nmorel.gwtjackson.rebind;

import java.util.Map;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;

/**
 * @author Nicolas Morel
 */
public class BeanJsonMapperInfo {

    private final JClassType type;

    private final String qualifiedSerializerClassName;

    private final String simpleSerializerClassName;

    private final String qualifiedDeserializerClassName;

    private final String simpleDeserializerClassName;

    private final String genericClassParameters;

    private final String genericClassBoundedParameters;

    private BeanInfo beanInfo;

    private Map<String, PropertyInfo> properties;

    public BeanJsonMapperInfo( JClassType type, String qualifiedSerializerClassName, String simpleSerializerClassName,
                               String qualifiedDeserializerClassName, String simpleDeserializerClassName ) {
        this.type = type;
        this.qualifiedSerializerClassName = qualifiedSerializerClassName;
        this.simpleSerializerClassName = simpleSerializerClassName;
        this.qualifiedDeserializerClassName = qualifiedDeserializerClassName;
        this.simpleDeserializerClassName = simpleDeserializerClassName;

        if ( null != type.isGenericType() ) {
            StringBuilder genericClassParametersBuilder = new StringBuilder();
            StringBuilder genericClassBoundedParametersBuilder = new StringBuilder();
            for ( JTypeParameter parameter : type.isGenericType().getTypeParameters() ) {
                if ( genericClassParametersBuilder.length() == 0 ) {
                    genericClassParametersBuilder.append( '<' );
                    genericClassBoundedParametersBuilder.append( '<' );
                } else {
                    genericClassParametersBuilder.append( ", " );
                    genericClassBoundedParametersBuilder.append( ", " );
                }
                genericClassParametersBuilder.append( parameter.getName() );

                genericClassBoundedParametersBuilder.append( parameter.getName() );
                if ( !(parameter.getBounds().length == 1 && parameter.getBounds()[0].getQualifiedSourceName().equals( Object.class
                    .getName() )) ) {
                    for ( int i = 0; i < parameter.getBounds().length; i++ ) {
                        if ( i == 0 ) {
                            genericClassBoundedParametersBuilder.append( " extends " );
                        } else {
                            genericClassBoundedParametersBuilder.append( " & " );
                        }
                        JClassType bound = parameter.getBounds()[i];
                        genericClassBoundedParametersBuilder.append( bound.getParameterizedQualifiedSourceName() );
                    }
                }
            }
            genericClassParametersBuilder.append( '>' );
            genericClassBoundedParametersBuilder.append( '>' );
            genericClassParameters = genericClassParametersBuilder.toString();
            genericClassBoundedParameters = genericClassBoundedParametersBuilder.toString();
        } else {
            genericClassParameters = "";
            genericClassBoundedParameters = "";
        }
    }

    public JClassType getType() {
        return type;
    }

    public String getQualifiedSerializerClassName() {
        return qualifiedSerializerClassName;
    }

    public String getSimpleSerializerClassName() {
        return simpleSerializerClassName;
    }

    public String getQualifiedDeserializerClassName() {
        return qualifiedDeserializerClassName;
    }

    public String getSimpleDeserializerClassName() {
        return simpleDeserializerClassName;
    }

    public String getGenericClassParameters() {
        return genericClassParameters;
    }

    public String getGenericClassBoundedParameters() {
        return genericClassBoundedParameters;
    }

    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    public void setBeanInfo( BeanInfo beanInfo ) {
        this.beanInfo = beanInfo;
    }

    public Map<String, PropertyInfo> getProperties() {
        return properties;
    }

    public void setProperties( Map<String, PropertyInfo> properties ) {
        this.properties = properties;
    }
}
