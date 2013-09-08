package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonMapper} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractSuperclassJsonMapper<T, B extends AbstractBeanJsonMapper.InstanceBuilder<T>> extends
    AbstractBeanJsonMapper<T, B>
{
    public static interface SubtypeMapper<T>
    {
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

    protected AbstractSuperclassJsonMapper()
    {
        this( null, null, false );
    }

    protected AbstractSuperclassJsonMapper( JsonTypeInfo.As include, String propertyName )
    {
        this( include, propertyName, true );
    }

    private AbstractSuperclassJsonMapper( JsonTypeInfo.As include, String propertyName, boolean includeTypeInfo )
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
        initSubtypeMappers();
    }

    protected abstract void initSubtypeMappers();

    protected <S extends T> void addSubtypeMapper( SubtypeMapper<S> subtypeMapper, Class<S> clazz, String typeInfo )
    {
        if ( includeTypeInfo )
        {
            subtypeInfoToMapper.put( typeInfo, subtypeMapper );
            subtypeClassToInfo.put( clazz, typeInfo );
        }
        subtypeClassToMapper.put( clazz, subtypeMapper );
    }

    protected abstract boolean isInstantiable();

    @Override
    public T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        T result;
        if ( !includeTypeInfo )
        {
            reader.beginObject();
            result = doDecode( reader, ctx, null );
            reader.endObject();
        }
        else
        {
            switch ( include )
            {
                case PROPERTY:
                    // the type info is the first property of the object
                    reader.beginObject();
                    String name = reader.nextName();
                    if ( !propertyName.equals( name ) )
                    {
                        // the type info is always the first value. If we don't find it, we throw an error
                        throw ctx.traceError( "Cannot find the type info" );
                    }
                    String typeInfoProperty = reader.nextString();

                    result = doDecode( reader, ctx, typeInfoProperty );
                    reader.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    reader.beginObject();
                    String typeInfoWrapObj = reader.nextName();
                    reader.beginObject();
                    result = doDecode( reader, ctx, typeInfoWrapObj );
                    reader.endObject();
                    reader.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    reader.beginArray();
                    String typeInfoWrapArray = reader.nextString();
                    reader.beginObject();
                    result = doDecode( reader, ctx, typeInfoWrapArray );
                    reader.endObject();
                    reader.endArray();
                    break;

                default:
                    throw ctx.traceError( "JsonTypeInfo.As." + include + " is not supported" );
            }
        }

        return result;
    }

    private T doDecode( JsonReader reader, JsonDecodingContext ctx, String typeInfo ) throws IOException
    {
        if ( null == typeInfo )
        {
            if ( isInstantiable() )
            {
                return decodeObject( reader, ctx );
            }
            else
            {
                throw ctx.traceError( "Cannot decode the object. There is no type info and the type is not instantiable." );
            }
        }

        SubtypeMapper<? extends T> mapper = subtypeInfoToMapper.get( typeInfo );
        if ( null == mapper )
        {
            throw ctx.traceError( "No mapper found for the type " + typeInfo );
        }

        return mapper.decodeObject( reader, ctx );
    }

    @Override
    public void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        SubtypeMapper mapper = subtypeClassToMapper.get( value.getClass() );
        if ( null == mapper )
        {
            throw ctx.traceError( value, "Cannot find mapper for class " + value.getClass() );
        }

        if ( !includeTypeInfo )
        {
            // we don't include type info so we just encode the properties
            writer.beginObject();
            mapper.encodeObject( writer, value, ctx );
            writer.endObject();
        }
        else
        {
            String typeInfo = subtypeClassToInfo.get( value.getClass() );
            if ( null == typeInfo )
            {
                throw ctx.traceError( value, "Cannot find type info for class " + value.getClass() );
            }

            switch ( include )
            {
                case PROPERTY:
                    // type info is included as a property of the object
                    writer.beginObject();
                    writer.name( propertyName );
                    writer.value( typeInfo );
                    mapper.encodeObject( writer, value, ctx );
                    writer.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    writer.beginObject();
                    writer.name( typeInfo );
                    writer.beginObject();
                    mapper.encodeObject( writer, value, ctx );
                    writer.endObject();
                    writer.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    writer.beginArray();
                    writer.value( typeInfo );
                    writer.beginObject();
                    mapper.encodeObject( writer, value, ctx );
                    writer.endObject();
                    writer.endArray();
                    break;

                default:
                    throw ctx.traceError( value, "JsonTypeInfo.As." + include + " is not supported" );
            }
        }
    }
}
