package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonMapper} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractSuperclassJsonMapper<T> extends AbstractBeanJsonMapper<T>
{
    public static interface SubtypeMapper<T>
    {
        T decodeObject( JsonReader reader, JsonDecodingContext ctx ) throws IOException;

        void encodeObject( JsonWriter writer, T bean, JsonEncodingContext ctx ) throws IOException;
    }

    /** Name of the property containing information about the subtype */
    private final String propertyName;
    private Map<String, SubtypeMapper<? extends T>> subtypeMappers;
    private Map<Class<? extends T>, String> subtypeInfos;

    protected AbstractSuperclassJsonMapper()
    {
        this( null );
    }

    protected AbstractSuperclassJsonMapper( String propertyName )
    {
        this.propertyName = propertyName;
        this.subtypeMappers = new HashMap<String, SubtypeMapper<? extends T>>();
        this.subtypeInfos = new HashMap<Class<? extends T>, String>();
        initSubtypeMappers();
    }

    protected abstract void initSubtypeMappers();

    protected <S extends T> void addSubtypeMapper( SubtypeMapper<S> subtypeMapper, String typeInfo, Class<S> clazz )
    {
        subtypeMappers.put( typeInfo, subtypeMapper );
        subtypeInfos.put( clazz, typeInfo );
    }

    protected abstract boolean isInstantiable();

    @Override
    public T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        reader.beginObject();

        T result;

        if ( null == propertyName || !JsonToken.NAME.equals( reader.peek() ) )
        {
            // no @JsonTypeInfo on type or the object is empty. If this class is instantiable, we decode it.
            if ( isInstantiable() )
            {
                result = decodeObject( reader, ctx );
            }
            else
            {
                throw new JsonDecodingException( "Cannot instantiate the type" );
            }
        }
        else
        {
            String name = reader.nextName();
            if ( !propertyName.equals( name ) )
            {
                // the type info is always the first value. If we don't find it, we throw an error
                throw new JsonDecodingException( "Cannot find the type info" );
            }

            String typeInfo = reader.nextString();

            SubtypeMapper<? extends T> mapper = subtypeMappers.get( typeInfo );
            if ( null == mapper )
            {
                throw new JsonDecodingException( "No mapper found for the type " + typeInfo );
            }

            result = mapper.decodeObject( reader, ctx );
        }

        reader.endObject();

        return result;
    }

    @Override
    public void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        writer.beginObject();
        String typeInfo = subtypeInfos.get( value.getClass() );
        if ( null == typeInfo )
        {
            encodeObject( writer, value, ctx );
        }
        else
        {
            SubtypeMapper mapper = subtypeMappers.get( typeInfo );
            if ( null == mapper )
            {
                throw new JsonEncodingException( "No mapper found for the type " + typeInfo );
            }

            if ( null != propertyName )
            {
                // we write the type info
                writer.name( propertyName );
                writer.value( typeInfo );
            }

            // we write the rest of the properties
            mapper.encodeObject( writer, value, ctx );
        }
        writer.endObject();
    }
}
