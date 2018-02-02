package com.fasterxml.jackson.databind.cfg;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;

@SuppressWarnings("serial")
public abstract class MapperConfigBase<CFG extends ConfigFeature,
        T extends MapperConfigBase<CFG, T>>
        extends MapperConfig<T>
        implements java.io.Serializable {

    private final static int DEFAULT_MAPPER_FEATURES = collectFeatureDefaults(MapperFeature.class);

    /*
    /**********************************************************
    /* Immutable config
    /**********************************************************
     */

    /**
     * Explicitly defined root name to use, if any; if empty
     * String, will disable root-name wrapping; if null, will
     * use defaults
     */
    protected final PropertyName _rootName;

    /**
     * View to use for filtering out properties to serialize
     * or deserialize.
     * Null if none (will also be assigned null if <code>Object.class</code>
     * is defined), meaning that all properties are to be included.
     */
    protected final Class<?> _view;

    /**
     * Contextual attributes accessible (get and set) during processing,
     * on per-call basis.
     * 
     * @since 2.3
     */
    protected final ContextAttributes _attributes;

    /*
    /**********************************************************
    /* Construction
    /**********************************************************
     */

    /**
     * Constructor used when creating a new instance (compared to
     * that of creating fluent copies)
     *
     * @since 2.8
     */
    protected MapperConfigBase(BaseSettings base) {
        super(base, DEFAULT_MAPPER_FEATURES);
        _rootName = null;
        _view = null;
        // default to "no attributes"
        _attributes = ContextAttributes.getEmpty();
    }

    /**
     * Pass-through constructor used when no changes are needed to the
     * base class.
     */
    protected MapperConfigBase(MapperConfigBase<CFG, T> src) {
        super(src);
        _rootName = src._rootName;
        _view = src._view;
        _attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, BaseSettings base) {
        super(src, base);
        _rootName = src._rootName;
        _view = src._view;
        _attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, int mapperFeatures) {
        super(src, mapperFeatures);
        _rootName = src._rootName;
        _view = src._view;
        _attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, PropertyName rootName) {
        super(src);
        _rootName = rootName;
        _view = src._view;
        _attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, Class<?> view) {
        super(src);
        _rootName = src._rootName;
        _view = view;
        _attributes = src._attributes;
    }

    /**
     * @since 2.3
     */
    protected MapperConfigBase(MapperConfigBase<CFG, T> src, ContextAttributes attr) {
        super(src);
        _rootName = src._rootName;
        _view = src._view;
        _attributes = attr;
    }

    /*
    /**********************************************************
    /* Overrides
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Addition fluent factory methods, common to all sub-types
    /**********************************************************
     */

    /**
     * Method for constructing and returning a new instance with different
     * {@link PropertyNamingStrategy}
     * to use.
     *<p>
     * NOTE: make sure to register new instance with <code>ObjectMapper</code>
     * if directly calling this method.
     */
    public abstract T with(PropertyNamingStrategy strategy);

    /**
     * Method for constructing and returning a new instance with different
     * root name to use (none, if null).
     *<p>
     * Note that when a root name is set to a non-Empty String, this will automatically force use
     * of root element wrapping with given name. If empty String passed, will
     * disable root name wrapping; and if null used, will instead use
     * <code>SerializationFeature</code> to determine if to use wrapping, and annotation
     * (or default name) for actual root name to use.
     * 
     * @param rootName to use: if null, means "use default" (clear setting);
     *   if empty String ("") means that no root name wrapping is used;
     *   otherwise defines root name to use.
     *   
     * @since 2.6
     */
    public abstract T withRootName(PropertyName rootName);

    public T withRootName(String rootName) {
        if (rootName == null) {
            return withRootName((PropertyName) null);
        }
        return withRootName(PropertyName.construct(rootName));
    }

    /**
     * Method for constructing and returning a new instance with different
     * {@link TypeFactory}
     * to use.
     */
    public abstract T with(TypeFactory typeFactory);

    /**
     * Method for constructing and returning a new instance with different
     * view to use.
     */
    public abstract T withView(Class<?> view);

    /**
     * Method for constructing and returning a new instance with different
     * minimal visibility level for specified property type
     */
    public abstract T withVisibility(PropertyAccessor forMethod, JsonAutoDetect.Visibility visibility);

    /**
     * Method for constructing and returning a new instance with different
     * default {@link java.util.Locale} to use for formatting.
     */
    public abstract T with(Locale l);

    /**
     * Method for constructing and returning a new instance with different
     * default {@link Base64Variant} to use with base64-encoded binary values.
     */
    public abstract T with(Base64Variant base64);

    /**
     * Method for constructing an instance that has specified
     * contextual attributes.
     * 
     * @since 2.3
     */
    public abstract T with(ContextAttributes attrs);

    /**
     * Method for constructing an instance that has only specified
     * attributes, removing any attributes that exist before the call.
     * 
     * @since 2.3
     */
    public T withAttributes(Map<?, ?> attributes) {
        return with(getAttributes().withSharedAttributes(attributes));
    }

    /**
     * Method for constructing an instance that has specified
     * value for attribute for given key.
     * 
     * @since 2.3
     */
    public T withAttribute(Object key, Object value) {
        return with(getAttributes().withSharedAttribute(key, value));
    }

    /**
     * Method for constructing an instance that has no
     * value for attribute for given key.
     * 
     * @since 2.3
     */
    public T withoutAttribute(Object key) {
        return with(getAttributes().withoutSharedAttribute(key));
    }

    /*
    /**********************************************************
    /* Simple accessors
    /**********************************************************
     */

    /**
     * @deprecated Since 2.6 use {@link #getFullRootName} instead.
     */
    @Deprecated // since 2.6
    public final String getRootName() {
        return (_rootName == null) ? null : _rootName.getSimpleName();
    }

    /**
     * @since 2.6
     */
    public final PropertyName getFullRootName() {
        return _rootName;
    }

    @Override
    public final Class<?> getActiveView() {
        return _view;
    }

    @Override
    public final ContextAttributes getAttributes() {
        return _attributes;
    }

    /*
    /**********************************************************
    /* Property config override access
    /**********************************************************
     */

    @Override
    public final JsonFormat.Value getDefaultPropertyFormat(Class<?> type) {
        return EMPTY_FORMAT;
    }

    @Override
    public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> type) {
        return null;
    }

    /*
    /**********************************************************
    /* Other config access
    /**********************************************************
     */

    @Override
    public PropertyName findRootName(JavaType rootType) {
        if (_rootName != null) {
            return _rootName;
        }
        return null;
    }

    @Override
    public PropertyName findRootName(Class<?> rawRootType) {
        if (_rootName != null) {
            return _rootName;
        }
        return null;
    }

    /*
    /**********************************************************
    /* ClassIntrospector.MixInResolver impl:
    /**********************************************************
     */

}
