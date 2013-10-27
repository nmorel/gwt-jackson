package com.github.nmorel.gwtjackson.client.deser.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Contains identity informations for deserialization process.
 *
 * @author Nicolas Morel
 */
public abstract class IdentityDeserializationInfo<T, V> extends HasDeserializer<V, JsonDeserializer<V>> {

    /**
     * Name of the property holding the identity
     */
    private final String propertyName;

    /**
     * Type of {@link ObjectIdGenerator} used for generating Object Id
     */
    private final Class<?> type;

    /**
     * Scope of the Object Id (may be null, to denote global)
     */
    private final Class<?> scope;

    protected IdentityDeserializationInfo( String propertyName, Class<?> type, Class<?> scope ) {
        this.propertyName = propertyName;
        this.type = type;
        this.scope = scope;
    }

    public final String getPropertyName() {
        return propertyName;
    }

    /**
     * Reads the id and returns an {@link IdKey} to the context.
     *
     * @param reader reader
     * @param ctx context of the deserialization process
     *
     * @return an {@link IdKey}
     */
    public final IdKey readIdKey( JsonReader reader, JsonDeserializationContext ctx ) {
        return new IdKey( type, scope, readId( reader, ctx ) );
    }

    /**
     * Reads the id and adds an {@link IdKey} to the context.
     *
     * @param reader reader
     * @param bean the bean to associate the id to
     * @param ctx context of the deserialization process
     */
    public final void readAndAddIdToContext( JsonReader reader, T bean, JsonDeserializationContext ctx ) {
        V id = readId( reader, ctx );
        if ( null != id ) {
            setIdToBean( bean, id );
            ctx.addObjectId( new IdKey( type, scope, id ), bean );
        }
    }

    private V readId( JsonReader reader, JsonDeserializationContext ctx ) {
        return getDeserializer( ctx ).deserialize( reader, ctx );
    }

    /**
     * Override this method to set the id into the bean
     *
     * @param bean the bean to associate the id to
     * @param id id
     */
    protected void setIdToBean( T bean, V id ) {
        // by default, we do nothing. The child has to override this method if the id is a property of the bean and need to be set
    }
}
