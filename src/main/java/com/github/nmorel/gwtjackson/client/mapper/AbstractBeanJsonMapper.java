package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.JsonMappingContext;
import com.github.nmorel.gwtjackson.client.mapper.SuperclassInfo.SubtypeMapper;
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

        void addCallback( InstanceBuilderCallback<T> callback );
    }

    public static interface InstanceBuilderCallback<T>
    {
        void onInstanceCreated( T instance );
    }

    public static interface DecoderProperty<T, B extends AbstractBeanJsonMapper.InstanceBuilder<T>>
    {
        Object decode( JsonReader reader, B builder, JsonDecodingContext ctx );
    }

    public static interface BackReferenceProperty<T, R>
    {
        void setBackReference( T value, R reference, JsonDecodingContext ctx );
    }

    public static interface EncoderProperty<T>
    {
        void encode( JsonWriter writer, T bean, JsonEncodingContext ctx );
    }

    public static interface IdProperty<T>
    {
        boolean isAlwaysAsId();

        String getPropertyName();

        JsonMapper<?> getMapper( JsonMappingContext ctx );

        ObjectIdEncoder<?> getObjectId( T bean, JsonEncodingContext ctx );

        IdKey getIdKey( JsonReader reader, JsonDecodingContext ctx );

        IdKey newIdKey( Object id );
    }

    private IdProperty<T> idProperty;
    private SuperclassInfo<T> superclassInfo;
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

    protected abstract boolean isInstantiable();

    protected abstract B newInstanceBuilder( JsonDecodingContext ctx );

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
        if ( null != idProperty && !JsonToken.BEGIN_OBJECT.equals( reader.peek() ) )
        {
            IdKey id = idProperty.getIdKey( reader, ctx );
            Object instance = ctx.getObjectWithId( id );
            if ( null == instance )
            {
                throw ctx.traceError( "Cannot find an object with id " + id );
            }
            return (T) instance;
        }

        T result;

        if ( null != superclassInfo && superclassInfo.isIncludeTypeInfo() )
        {
            switch ( superclassInfo.getInclude() )
            {
                case PROPERTY:
                    // the type info is the first property of the object
                    reader.beginObject();
                    String name = reader.nextName();
                    if ( !superclassInfo.getPropertyName().equals( name ) )
                    {
                        // the type info is always the first value. If we don't find it, we throw an error
                        throw ctx.traceError( "Cannot find the type info" );
                    }
                    String typeInfoProperty = reader.nextString();

                    result = decodeSubtype( reader, ctx, typeInfoProperty );
                    reader.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    reader.beginObject();
                    String typeInfoWrapObj = reader.nextName();
                    reader.beginObject();
                    result = decodeSubtype( reader, ctx, typeInfoWrapObj );
                    reader.endObject();
                    reader.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    reader.beginArray();
                    String typeInfoWrapArray = reader.nextString();
                    reader.beginObject();
                    result = decodeSubtype( reader, ctx, typeInfoWrapArray );
                    reader.endObject();
                    reader.endArray();
                    break;

                default:
                    throw ctx.traceError( "JsonTypeInfo.As." + superclassInfo.getInclude() + " is not supported" );
            }
        }
        else if ( isInstantiable() )
        {
            reader.beginObject();
            result = decodeObject( reader, ctx );
            reader.endObject();
        }
        else
        {
            throw ctx.traceError( "Cannot instantiate the type" );
        }

        return result;
    }

    public final T decodeObject( final JsonReader reader, final JsonDecodingContext ctx ) throws IOException
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
            if ( null != idProperty )
            {
                final Object id;
                if ( null == property )
                {
                    id = idProperty.getMapper( ctx ).decode( reader, ctx );
                }
                else
                {
                    id = property.decode( reader, builder, ctx );
                }
                builder.addCallback( new InstanceBuilderCallback<T>()
                {
                    @Override
                    public void onInstanceCreated( T instance )
                    {
                        ctx.addObjectId( idProperty.newIdKey( id ), instance );
                    }
                } );
            }
            else if ( null == property )
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

    public final T decodeSubtype( JsonReader reader, JsonDecodingContext ctx, String typeInfo ) throws IOException
    {
        SubtypeMapper<? extends T> mapper = superclassInfo.getMapper( typeInfo );
        if ( null == mapper )
        {
            throw ctx.traceError( "No mapper found for the type " + typeInfo );
        }

        return mapper.decodeObject( reader, ctx );
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T value, JsonDecodingContext ctx )
    {
        if ( null == value )
        {
            return;
        }

        if ( null != superclassInfo )
        {
            SubtypeMapper mapper = superclassInfo.getMapper( value.getClass() );
            if ( null == mapper )
            {
                // should never happen, the generator add every subtype to the map
                throw ctx.traceError( "Cannot find mapper for class " + value.getClass() );
            }
            JsonMapper<T> jsonMapper = mapper.getMapper( ctx );
            if ( jsonMapper != this )
            {
                // we test if it's not this mapper to avoid an infinite loop
                jsonMapper.setBackReference( referenceName, reference, value, ctx );
                return;
            }
        }

        initDecodersIfNeeded();
        BackReferenceProperty backReferenceProperty = backReferenceDecoders.get( referenceName );
        if ( null == backReferenceProperty )
        {
            throw ctx.traceError( "The back reference '" + referenceName + "' does not exist" );
        }
        backReferenceProperty.setBackReference( value, reference, ctx );
    }

    @Override
    public void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException
    {
        ObjectIdEncoder<?> idWriter = null;
        if ( null != idProperty )
        {
            idWriter = ctx.getObjectId( value );
            if ( null != idWriter )
            {
                // the bean has already been encoded, we just encode the id-
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
        }

        if ( null != superclassInfo )
        {
            SubtypeMapper mapper = superclassInfo.getMapper( value.getClass() );
            if ( null == mapper )
            {
                throw ctx.traceError( value, "Cannot find mapper for class " + value.getClass() );
            }

            if ( !superclassInfo.isIncludeTypeInfo() )
            {
                // we don't include type info so we just encode the properties
                writer.beginObject();
                if ( null != idWriter )
                {
                    writer.name( idProperty.getPropertyName() );
                    idWriter.encodeId( writer, ctx );
                }
                mapper.encodeObject( writer, value, ctx );
                writer.endObject();
            }
            else
            {
                String typeInfo = superclassInfo.getTypeInfo( value.getClass() );
                if ( null == typeInfo )
                {
                    throw ctx.traceError( value, "Cannot find type info for class " + value.getClass() );
                }

                switch ( superclassInfo.getInclude() )
                {
                    case PROPERTY:
                        // type info is included as a property of the object
                        writer.beginObject();
                        writer.name( superclassInfo.getPropertyName() );
                        writer.value( typeInfo );
                        if ( null != idWriter )
                        {
                            writer.name( idProperty.getPropertyName() );
                            idWriter.encodeId( writer, ctx );
                        }
                        mapper.encodeObject( writer, value, ctx );
                        writer.endObject();
                        break;

                    case WRAPPER_OBJECT:
                        // type info is included in a wrapper object that contains only one property. The name of this property is the type
                        // info and the value the object
                        writer.beginObject();
                        writer.name( typeInfo );
                        writer.beginObject();
                        if ( null != idWriter )
                        {
                            writer.name( idProperty.getPropertyName() );
                            idWriter.encodeId( writer, ctx );
                        }
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
                        if ( null != idWriter )
                        {
                            writer.name( idProperty.getPropertyName() );
                            idWriter.encodeId( writer, ctx );
                        }
                        mapper.encodeObject( writer, value, ctx );
                        writer.endObject();
                        writer.endArray();
                        break;

                    default:
                        throw ctx.traceError( value, "JsonTypeInfo.As." + superclassInfo.getInclude() + " is not supported" );
                }
            }
        }
        else
        {
            writer.beginObject();
            if ( null != idWriter )
            {
                writer.name( idProperty.getPropertyName() );
                idWriter.encodeId( writer, ctx );
            }
            encodeObject( writer, value, ctx );
            writer.endObject();
        }
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

    public void setIdProperty( IdProperty<T> idProperty )
    {
        this.idProperty = idProperty;
    }

    public void setSuperclassInfo( SuperclassInfo<T> superclassInfo )
    {
        this.superclassInfo = superclassInfo;
    }
}
