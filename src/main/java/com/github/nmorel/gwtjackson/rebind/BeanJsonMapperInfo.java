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
            StringBuilder builder = new StringBuilder();
            for ( JTypeParameter parameter : type.isGenericType().getTypeParameters() ) {
                if ( builder.length() == 0 ) {
                    builder.append( '<' );
                } else {
                    builder.append( ", " );
                }
                builder.append( parameter.getName() );
            }
            builder.append( '>' );
            genericClassParameters = builder.toString();
        } else {
            genericClassParameters = "";
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
