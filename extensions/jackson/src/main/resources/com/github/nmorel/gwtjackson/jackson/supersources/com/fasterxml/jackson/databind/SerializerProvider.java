package com.fasterxml.jackson.databind;

import java.io.IOException;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.github.nmorel.gwtjackson.jackson.client.util.ClassUtils;
import com.github.nmorel.gwtjackson.jackson.client.util.StringUtil;

/**
 * Class that defines API used by {@link ObjectMapper} and
 * {@link JsonSerializer}s to obtain serializers capable of serializing
 * instances of specific types; as well as the default implementation
 * of the functionality.
 *<p>
 * Provider handles caching aspects of serializer handling; all construction
 * details are delegated to {@link SerializerFactory} instance.
 *<p>
 * Object life-cycle is such that an initial instance ("blueprint") is created
 * and referenced by {@link ObjectMapper} and {@link ObjectWriter} intances;
 * but for actual usage, a configured instance is created by using
 * a create method in sub-class
 * {@link com.fasterxml.jackson.databind.ser.DefaultSerializerProvider}.
 * Only this instance can be used for actual serialization calls; blueprint
 * object is only to be used for creating instances.
 */
public abstract class SerializerProvider
        extends DatabindContext {

    /**
     * Setting for determining whether mappings for "unknown classes" should be
     * cached for faster resolution. Usually this isn't needed, but maybe it
     * is in some cases?
     */
    protected final static boolean CACHE_UNKNOWN_MAPPINGS = false;

    public final static JsonSerializer<Object> DEFAULT_NULL_KEY_SERIALIZER =
            new FailingSerializer("Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)");

    /**
     * Placeholder serializer used when <code>java.lang.Object</code> typed property
     * is marked to be serialized.
     *<br>
     * NOTE: starting with 2.6, this instance is NOT used for any other types, and
     * separate instances are constructed for "empty" Beans.
     *<p>
     * NOTE: changed to <code>protected</code> for 2.3; no need to be publicly available.
     */
    protected final static JsonSerializer<Object> DEFAULT_UNKNOWN_SERIALIZER = new UnknownSerializer();

    /*
    /**********************************************************
    /* Configuration, general
    /**********************************************************
     */

    /**
     * Serialization configuration to use for serialization processing.
     */
    final protected SerializationConfig _config;

    /**
     * View used for currently active serialization, if any.
     * Only set for non-blueprint instances.
     */
    final protected Class<?> _serializationView;

    /*
    /**********************************************************
    /* Configuration, factories
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Helper objects for caching, reuse
    /**********************************************************
     */

    /**
     * Cache for doing type-to-value-serializer lookups.
     */
    final protected SerializerCache _serializerCache;

    /**
     * Lazily-constructed holder for per-call attributes.
     * Only set for non-blueprint instances.
     * 
     * @since 2.3
     */
    protected transient ContextAttributes _attributes;

    /*
    /**********************************************************
    /* Configuration, specialized serializers
    /**********************************************************
     */

    /**
     * Serializer that gets called for values of types for which no
     * serializers can be constructed.
     *<p>
     * The default serializer will simply thrown an exception.
     */
    protected JsonSerializer<Object> _unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;

    /**
     * Serializer used to output non-null keys of Maps (which will get
     * output as JSON Objects), if not null; if null, us the standard
     * default key serializer.
     */
    protected JsonSerializer<Object> _keySerializer;

    /**
     * Serializer used to output a null value. Default implementation
     * writes nulls using {@link JsonGenerator#writeNull}.
     */
    protected JsonSerializer<Object> _nullValueSerializer = NullSerializer.instance;

    /**
     * Serializer used to (try to) output a null key, due to an entry of
     * {@link java.util.Map} having null key.
     * The default implementation will throw an exception if this happens;
     * alternative implementation (like one that would write an Empty String)
     * can be defined.
     */
    protected JsonSerializer<Object> _nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;

    /*
    /**********************************************************
    /* State, for non-blueprint instances: generic
    /**********************************************************
     */

    /**
     * For fast lookups, we will have a local non-shared read-only
     * map that contains serializers previously fetched.
     */
    protected final ReadOnlyClassToSerializerMap _knownSerializers;

    /**
     * Flag set to indicate that we are using vanilla null value serialization
     * 
     * @since 2.3
     */
    protected final boolean _stdNullValueSerializer;

    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

    /**
     * Constructor for creating master (or "blue-print") provider object,
     * which is only used as the template for constructing per-binding
     * instances.
     */
    public SerializerProvider() {
        _config = null;
        _serializerCache = new SerializerCache();
        // Blueprints doesn't have access to any serializers...
        _knownSerializers = null;

        _serializationView = null;
        _attributes = null;

        // not relevant for blueprint instance, could set either way:
        _stdNullValueSerializer = true;
    }

    /**
     * Copy-constructor used when making a copy of a blueprint instance.
     * 
     * @since 2.5
     */
    protected SerializerProvider(SerializerProvider src) {
        // since this is assumed to be a blue-print instance, many settings missing:
        _config = null;
        _serializationView = null;
        _knownSerializers = null;

        // and others initialized to default empty state
        _serializerCache = new SerializerCache();

        _unknownTypeSerializer = src._unknownTypeSerializer;
        _keySerializer = src._keySerializer;
        _nullValueSerializer = src._nullValueSerializer;
        _nullKeySerializer = src._nullKeySerializer;

        _stdNullValueSerializer = src._stdNullValueSerializer;
    }

    /*
    /**********************************************************
    /* Methods for configuring default settings
    /**********************************************************
     */

    /**
     * Method that can be used to specify serializer that will be
     * used to write JSON property names matching null keys for Java
     * Maps (which will throw an exception if try write such property
     * name)
     */
    public void setDefaultKeySerializer(JsonSerializer<Object> ks) {
        if (ks == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        _keySerializer = ks;
    }

    /**
     * Method that can be used to specify serializer that will be
     * used to write JSON values matching Java null values
     * instead of default one (which simply writes JSON null).
     *<p>
     * Note that you can get finer control over serializer to use by overriding
     * {@link #findNullValueSerializer}, which gets called once per each
     * property.
     */
    public void setNullValueSerializer(JsonSerializer<Object> nvs) {
        if (nvs == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        _nullValueSerializer = nvs;
    }

    /**
     * Method that can be used to specify serializer to use for serializing
     * all non-null JSON property names, unless more specific key serializer
     * is found (i.e. if not custom key serializer has been registered for
     * Java type).
     *<p>
     * Note that key serializer registration are different from value serializer
     * registrations.
     */
    public void setNullKeySerializer(JsonSerializer<Object> nks) {
        if (nks == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        _nullKeySerializer = nks;
    }

    /*
    /**********************************************************
    /* DatabindContext implementation (and closely related
    /* but ser-specific)
    /**********************************************************
     */

    @Override
    public final Class<?> getActiveView() {
        return _serializationView;
    }

    /**
     * @deprecated Since 2.2, use {@link #getActiveView} instead.
     */
    @Deprecated
    public final Class<?> getSerializationView() {
        return _serializationView;
    }

    /*
    /**********************************************************
    /* Generic attributes (2.3+)
    /**********************************************************
     */

    @Override
    public Object getAttribute(Object key) {
        return _attributes.getAttribute(key);
    }

    @Override
    public SerializerProvider setAttribute(Object key, Object value) {
        _attributes = _attributes.withPerCallAttribute(key, value);
        return this;
    }

    /*
    /**********************************************************
    /* Access to general configuration
    /**********************************************************
     */

    @Override
    public final boolean isEnabled(MapperFeature feature) {
        return _config.isEnabled(feature);
    }

    @Override
    public final TypeFactory getTypeFactory() {
        return _config.getTypeFactory();
    }

    @Override
    public final boolean canOverrideAccessModifiers() {
        return _config.canOverrideAccessModifiers();
    }

    @Override
    public final JsonFormat.Value getDefaultPropertyFormat(Class<?> baseType) {
        return _config.getDefaultPropertyFormat(baseType);
    }

    @Override
    public Locale getLocale() {
        return _config.getLocale();
    }

    @Override
    public final SerializationConfig getConfig() {
        return _config;
    }

    /*
    /**********************************************************
    /* Access to Object Id aspects
    /**********************************************************
     */

    /**
     * Method called to find the Object Id for given POJO, if one
     * has been generated. Will always return a non-null Object;
     * contents vary depending on whether an Object Id already
     * exists or not.
     */
    public abstract WritableObjectId findObjectId(Object forPojo,
            ObjectIdGenerator<?> generatorType);

    /*
    /**********************************************************
    /* General serializer locating functionality
    /**********************************************************
     */

    /*
    /********************************************************
    /* Accessors for specialized serializers
    /********************************************************
     */

    /**
     * @since 2.0
     */
    public JsonSerializer<Object> getDefaultNullKeySerializer() {
        return _nullKeySerializer;
    }

    /**
     * @since 2.0
     */
    public JsonSerializer<Object> getDefaultNullValueSerializer() {
        return _nullValueSerializer;
    }

    /**
     * Method called to get the serializer to use for serializing
     * Map keys that are nulls: this is needed since JSON does not allow
     * any non-String value as key, including null.
     *<p>
     * Typically, returned serializer
     * will either throw an exception, or use an empty String; but
     * other behaviors are possible.
     */
    /**
     * Method called to find a serializer to use for null values for given
     * declared type. Note that type is completely based on declared type,
     * since nulls in Java have no type and thus runtime type can not be
     * determined.
     * 
     * @since 2.0
     */
    public JsonSerializer<Object> findNullKeySerializer(JavaType serializationType,
            BeanProperty property)
            throws JsonMappingException {
        return _nullKeySerializer;
    }

    /**
     * Method called to get the serializer to use for serializing null
     * values for specified property.
     *<p>
     * Default implementation simply calls {@link #getDefaultNullValueSerializer()};
     * can be overridden to add custom null serialization for properties
     * of certain type or name. This gives method full granularity to basically
     * override null handling for any specific property or class of properties.
     * 
     * @since 2.0
     */
    public JsonSerializer<Object> findNullValueSerializer(BeanProperty property)
            throws JsonMappingException {
        return _nullValueSerializer;
    }

    /**
     * Method called to get the serializer to use if provider
     * can not determine an actual type-specific serializer
     * to use; typically when none of {@link SerializerFactory}
     * instances are able to construct a serializer.
     *<p>
     * Typically, returned serializer will throw an exception,
     * although alternatively {@link com.fasterxml.jackson.databind.ser.std.ToStringSerializer}
     * could be returned as well.
     *
     * @param unknownType Type for which no serializer is found
     */
    public JsonSerializer<Object> getUnknownTypeSerializer(Class<?> unknownType) {
        // 23-Apr-2015, tatu: Only return shared instance if nominal type is Object.class
        if (unknownType == Object.class) {
            return _unknownTypeSerializer;
        }
        // otherwise construct explicit instance with property handled type
        return new UnknownSerializer(unknownType);
    }

    /**
     * Helper method called to see if given serializer is considered to be
     * something returned by {@link #getUnknownTypeSerializer}, that is, something
     * for which no regular serializer was found or constructed.
     * 
     * @since 2.5
     */
    public boolean isUnknownTypeSerializer(JsonSerializer<?> ser) {
        if ((ser == _unknownTypeSerializer) || (ser == null)) {
            return true;
        }
        return false;
    }

    /*
    /**********************************************************
    /* Methods for creating instances based on annotations
    /**********************************************************
     */

    /**
     * Method that can be called to construct and configure serializer instance,
     * either given a {@link Class} to instantiate (with default constructor),
     * or an uninitialized serializer instance.
     * Either way, serialize will be properly resolved
     * (via {@link com.fasterxml.jackson.databind.ser.ResolvableSerializer}) and/or contextualized
     * (via {@link com.fasterxml.jackson.databind.ser.ContextualSerializer}) as necessary.
     * 
     * @param annotated Annotated entity that contained definition
     * @param serDef Serializer definition: either an instance or class
     */
    public abstract JsonSerializer<Object> serializerInstance(Annotated annotated,
            Object serDef)
            throws JsonMappingException;

    /*
    /**********************************************************
    /* Support for contextualization
    /**********************************************************
     */

    /**
     * Method called for primary property serializers (ones
     * directly created to serialize values of a POJO property),
     * to handle details of resolving
     * {@link ContextualSerializer} with given property context.
     * 
     * @param property Property for which the given primary serializer is used; never null.
     * 
     * @since 2.3
     */
    public JsonSerializer<?> handlePrimaryContextualization(JsonSerializer<?> ser,
            BeanProperty property)
            throws JsonMappingException {
        if (ser != null) {
            if (ser instanceof ContextualSerializer) {
                ser = ((ContextualSerializer) ser).createContextual(this, property);
            }
        }
        return ser;
    }

    /**
     * Method called for secondary property serializers (ones
     * NOT directly created to serialize values of a POJO property
     * but instead created as a dependant serializer -- such as value serializers
     * for structured types, or serializers for root values)
     * to handle details of resolving
     * {@link ContextualDeserializer} with given property context.
     * Given that these serializers are not directly related to given property
     * (or, in case of root value property, to any property), annotations
     * accessible may or may not be relevant.
     * 
     * @param property Property for which serializer is used, if any; null
     *    when deserializing root values
     * 
     * @since 2.3
     */
    public JsonSerializer<?> handleSecondaryContextualization(JsonSerializer<?> ser,
            BeanProperty property)
            throws JsonMappingException {
        if (ser != null) {
            if (ser instanceof ContextualSerializer) {
                ser = ((ContextualSerializer) ser).createContextual(this, property);
            }
        }
        return ser;
    }

    /*
    /********************************************************
    /* Convenience methods for serializing using default methods
    /********************************************************
     */

    public final void defaultSerializeNull(JsonGenerator gen) throws IOException {
        if (_stdNullValueSerializer) { // minor perf optimization
            gen.writeNull();
        } else {
            _nullValueSerializer.serialize(null, gen, this);
        }
    }

    /*
    /********************************************************
    /* Error reporting
    /********************************************************
     */

    /**
     * Factory method for constructing a {@link JsonMappingException};
     * usually only indirectly used by calling
     * {@link #reportMappingProblem(String, Object...)}.
     *
     * @since 2.6
     */
    public JsonMappingException mappingException(String message, Object... args) {
        if (args != null && args.length > 0) {
            message = StringUtil.format(message, args);
        }
        return JsonMappingException.from(getGenerator(), message);
    }

    /**
     * Factory method for constructing a {@link JsonMappingException};
     * usually only indirectly used by calling
     * {@link #reportMappingProblem(Throwable, String, Object...)}
     * 
     * @since 2.8
     */
    protected JsonMappingException mappingException(Throwable t, String message, Object... args) {
        if (args != null && args.length > 0) {
            message = StringUtil.format(message, args);
        }
        return JsonMappingException.from(getGenerator(), message, t);
    }

    /**
     * Helper method called to indicate problem; default behavior is to construct and
     * throw a {@link JsonMappingException}, but in future may collect more than one
     * and only throw after certain number, or at the end of serialization.
     *
     * @since 2.8
     */
    public void reportMappingProblem(String message, Object... args) throws JsonMappingException {
        throw mappingException(message, args);
    }

    /**
     * Helper method called to indicate problem; default behavior is to construct and
     * throw a {@link JsonMappingException}, but in future may collect more than one
     * and only throw after certain number, or at the end of serialization.
     *
     * @since 2.8
     */
    public void reportMappingProblem(Throwable t, String message, Object... args) throws JsonMappingException {
        throw mappingException(t, message, args);
    }

    /**
     * Helper method called to indicate problem in POJO (serialization) definitions or settings
     * regarding specific Java type, unrelated to actual JSON content to map.
     * Default behavior is to construct and throw a {@link JsonMappingException}.
     *
     * @since 2.9
     */
    public <T> T reportBadTypeDefinition(BeanDescription bean,
            String message, Object... args) throws JsonMappingException {
        if (args != null && args.length > 0) {
            message = StringUtil.format(message, args);
        }
        String beanDesc = (bean == null) ? "N/A" : _desc(bean.getType().getGenericSignature());
        throw mappingException("Invalid type definition for type %s: %s",
                beanDesc, message);
    }

    /**
     * @since 2.8
     */
    public JsonGenerator getGenerator() {
        return null;
    }

    /*
    /********************************************************
    /* Helper methods
    /********************************************************
     */

    protected void _reportIncompatibleRootType(Object value, JavaType rootType) throws IOException {
        // One special case: allow primitive/wrapper type coercion
        if (rootType.isPrimitive()) {
            Class<?> wrapperType = ClassUtil.wrapperType(rootType.getRawClass());
            // If it's just difference between wrapper, primitive, let it slide
            if (ClassUtils.isAssignableFrom(wrapperType, value.getClass())) {
                return;
            }
        }
        reportMappingProblem("Incompatible types: declared root type (%s) vs %s",
                rootType, value.getClass().getName());
    }

    /*
    /**********************************************************
    /* Low-level methods for actually constructing and initializing
    /* serializers
    /**********************************************************
     */

    /**
     * Helper method called to resolve and contextualize given
     * serializer, if and as necessary.
     */
    @SuppressWarnings("unchecked")
    protected JsonSerializer<Object> _handleContextualResolvable(JsonSerializer<?> ser,
            BeanProperty property)
            throws JsonMappingException {
        if (ser instanceof ResolvableSerializer) {
            ((ResolvableSerializer) ser).resolve(this);
        }
        return (JsonSerializer<Object>) handleSecondaryContextualization(ser, property);
    }

    @SuppressWarnings("unchecked")
    protected JsonSerializer<Object> _handleResolvable(JsonSerializer<?> ser)
            throws JsonMappingException {
        if (ser instanceof ResolvableSerializer) {
            ((ResolvableSerializer) ser).resolve(this);
        }
        return (JsonSerializer<Object>) ser;
    }

    /*
    /**********************************************************
    /* Internal methods
    /**********************************************************
     */

    protected String _desc(Object value) {
        if (value == null) {
            return "N/A";
        }
        return "'" + value + "'";
    }

    protected String _quotedString(Object value) {
        if (value == null) {
            return "N/A";
        }
        return String.valueOf(value);
    }
}
