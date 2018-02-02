package com.fasterxml.jackson.databind;

import java.lang.reflect.Type;
import java.util.Locale;

import com.fasterxml.jackson.annotation.*;

import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Shared base class for {@link DeserializationContext} and
 * {@link SerializerProvider}, context objects passed through data-binding
 * process. Designed so that some of implementations can rely on shared
 * aspects like access to secondary contextual objects like type factories
 * or handler instantiators.
 * 
 * @since 2.2
 */
public abstract class DatabindContext {
    /*
    /**********************************************************
    /* Generic config access
    /**********************************************************
     */

    /**
     * Accessor to currently active configuration (both per-request configs
     * and per-mapper config).
     */
    public abstract MapperConfig<?> getConfig();

    /*
    /**********************************************************
    /* Access to specific config settings
    /**********************************************************
     */

    /**
     * Convenience method for checking whether specified serialization
     * feature is enabled or not.
     * Shortcut for:
     *<pre>
     *  getConfig().isEnabled(feature);
     *</pre>
     */
    public abstract boolean isEnabled(MapperFeature feature);

    /**
     * Convenience method for accessing serialization view in use (if any); equivalent to:
     *<pre>
     *   getConfig().canOverrideAccessModifiers();
     *</pre>
     */
    public abstract boolean canOverrideAccessModifiers();

    /**
     * Accessor for locating currently active view, if any;
     * returns null if no view has been set.
     */
    public abstract Class<?> getActiveView();

    /**
     * @since 2.6
     */
    public abstract Locale getLocale();

    /**
     * @since 2.7
     */
    public abstract JsonFormat.Value getDefaultPropertyFormat(Class<?> baseType);

    /*
    /**********************************************************
    /* Generic attributes (2.3+)
    /**********************************************************
     */

    /**
     * Method for accessing attributes available in this context.
     * Per-call attributes have highest precedence; attributes set
     * via {@link ObjectReader} or {@link ObjectWriter} have lower
     * precedence.
     * 
     * @param key Key of the attribute to get
     * @return Value of the attribute, if any; null otherwise
     * 
     * @since 2.3
     */
    public abstract Object getAttribute(Object key);

    /**
     * Method for setting per-call value of given attribute.
     * This will override any previously defined value for the
     * attribute within this context.
     * 
     * @param key Key of the attribute to set
     * @param value Value to set attribute to
     * 
     * @return This context object, to allow chaining
     * 
     * @since 2.3
     */
    public abstract DatabindContext setAttribute(Object key, Object value);

    /*
    /**********************************************************
    /* Type instantiation/resolution
    /**********************************************************
     */

    /**
     * Convenience method for constructing {@link JavaType} for given JDK
     * type (usually {@link java.lang.Class})
     */
    public JavaType constructType(Type type) {
        return getTypeFactory().constructType(type);
    }

    /**
     * Convenience method for constructing subtypes, retaining generic
     * type parameter (if any)
     */
    public JavaType constructSpecializedType(JavaType baseType, Class<?> subclass) {
        // simple optimization to avoid costly introspection if type-erased type does NOT differ
        if (baseType.getRawClass() == subclass) {
            return baseType;
        }
        return getConfig().constructSpecializedType(baseType, subclass);
    }

    public abstract TypeFactory getTypeFactory();

    /*
    /**********************************************************
    /* Helper object construction
    /**********************************************************
     */
}
