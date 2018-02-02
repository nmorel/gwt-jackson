package com.fasterxml.jackson.databind;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Instantiatable;

import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Object that contains baseline configuration for serialization
 * process. An instance is owned by {@link ObjectMapper}, which
 * passes an immutable instance for serialization process to
 * {@link SerializerProvider} and {@link SerializerFactory}
 * (either directly, or through {@link ObjectWriter}.
 *<p>
 * Note that instances are considered immutable and as such no copies
 * should need to be created for sharing; all copying is done with
 * "fluent factory" methods.
 */
public final class SerializationConfig
        extends MapperConfigBase<SerializationFeature, SerializationConfig>
        implements java.io.Serializable // since 2.1
{

    // since 2.5
    private static final long serialVersionUID = 1;

    // since 2.6
    protected final static PrettyPrinter DEFAULT_PRETTY_PRINTER = new DefaultPrettyPrinter();

    // since 2.7
    // Default is "USE_DEFAULTS, USE_DEFAULTS"
    protected final static JsonInclude.Value DEFAULT_INCLUSION = JsonInclude.Value.empty();

    /*
    /**********************************************************
    /* Configured helper objects
    /**********************************************************
     */

    /**
     * If "default pretty-printing" is enabled, it will create the instance
     * from this blueprint object.
     *
     * @since 2.6
     */
    protected final PrettyPrinter _defaultPrettyPrinter;

    /*
    /**********************************************************
    /* Serialization features 
    /**********************************************************
     */

    /**
     * Set of {@link SerializationFeature}s enabled.
     */
    protected final int _serFeatures;

    /*
    /**********************************************************
    /* Generator features: generic, format-specific
    /**********************************************************
     */
    /**
     * States of {@link com.fasterxml.jackson.core.JsonGenerator.Feature}s to enable/disable.
     */
    protected final int _generatorFeatures;

    /**
     * Bitflag of {@link com.fasterxml.jackson.core.JsonGenerator.Feature}s to enable/disable
     */
    protected final int _generatorFeaturesToChange;

    /**
     * States of {@link com.fasterxml.jackson.core.FormatFeature}s to enable/disable.
     *
     * @since 2.7
     */
    protected final int _formatWriteFeatures;

    /**
     * Bitflag of {@link com.fasterxml.jackson.core.FormatFeature}s to enable/disable
     *
     * @since 2.7
     */
    protected final int _formatWriteFeaturesToChange;

    /*
    /**********************************************************
    /* Other configuration
    /**********************************************************
     */

    /**
     * Which Bean/Map properties are to be included in serialization?
     * Default settings is to include all regardless of value; can be
     * changed to only include non-null properties, or properties
     * with non-default values.
     *<p>
     * NOTE: type changed in 2.7, to include both value and content
     * inclusion options./
     */
    protected final JsonInclude.Value _serializationInclusion;

    /*
    /**********************************************************
    /* Life-cycle, constructors
    /**********************************************************
     */

    private SerializationConfig(SerializationConfig src,
            int mapperFeatures, int serFeatures,
            int generatorFeatures, int generatorFeatureMask,
            int formatFeatures, int formatFeaturesMask) {
        super(src, mapperFeatures);
        _serFeatures = serFeatures;
        _serializationInclusion = src._serializationInclusion;
        _defaultPrettyPrinter = src._defaultPrettyPrinter;
        _generatorFeatures = generatorFeatures;
        _generatorFeaturesToChange = generatorFeatureMask;
        _formatWriteFeatures = formatFeatures;
        _formatWriteFeaturesToChange = formatFeaturesMask;
    }

    private SerializationConfig(SerializationConfig src, BaseSettings base) {
        super(src, base);
        _serFeatures = src._serFeatures;
        _serializationInclusion = src._serializationInclusion;
        _defaultPrettyPrinter = src._defaultPrettyPrinter;
        _generatorFeatures = src._generatorFeatures;
        _generatorFeaturesToChange = src._generatorFeaturesToChange;
        _formatWriteFeatures = src._formatWriteFeatures;
        _formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
    }

    private SerializationConfig(SerializationConfig src, Class<?> view) {
        super(src, view);
        _serFeatures = src._serFeatures;
        _serializationInclusion = src._serializationInclusion;
        _defaultPrettyPrinter = src._defaultPrettyPrinter;
        _generatorFeatures = src._generatorFeatures;
        _generatorFeaturesToChange = src._generatorFeaturesToChange;
        _formatWriteFeatures = src._formatWriteFeatures;
        _formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
    }

    private SerializationConfig(SerializationConfig src, JsonInclude.Value incl) {
        super(src);
        _serFeatures = src._serFeatures;
        _serializationInclusion = incl;
        _defaultPrettyPrinter = src._defaultPrettyPrinter;
        _generatorFeatures = src._generatorFeatures;
        _generatorFeaturesToChange = src._generatorFeaturesToChange;
        _formatWriteFeatures = src._formatWriteFeatures;
        _formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
    }

    private SerializationConfig(SerializationConfig src, PropertyName rootName) {
        super(src, rootName);
        _serFeatures = src._serFeatures;
        _serializationInclusion = src._serializationInclusion;
        _defaultPrettyPrinter = src._defaultPrettyPrinter;
        _generatorFeatures = src._generatorFeatures;
        _generatorFeaturesToChange = src._generatorFeaturesToChange;
        _formatWriteFeatures = src._formatWriteFeatures;
        _formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
    }

    /**
     * @since 2.1
     */
    protected SerializationConfig(SerializationConfig src, ContextAttributes attrs) {
        super(src, attrs);
        _serFeatures = src._serFeatures;
        _serializationInclusion = src._serializationInclusion;
        _defaultPrettyPrinter = src._defaultPrettyPrinter;
        _generatorFeatures = src._generatorFeatures;
        _generatorFeaturesToChange = src._generatorFeaturesToChange;
        _formatWriteFeatures = src._formatWriteFeatures;
        _formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
    }

    /**
     * @since 2.1
     */
    protected SerializationConfig(SerializationConfig src) {
        super(src);
        _serFeatures = src._serFeatures;
        _serializationInclusion = src._serializationInclusion;
        _defaultPrettyPrinter = src._defaultPrettyPrinter;
        _generatorFeatures = src._generatorFeatures;
        _generatorFeaturesToChange = src._generatorFeaturesToChange;
        _formatWriteFeatures = src._formatWriteFeatures;
        _formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
    }

    /**
     * @since 2.6
     */
    protected SerializationConfig(SerializationConfig src, PrettyPrinter defaultPP) {
        super(src);
        _serFeatures = src._serFeatures;
        _serializationInclusion = src._serializationInclusion;
        _defaultPrettyPrinter = defaultPP;
        _generatorFeatures = src._generatorFeatures;
        _generatorFeaturesToChange = src._generatorFeaturesToChange;
        _formatWriteFeatures = src._formatWriteFeatures;
        _formatWriteFeaturesToChange = src._formatWriteFeaturesToChange;
    }

    /*
    /**********************************************************
    /* Life-cycle, factory methods from MapperConfig
    /**********************************************************
     */

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features enabled.
     */
    @Override
    public SerializationConfig with(MapperFeature... features) {
        int newMapperFlags = _mapperFeatures;
        for (MapperFeature f : features) {
            newMapperFlags |= f.getMask();
        }
        return (newMapperFlags == _mapperFeatures) ? this
                : new SerializationConfig(this, newMapperFlags, _serFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features disabled.
     */
    @Override
    public SerializationConfig without(MapperFeature... features) {
        int newMapperFlags = _mapperFeatures;
        for (MapperFeature f : features) {
            newMapperFlags &= ~f.getMask();
        }
        return (newMapperFlags == _mapperFeatures) ? this
                : new SerializationConfig(this, newMapperFlags, _serFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    @Override
    public SerializationConfig with(MapperFeature feature, boolean state) {
        int newMapperFlags;
        if (state) {
            newMapperFlags = _mapperFeatures | feature.getMask();
        } else {
            newMapperFlags = _mapperFeatures & ~feature.getMask();
        }
        return (newMapperFlags == _mapperFeatures) ? this
                : new SerializationConfig(this, newMapperFlags, _serFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    @Override
    public SerializationConfig with(PropertyNamingStrategy pns) {
        return _withBase(_base.withPropertyNamingStrategy(pns));
    }

    @Override
    public SerializationConfig withRootName(PropertyName rootName) {
        if (rootName == null) {
            if (_rootName == null) {
                return this;
            }
        } else if (rootName.equals(_rootName)) {
            return this;
        }
        return new SerializationConfig(this, rootName);
    }

    @Override
    public SerializationConfig with(TypeFactory tf) {
        return _withBase(_base.withTypeFactory(tf));
    }

    @Override
    public SerializationConfig withView(Class<?> view) {
        return (_view == view) ? this : new SerializationConfig(this, view);
    }

    @Override
    public SerializationConfig with(Locale l) {
        return _withBase(_base.with(l));
    }

    @Override
    public SerializationConfig with(Base64Variant base64) {
        return _withBase(_base.with(base64));
    }

    @Override
    public SerializationConfig with(ContextAttributes attrs) {
        return (attrs == _attributes) ? this : new SerializationConfig(this, attrs);
    }

    private final SerializationConfig _withBase(BaseSettings newBase) {
        return (_base == newBase) ? this : new SerializationConfig(this, newBase);
    }

    /*
    /**********************************************************
    /* Factory methods for SerializationFeature
    /**********************************************************
     */

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified feature enabled.
     */
    public SerializationConfig with(SerializationFeature feature) {
        int newSerFeatures = _serFeatures | feature.getMask();
        return (newSerFeatures == _serFeatures) ? this
                : new SerializationConfig(this, _mapperFeatures, newSerFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features enabled.
     */
    public SerializationConfig with(SerializationFeature first, SerializationFeature... features) {
        int newSerFeatures = _serFeatures | first.getMask();
        for (SerializationFeature f : features) {
            newSerFeatures |= f.getMask();
        }
        return (newSerFeatures == _serFeatures) ? this
                : new SerializationConfig(this, _mapperFeatures, newSerFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features enabled.
     */
    public SerializationConfig withFeatures(SerializationFeature... features) {
        int newSerFeatures = _serFeatures;
        for (SerializationFeature f : features) {
            newSerFeatures |= f.getMask();
        }
        return (newSerFeatures == _serFeatures) ? this
                : new SerializationConfig(this, _mapperFeatures, newSerFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified feature disabled.
     */
    public SerializationConfig without(SerializationFeature feature) {
        int newSerFeatures = _serFeatures & ~feature.getMask();
        return (newSerFeatures == _serFeatures) ? this
                : new SerializationConfig(this, _mapperFeatures, newSerFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features disabled.
     */
    public SerializationConfig without(SerializationFeature first, SerializationFeature... features) {
        int newSerFeatures = _serFeatures & ~first.getMask();
        for (SerializationFeature f : features) {
            newSerFeatures &= ~f.getMask();
        }
        return (newSerFeatures == _serFeatures) ? this
                : new SerializationConfig(this, _mapperFeatures, newSerFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features disabled.
     */
    public SerializationConfig withoutFeatures(SerializationFeature... features) {
        int newSerFeatures = _serFeatures;
        for (SerializationFeature f : features) {
            newSerFeatures &= ~f.getMask();
        }
        return (newSerFeatures == _serFeatures) ? this
                : new SerializationConfig(this, _mapperFeatures, newSerFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /*
    /**********************************************************
    /* Factory methods for JsonGenerator.Feature (2.5)
    /**********************************************************
     */
    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified feature enabled.
     *
     * @since 2.5
     */
    public SerializationConfig with(JsonGenerator.Feature feature) {
        int newSet = _generatorFeatures | feature.getMask();
        int newMask = _generatorFeaturesToChange | feature.getMask();
        return ((_generatorFeatures == newSet) && (_generatorFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        newSet, newMask,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features enabled.
     *
     * @since 2.5
     */
    public SerializationConfig withFeatures(JsonGenerator.Feature... features) {
        int newSet = _generatorFeatures;
        int newMask = _generatorFeaturesToChange;
        for (JsonGenerator.Feature f : features) {
            int mask = f.getMask();
            newSet |= mask;
            newMask |= mask;
        }
        return ((_generatorFeatures == newSet) && (_generatorFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        newSet, newMask,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified feature disabled.
     *
     * @since 2.5
     */
    public SerializationConfig without(JsonGenerator.Feature feature) {
        int newSet = _generatorFeatures & ~feature.getMask();
        int newMask = _generatorFeaturesToChange | feature.getMask();
        return ((_generatorFeatures == newSet) && (_generatorFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        newSet, newMask,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features disabled.
     *
     * @since 2.5
     */
    public SerializationConfig withoutFeatures(JsonGenerator.Feature... features) {
        int newSet = _generatorFeatures;
        int newMask = _generatorFeaturesToChange;
        for (JsonGenerator.Feature f : features) {
            int mask = f.getMask();
            newSet &= ~mask;
            newMask |= mask;
        }
        return ((_generatorFeatures == newSet) && (_generatorFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        newSet, newMask,
                        _formatWriteFeatures, _formatWriteFeaturesToChange);
    }

    /*
    /**********************************************************
    /* Factory methods for FormatFeature (2.7)
    /**********************************************************
     */
    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified feature enabled.
     *
     * @since 2.7
     */
    public SerializationConfig with(FormatFeature feature) {
        int newSet = _formatWriteFeatures | feature.getMask();
        int newMask = _formatWriteFeaturesToChange | feature.getMask();
        return ((_formatWriteFeatures == newSet) && (_formatWriteFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        newSet, newMask);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features enabled.
     *
     * @since 2.7
     */
    public SerializationConfig withFeatures(FormatFeature... features) {
        int newSet = _formatWriteFeatures;
        int newMask = _formatWriteFeaturesToChange;
        for (FormatFeature f : features) {
            int mask = f.getMask();
            newSet |= mask;
            newMask |= mask;
        }
        return ((_formatWriteFeatures == newSet) && (_formatWriteFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        newSet, newMask);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified feature disabled.
     *
     * @since 2.7
     */
    public SerializationConfig without(FormatFeature feature) {
        int newSet = _formatWriteFeatures & ~feature.getMask();
        int newMask = _formatWriteFeaturesToChange | feature.getMask();
        return ((_formatWriteFeatures == newSet) && (_formatWriteFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        newSet, newMask);
    }

    /**
     * Fluent factory method that will construct and return a new configuration
     * object instance with specified features disabled.
     *
     * @since 2.7
     */
    public SerializationConfig withoutFeatures(FormatFeature... features) {
        int newSet = _formatWriteFeatures;
        int newMask = _formatWriteFeaturesToChange;
        for (FormatFeature f : features) {
            int mask = f.getMask();
            newSet &= ~mask;
            newMask |= mask;
        }
        return ((_formatWriteFeatures == newSet) && (_formatWriteFeaturesToChange == newMask)) ? this
                : new SerializationConfig(this, _mapperFeatures, _serFeatures,
                        _generatorFeatures, _generatorFeaturesToChange,
                        newSet, newMask);
    }

    /*
    /**********************************************************
    /* Factory methods, other
    /**********************************************************
     */

    /**
     * @deprecated Since 2.7 use {@link #withPropertyInclusion} instead
     */
    @Deprecated
    public SerializationConfig withSerializationInclusion(JsonInclude.Include incl) {
        return withPropertyInclusion(DEFAULT_INCLUSION.withValueInclusion(incl));
    }

    /**
     * @since 2.7
     */
    public SerializationConfig withPropertyInclusion(JsonInclude.Value incl) {
        if (_serializationInclusion.equals(incl)) {
            return this;
        }
        return new SerializationConfig(this, incl);
    }

    /**
     * @since 2.6
     */
    public SerializationConfig withDefaultPrettyPrinter(PrettyPrinter pp) {
        return (_defaultPrettyPrinter == pp) ? this : new SerializationConfig(this, pp);
    }

    /*
    /**********************************************************
    /* Factories for objects configured here
    /**********************************************************
     */

    public PrettyPrinter constructDefaultPrettyPrinter() {
        PrettyPrinter pp = _defaultPrettyPrinter;
        if (pp instanceof Instantiatable<?>) {
            pp = (PrettyPrinter) ((Instantiatable<?>) pp).createInstance();
        }
        return pp;
    }

    /*
    /**********************************************************
    /* JsonParser initialization
    /**********************************************************
     */

    /**
     * Method called by {@link ObjectMapper} and {@link ObjectWriter}
     * to modify those {@link com.fasterxml.jackson.core.JsonGenerator.Feature} settings
     * that have been configured via this config instance.
     * 
     * @since 2.5
     */
    public void initialize(JsonGenerator g) {
        if (SerializationFeature.INDENT_OUTPUT.enabledIn(_serFeatures)) {
            // but do not override an explicitly set one
            if (g.getPrettyPrinter() == null) {
                PrettyPrinter pp = constructDefaultPrettyPrinter();
                if (pp != null) {
                    g.setPrettyPrinter(pp);
                }
            }
        }
        @SuppressWarnings("deprecation")
        boolean useBigDec = SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(_serFeatures);

        int mask = _generatorFeaturesToChange;
        if ((mask != 0) || useBigDec) {
            int newFlags = _generatorFeatures;
            // although deprecated, needs to be supported for now
            if (useBigDec) {
                int f = JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN.getMask();
                newFlags |= f;
                mask |= f;
            }
            g.overrideStdFeatures(newFlags, mask);
        }
        if (_formatWriteFeaturesToChange != 0) {
            g.overrideFormatFeatures(_formatWriteFeatures, _formatWriteFeaturesToChange);
        }
    }

    /*
    /**********************************************************
    /* MapperConfig implementation/overrides: introspection
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Configuration: default settings with per-type overrides
    /**********************************************************
     */

    /**
     * @deprecated Since 2.7 use {@link #getDefaultPropertyInclusion} instead
     */
    @Deprecated
    public JsonInclude.Include getSerializationInclusion() {
        JsonInclude.Include incl = _serializationInclusion.getValueInclusion();
        return (incl == JsonInclude.Include.USE_DEFAULTS) ? JsonInclude.Include.ALWAYS : incl;
    }

    @Override
    public JsonInclude.Value getDefaultPropertyInclusion() {
        return _serializationInclusion;
    }

    @Override
    public JsonInclude.Value getDefaultPropertyInclusion(Class<?> baseType) {
        return _serializationInclusion;
    }

    @Override
    public JsonInclude.Value getDefaultPropertyInclusion(Class<?> baseType,
            JsonInclude.Value defaultIncl) {
        return defaultIncl;
    }

    /*
    /**********************************************************
    /* Configuration: other
    /**********************************************************
     */

    @Override
    public boolean useRootWrapping() {
        if (_rootName != null) { // empty String disables wrapping; non-empty enables
            return !_rootName.isEmpty();
        }
        return isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
    }

    public final boolean isEnabled(SerializationFeature f) {
        return (_serFeatures & f.getMask()) != 0;
    }

    /**
     * Accessor method that first checks if we have any overrides
     * for feature, and only if not, checks state of passed-in
     * factory.
     * 
     * @since 2.5
     */
    public final boolean isEnabled(JsonGenerator.Feature f, JsonFactory factory) {
        int mask = f.getMask();
        if ((_generatorFeaturesToChange & mask) != 0) {
            return (_generatorFeatures & f.getMask()) != 0;
        }
        return factory.isEnabled(f);
    }

    /**
     * "Bulk" access method for checking that all features specified by
     * mask are enabled.
     * 
     * @since 2.3
     */
    public final boolean hasSerializationFeatures(int featureMask) {
        return (_serFeatures & featureMask) == featureMask;
    }

    public final int getSerializationFeatures() {
        return _serFeatures;
    }

    /**
     * Accessor for configured blueprint "default" {@link PrettyPrinter} to
     * use, if default pretty-printing is enabled.
     *<p>
     * NOTE: returns the "blueprint" instance, and does NOT construct
     * an instance ready to use; call {@link #constructDefaultPrettyPrinter()} if
     * actually usable instance is desired.
     *
     * @since 2.6
     */
    public PrettyPrinter getDefaultPrettyPrinter() {
        return _defaultPrettyPrinter;
    }

    /*
    /**********************************************************
    /* Introspection methods
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Debug support
    /**********************************************************
     */

    @Override
    public String toString() {
        return "[SerializationConfig: flags=0x" + Integer.toHexString(_serFeatures) + "]";
    }

    @Override
    public SerializationConfig withVisibility(PropertyAccessor forMethod, Visibility visibility) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BeanDescription introspectClassAnnotations(JavaType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BeanDescription introspectDirectClassAnnotations(JavaType type) {
        // TODO Auto-generated method stub
        return null;
    }
}
