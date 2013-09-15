package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.utils.ObjectIdEncoder;

/**
 * Base implementation of {@link JsonMapper} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonMapper<T, B extends AbstractBeanJsonMapper.InstanceBuilder<T>> extends AbstractJsonMapper<T>
{
    public static interface InstanceBuilder<T>
    {
        T build( JsonDecodingContext ctx );
    }

    public static interface DecoderProperty<T, B extends AbstractBeanJsonMapper.InstanceBuilder<T>>
    {
        void decode( JsonReader reader, B builder, JsonDecodingContext ctx );
    }

    public static interface BackReferenceProperty<T, R>
    {
        void setBackReference( T value, R reference, JsonDecodingContext ctx );
    }

    public static interface EncoderProperty<T>
    {
        void encode( JsonWriter writer, T bean, JsonEncodingContext ctx );
    }

    public static interface IdProperty<T, B extends AbstractBeanJsonMapper.InstanceBuilder<T>> extends DecoderProperty<T, B>
    {
        boolean isAlwaysAsId();

        String getPropertyName();

        ObjectIdEncoder<?> getObjectId( T bean, JsonEncodingContext ctx );

        IdKey getObjectId( JsonReader reader, JsonDecodingContext ctx );
    }

    private Map<String, DecoderProperty<T, B>> decoders;
    private Map<String, EncoderProperty<T>> encoders;
    private Map<String, BackReferenceProperty<T, ?>> backReferenceDecoders;

    protected void initDecodersIfNeeded()
    {
        if ( null == decoders )
        {
            decoders = new LinkedHashMap<String, DecoderProperty<T, B>>();
            backReferenceDecoders = new LinkedHashMap<String, BackReferenceProperty<T, ?>>();
            initDecoders();
        }
    }

    protected abstract void initDecoders();

    protected void initEncodersIfNeeded()
    {
        if ( null == encoders )
        {
            encoders = new LinkedHashMap<String, EncoderProperty<T>>();
            initEncoders();
        }
    }

    protected abstract void initEncoders();

    protected abstract B newInstanceBuilder( JsonDecodingContext ctx );

    protected IdProperty<T, B> getIdProperty()
    {
        return null;
    }

    /**
     * Add a {@link DecoderProperty}
     *
     * @param propertyName name of the property
     * @param decoder decoder
     */
    protected void addProperty( String propertyName, DecoderProperty<T, B> decoder )
    {
        decoders.put( propertyName, decoder );
    }

    /**
     * Add an {@link EncoderProperty}
     *
     * @param propertyName name of the property
     * @param encoder encoder
     */
    protected void addProperty( String propertyName, EncoderProperty<T> encoder )
    {
        encoders.put( propertyName, encoder );
    }

    /**
     * Add a {@link BackReferenceProperty}
     *
     * @param referenceName name of the reference
     * @param backReference backReference
     */
    protected void addProperty( String referenceName, BackReferenceProperty<T, ?> backReference )
    {
        backReferenceDecoders.put( referenceName, backReference );
    }

    @Override
    public T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        IdProperty<T, B> idProperty = getIdProperty();
        if ( null != idProperty && !JsonToken.BEGIN_OBJECT.equals( reader.peek() ) )
        {
            IdKey id = idProperty.getObjectId( reader, ctx );
            Object instance = ctx.getObjectWithId( id );
            if ( null == instance )
            {
                throw ctx.traceError( "Cannot find an object with id " + id );
            }
            return (T) instance;
        }

        reader.beginObject();
        T result = decodeObject( reader, ctx );
        reader.endObject();
        return result;
    }

    public final T decodeObject( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        initDecodersIfNeeded();

        B builder = newInstanceBuilder( ctx );

        while ( JsonToken.NAME.equals( reader.peek() ) )
        {
            String name = reader.nextName();
            if ( JsonToken.NULL.equals( reader.peek() ) )
            {
                reader.skipValue();
                continue;
            }

            DecoderProperty<T, B> property = decoders.get( name );
            if ( null == property )
            {
                // TODO add a configuration to tell if we skip or throw an unknown property
                reader.skipValue();
            }
            else
            {
                property.decode( reader, builder, ctx );
            }
        }

        return builder.build( ctx );
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T value, JsonDecodingContext ctx )
    {
        if ( null != value )
        {
            initDecodersIfNeeded();
            BackReferenceProperty backReferenceProperty = backReferenceDecoders.get( referenceName );
            if ( null == backReferenceProperty )
            {
                throw ctx.traceError( "The back reference '" + referenceName + "' does not exist" );
            }
            backReferenceProperty.setBackReference( value, reference, ctx );
        }
    }

    @Override
    public void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        IdProperty<T, B> idProperty = getIdProperty();
        if ( null != idProperty )
        {
            ObjectIdEncoder<?> idWriter = ctx.getObjectId( value );
            if ( null != idWriter )
            {
                idWriter.encodeId( writer, ctx );
                return;
            }

            idWriter = idProperty.getObjectId( value, ctx );
            ctx.addObjectId( value, idWriter );
            if ( idProperty.isAlwaysAsId() )
            {
                idWriter.encodeId( writer, ctx );
                return;
            }
            else
            {
                writer.beginObject();
                writer.name( idProperty.getPropertyName() );
                idWriter.encodeId( writer, ctx );
            }
        }
        else
        {
            writer.beginObject();
        }

        encodeObject( writer, value, ctx );
        writer.endObject();
    }

    public final void encodeObject( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        initEncodersIfNeeded();

        for ( Map.Entry<String, EncoderProperty<T>> entry : encoders.entrySet() )
        {
            writer.name( entry.getKey() );
            entry.getValue().encode( writer, value, ctx );
        }
    }

}
