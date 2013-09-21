package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.JsonMappingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonMapper} for beans.
 *
 * @author Nicolas Morel
 */
public class SuperclassInfo<T>
{
    public static interface SubtypeMapper<T>
    {
        JsonMapper<T> getMapper( JsonMappingContext ctx );

        T decodeObject( JsonReader reader, JsonDecodingContext ctx ) throws IOException;

        void encodeObject( JsonWriter writer, T bean, JsonEncodingContext ctx ) throws IOException;
    }

    /** Name of the property containing information about the subtype */
    private final boolean includeTypeInfo;
    private final JsonTypeInfo.As include;
    private final String propertyName;
    private final Map<String, SubtypeMapper<? extends T>> subtypeInfoToMapper;
    private final Map<Class<? extends T>, String> subtypeClassToInfo;
    private final Map<Class<? extends T>, SubtypeMapper<? extends T>> subtypeClassToMapper;

    public SuperclassInfo()
    {
        this( null, null, false );
    }

    public SuperclassInfo( JsonTypeInfo.As include, String propertyName )
    {
        this( include, propertyName, true );
    }

    private SuperclassInfo( JsonTypeInfo.As include, String propertyName, boolean includeTypeInfo )
    {
        this.includeTypeInfo = includeTypeInfo;
        this.include = include;
        this.propertyName = propertyName;
        if ( includeTypeInfo )
        {
            this.subtypeInfoToMapper = new HashMap<String, SubtypeMapper<? extends T>>();
            this.subtypeClassToInfo = new HashMap<Class<? extends T>, String>();
        }
        else
        {
            this.subtypeInfoToMapper = null;
            this.subtypeClassToInfo = null;
        }
        this.subtypeClassToMapper = new HashMap<Class<? extends T>, SubtypeMapper<? extends T>>();
    }

    public <S extends T> SuperclassInfo<T> addSubtypeMapper( SubtypeMapper<S> subtypeMapper, Class<S> clazz, String typeInfo )
    {
        if ( includeTypeInfo )
        {
            subtypeInfoToMapper.put( typeInfo, subtypeMapper );
            subtypeClassToInfo.put( clazz, typeInfo );
        }
        subtypeClassToMapper.put( clazz, subtypeMapper );
        return this;
    }

    public boolean isIncludeTypeInfo()
    {
        return includeTypeInfo;
    }

    public As getInclude()
    {
        return include;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public SubtypeMapper<? extends T> getMapper( Class aClass )
    {
        return subtypeClassToMapper.get( aClass );
    }

    public SubtypeMapper<? extends T> getMapper( String typeInfo )
    {
        return subtypeInfoToMapper.get( typeInfo );
    }

    public String getTypeInfo( Class aClass )
    {
        return subtypeClassToInfo.get( aClass );
    }
}
