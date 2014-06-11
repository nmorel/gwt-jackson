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

package com.github.nmorel.gwtjackson.client.deser.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link JsonDeserializer} for beans.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonDeserializer<T> extends JsonDeserializer<T> implements InternalDeserializer<T,
        AbstractBeanJsonDeserializer<T>> {

    private final InstanceBuilder<T> instanceBuilder;

    private final SimpleStringMap<BeanPropertyDeserializer<T, ?>> deserializers;

    private final SimpleStringMap<BackReferenceProperty<T, ?>> backReferenceDeserializers;

    private final Set<String> defaultIgnoredProperties;

    private final Set<String> requiredProperties;

    private final IdentityDeserializationInfo<T> defaultIdentityInfo;

    private final TypeDeserializationInfo<T> defaultTypeInfo;

    private final Map<Class, SubtypeDeserializer> subtypeClassToDeserializer;

    protected AbstractBeanJsonDeserializer() {
        this.instanceBuilder = initInstanceBuilder();
        this.deserializers = initDeserializers();
        this.backReferenceDeserializers = initBackReferenceDeserializers();
        this.defaultIgnoredProperties = initIgnoredProperties();
        this.requiredProperties = initRequiredProperties();
        this.defaultIdentityInfo = initIdentityInfo();
        this.defaultTypeInfo = initTypeInfo();
        this.subtypeClassToDeserializer = initMapSubtypeClassToDeserializer();
    }

    /**
     * Initialize the {@link InstanceBuilder}. Returns null if the class isn't instantiable.
     */
    protected InstanceBuilder<T> initInstanceBuilder() {
        return null;
    }

    /**
     * Initialize the {@link SimpleStringMap} containing the property deserializers. Returns an empty map if there are no properties to
     * deserialize.
     */
    protected SimpleStringMap<BeanPropertyDeserializer<T, ?>> initDeserializers() {
        return SimpleStringMap.createObject().cast();
    }

    /**
     * Initialize the {@link SimpleStringMap} containing the back reference deserializers. Returns an empty map if there are no back
     * reference on the bean.
     */
    protected SimpleStringMap<BackReferenceProperty<T, ?>> initBackReferenceDeserializers() {
        return SimpleStringMap.createObject().cast();
    }

    /**
     * Initialize the {@link Set} containing the ignored property names. Returns an empty set if there are no ignored properties.
     */
    protected Set<String> initIgnoredProperties() {
        return Collections.emptySet();
    }

    /**
     * Initialize the {@link Set} containing the required property names. Returns an empty set if there are no required properties.
     */
    protected Set<String> initRequiredProperties() {
        return Collections.emptySet();
    }

    /**
     * Initialize the {@link IdentityDeserializationInfo}. Returns null if there is no {@link JsonIdentityInfo} annotation on bean.
     */
    protected IdentityDeserializationInfo<T> initIdentityInfo() {
        return null;
    }

    /**
     * Initialize the {@link TypeDeserializationInfo}. Returns null if there is no {@link JsonTypeInfo} annotation on bean.
     */
    protected TypeDeserializationInfo<T> initTypeInfo() {
        return null;
    }

    /**
     * Initialize the {@link Map} containing the {@link SubtypeDeserializer}. Returns an empty map if the bean has no subtypes.
     */
    protected Map<Class, SubtypeDeserializer> initMapSubtypeClassToDeserializer() {
        return Collections.emptyMap();
    }

    /**
     * Whether encountering of unknown
     * properties should result in a failure (by throwing a
     * {@link com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException}) or not.
     */
    protected boolean isDefaultIgnoreUnknown() {
        return false;
    }

    public abstract Class getDeserializedType();

    @Override
    public T doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {

        // Processing the parameters. We fallback to default if parameter is not present.
        final IdentityDeserializationInfo identityInfo = null == params.getIdentityInfo() ? defaultIdentityInfo : params.getIdentityInfo();
        final TypeDeserializationInfo typeInfo = null == params.getTypeInfo() ? defaultTypeInfo : params.getTypeInfo();

        JsonToken token = reader.peek();

        // If it's not a json object or array, it must be an identifier
        if ( null != identityInfo && !JsonToken.BEGIN_OBJECT.equals( token ) && !JsonToken.BEGIN_ARRAY.equals( token ) ) {
            Object id;
            if ( identityInfo.isProperty() ) {
                BeanPropertyDeserializer<T, ?> propertyDeserializer = deserializers.get( identityInfo.getPropertyName() );
                id = propertyDeserializer.getDeserializer().deserialize( reader, ctx );
            } else {
                id = identityInfo.readId( reader, ctx );
            }
            Object instance = ctx.getObjectWithId( identityInfo.newIdKey( id ) );
            if ( null == instance ) {
                throw ctx.traceError( "Cannot find an object with id " + id, reader );
            }
            return (T) instance;
        }

        T result;

        if ( null != typeInfo ) {

            As include;
            if ( JsonToken.BEGIN_ARRAY.equals( token ) ) {
                // in case of an enum subtype, we can have a wrapper array even if the user specified As.PROPERTY
                include = As.WRAPPER_ARRAY;
            } else {
                include = typeInfo.getInclude();
            }

            switch ( include ) {
                case PROPERTY:
                    // the type info is the first property of the object
                    reader.beginObject();
                    Map<String, String> bufferedProperties = null;
                    String typeInfoProperty = null;
                    while ( JsonToken.NAME.equals( reader.peek() ) ) {
                        String name = reader.nextName();

                        if ( typeInfo.getPropertyName().equals( name ) ) {
                            typeInfoProperty = reader.nextString();
                            break;
                        } else {
                            if ( null == bufferedProperties ) {
                                bufferedProperties = new HashMap<String, String>();
                            }
                            bufferedProperties.put( name, reader.nextValue() );
                        }
                    }
                    if ( null == typeInfoProperty ) {
                        throw ctx.traceError( "Cannot find the property " + typeInfo
                                .getPropertyName() + " containing the type information", reader );
                    }
                    result = getDeserializer( reader, ctx, typeInfo, typeInfoProperty )
                            .deserializeInline( reader, ctx, params, identityInfo, typeInfo, typeInfoProperty, bufferedProperties );
                    reader.endObject();
                    break;

                case WRAPPER_OBJECT:
                    // type info is included in a wrapper object that contains only one property. The name of this property is the type
                    // info and the value the object
                    reader.beginObject();
                    String typeInfoWrapObj = reader.nextName();
                    result = getDeserializer( reader, ctx, typeInfo, typeInfoWrapObj )
                            .deserializeWrapped( reader, ctx, params, identityInfo, typeInfo, typeInfoWrapObj );
                    reader.endObject();
                    break;

                case WRAPPER_ARRAY:
                    // type info is included in a wrapper array that contains two elements. First one is the type
                    // info and the second one the object
                    reader.beginArray();
                    String typeInfoWrapArray = reader.nextString();
                    result = getDeserializer( reader, ctx, typeInfo, typeInfoWrapArray )
                            .deserializeWrapped( reader, ctx, params, identityInfo, typeInfo, typeInfoWrapArray );
                    reader.endArray();
                    break;

                default:
                    throw ctx.traceError( "JsonTypeInfo.As." + typeInfo.getInclude() + " is not supported", reader );
            }
        } else if ( null != instanceBuilder ) {
            result = deserializeWrapped( reader, ctx, params, identityInfo, null, null );
        } else {
            throw ctx.traceError( "Cannot instantiate the type " + getDeserializedType().getName(), reader );
        }

        return result;
    }

    @Override
    public T deserializeWrapped( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                 IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation ) {
        reader.beginObject();
        T result = deserializeInline( reader, ctx, params, identityInfo, typeInfo, typeInformation, null );
        reader.endObject();
        return result;
    }

    /**
     * Deserializes all the properties of the bean. The {@link JsonReader} must be in a json object.
     *
     * @param reader reader
     * @param ctx context of the deserialization process
     * @param type in case of a subtype, it's the corresponding type value
     * @param bufferedProperties Buffered properties in case the type info property was not in 1st position
     */
    @Override
    public final T deserializeInline( final JsonReader reader, final JsonDeserializationContext ctx, JsonDeserializerParameters params, IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String type,
                                      Map<String, String> bufferedProperties ) {
        final boolean ignoreUnknown = params.isIgnoreUnknown() || isDefaultIgnoreUnknown();
        final Set<String> ignoredProperties;
        if ( null == params.getIgnoredProperties() ) {
            ignoredProperties = defaultIgnoredProperties;
        } else {
            ignoredProperties = new HashSet<String>( defaultIgnoredProperties );
            ignoredProperties.addAll( params.getIgnoredProperties() );
        }

        // we will remove the properties read from this list and check at the end it's empty
        Set<String> requiredPropertiesLeft = requiredProperties.isEmpty() ? Collections
                .<String>emptySet() : new HashSet<String>( requiredProperties );

        // we first instantiate the bean. It might buffer properties if there are properties required for constructor and they are not in
        // first position
        Instance<T> instance = instanceBuilder.newInstance( reader, ctx, bufferedProperties );

        T bean = instance.getInstance();

        // we then look for identity. It can also buffer properties it is not in current reader position.
        bufferedProperties = readIdentityProperty( identityInfo, bean, instance.getBufferedProperties(), reader, ctx, ignoredProperties );

        // we flush any buffered properties
        flushBufferedProperties( bean, bufferedProperties, requiredPropertiesLeft, ctx, ignoreUnknown, ignoredProperties );

        // in case there is a property that need the type info
        if ( null != typeInfo && null != typeInfo.getPropertyName() && null != type ) {
            BeanPropertyDeserializer deserializer = getPropertyDeserializer( typeInfo.getPropertyName(), ctx, true );
            if ( null != deserializer ) {
                deserializer.setValue( bean, type, ctx );
            }
        }

        while ( JsonToken.NAME.equals( reader.peek() ) ) {
            String propertyName = reader.nextName();

            requiredPropertiesLeft.remove( propertyName );

            if ( ignoredProperties.contains( propertyName ) ) {
                reader.skipValue();
                continue;
            }

            BeanPropertyDeserializer<T, ?> property = getPropertyDeserializer( propertyName, ctx, ignoreUnknown );
            if ( null == property ) {
                reader.skipValue();
            } else {
                property.deserialize( reader, bean, ctx );
            }
        }

        if ( !requiredPropertiesLeft.isEmpty() ) {
            throw ctx.traceError( "Required properties are missing : " + requiredPropertiesLeft, reader );
        }
        return bean;
    }

    private Map<String, String> readIdentityProperty( IdentityDeserializationInfo identityInfo, T bean, Map<String,
            String> bufferedProperties, JsonReader reader, final JsonDeserializationContext ctx, Set<String> ignoredProperties ) {
        if ( null == identityInfo ) {
            return bufferedProperties;
        }

        JsonReader identityReader = null;

        // we look if it has not been already buffered
        String propertyValue = null;

        // we fisrt look if the identity property has not been read already
        if ( null != bufferedProperties ) {
            propertyValue = bufferedProperties.remove( identityInfo.getPropertyName() );
        }

        if ( null != propertyValue ) {
            identityReader = ctx.newJsonReader( propertyValue );
        } else {
            // we search for the identity property
            while ( JsonToken.NAME.equals( reader.peek() ) ) {
                String name = reader.nextName();

                if ( ignoredProperties.contains( name ) ) {
                    reader.skipValue();
                    continue;
                }

                if ( identityInfo.getPropertyName().equals( name ) ) {
                    identityReader = reader;
                    break;
                } else {
                    if ( null == bufferedProperties ) {
                        bufferedProperties = new HashMap<String, String>();
                    }
                    bufferedProperties.put( name, reader.nextValue() );
                }
            }
        }

        if ( null != identityReader ) {
            Object id;
            if ( identityInfo.isProperty() ) {
                BeanPropertyDeserializer propertyDeserializer = deserializers.get( identityInfo.getPropertyName() );
                id = propertyDeserializer.getDeserializer().deserialize( identityReader, ctx );
                if ( null != id ) {
                    propertyDeserializer.setValue( bean, id, ctx );
                }
            } else {
                id = identityInfo.readId( identityReader, ctx );
            }
            if ( null != id ) {
                ctx.addObjectId( identityInfo.newIdKey( id ), bean );
            }
        }

        return bufferedProperties;
    }

    private void flushBufferedProperties( T bean, Map<String, String> bufferedProperties, Set<String> requiredPropertiesLeft,
                                          JsonDeserializationContext ctx, boolean ignoreUnknown, Set<String> ignoredProperties ) {
        if ( null != bufferedProperties && !bufferedProperties.isEmpty() ) {
            for ( Entry<String, String> bufferedProperty : bufferedProperties.entrySet() ) {
                String propertyName = bufferedProperty.getKey();

                requiredPropertiesLeft.remove( propertyName );

                if ( ignoredProperties.contains( propertyName ) ) {
                    continue;
                }

                BeanPropertyDeserializer<T, ?> property = getPropertyDeserializer( propertyName, ctx, ignoreUnknown );
                if ( null != property ) {
                    property.deserialize( ctx.newJsonReader( bufferedProperty.getValue() ), bean, ctx );
                }
            }
        }
    }

    private BeanPropertyDeserializer<T, ?> getPropertyDeserializer( String propertyName, JsonDeserializationContext ctx,
                                                                    boolean ignoreUnknown ) {
        BeanPropertyDeserializer<T, ?> property = deserializers.get( propertyName );
        if ( null == property ) {
            if ( !ignoreUnknown && ctx.isFailOnUnknownProperties() ) {
                throw ctx.traceError( "Unknown property '" + propertyName + "'" );
            }
        }
        return property;
    }

    private InternalDeserializer<T, ? extends JsonDeserializer<T>> getDeserializer( JsonReader reader, JsonDeserializationContext ctx,
                                                                                    TypeDeserializationInfo typeInfo,
                                                                                    String typeInformation ) {
        Class typeClass = typeInfo.getTypeClass( typeInformation );
        if ( null == typeClass ) {
            throw ctx.traceError( "Could not find the type associated to " + typeInformation, reader );
        }

        return getDeserializer( reader, ctx, typeClass );
    }

    private InternalDeserializer<T, ? extends JsonDeserializer<T>> getDeserializer( JsonReader reader, JsonDeserializationContext ctx,
                                                                                    Class typeClass ) {
        if ( typeClass == getDeserializedType() ) {
            return this;
        }

        SubtypeDeserializer deserializer = subtypeClassToDeserializer.get( typeClass );
        if ( null == deserializer ) {
            throw ctx.traceError( "No deserializer found for the type " + typeClass.getName(), reader );
        }
        return deserializer;
    }

    @Override
    public AbstractBeanJsonDeserializer<T> getDeserializer() {
        return this;
    }

    @Override
    public void setBackReference( String referenceName, Object reference, T value, JsonDeserializationContext ctx ) {
        if ( null == value ) {
            return;
        }

        JsonDeserializer<T> deserializer = getDeserializer( null, ctx, value.getClass() ).getDeserializer();
        if ( deserializer.getClass() != getClass() ) {
            // we test if it's not this deserializer to avoid an infinite loop
            deserializer.setBackReference( referenceName, reference, value, ctx );
            return;
        }

        BackReferenceProperty backReferenceProperty = backReferenceDeserializers.get( referenceName );
        if ( null == backReferenceProperty ) {
            throw ctx.traceError( "The back reference '" + referenceName + "' does not exist" );
        }
        backReferenceProperty.setBackReference( value, reference, ctx );
    }
}
