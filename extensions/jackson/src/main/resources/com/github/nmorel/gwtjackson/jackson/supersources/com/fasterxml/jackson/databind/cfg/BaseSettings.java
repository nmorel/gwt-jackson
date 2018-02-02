package com.fasterxml.jackson.databind.cfg;

import java.util.Locale;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Immutable container class used to store simple configuration
 * settings. Since instances are fully immutable, instances can
 * be freely shared and used without synchronization.
 */
public final class BaseSettings
        implements java.io.Serializable {

    // for 2.6
    private static final long serialVersionUID = 1L;

    /*
    /**********************************************************
    /* Configuration settings; introspection, related
    /**********************************************************
     */

    /**
     * Custom property naming strategy in use, if any.
     */
    protected final PropertyNamingStrategy _propertyNamingStrategy;

    /**
     * Specific factory used for creating {@link JavaType} instances;
     * needed to allow modules to add more custom type handling
     * (mostly to support types of non-Java JVM languages)
     */
    protected final TypeFactory _typeFactory;

    /*
    /**********************************************************
    /* Configuration settings; type resolution
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Configuration settings; other
    /**********************************************************
     */

    /**
     * Default {@link java.util.Locale} used with serialization formats.
     * Default value is {@link Locale#getDefault()}.
     */
    protected final Locale _locale;

    /**
     * Explicitly default {@link Base64Variant} to use for handling
     * binary data (<code>byte[]</code>), used with data formats
     * that use base64 encoding (like JSON, CSV).
     * 
     * @since 2.1
     */
    protected final Base64Variant _defaultBase64;

    /*
    /**********************************************************
    /* Construction
    /**********************************************************
     */

    public BaseSettings(
            PropertyNamingStrategy pns, TypeFactory tf,
            Locale locale, Base64Variant defaultBase64) {
        _propertyNamingStrategy = pns;
        _typeFactory = tf;
        _locale = locale;
        _defaultBase64 = defaultBase64;
    }

    /*
    /**********************************************************
    /* Factory methods
    /**********************************************************
     */

    public BaseSettings withPropertyNamingStrategy(PropertyNamingStrategy pns) {
        if (_propertyNamingStrategy == pns) {
            return this;
        }
        return new BaseSettings(pns, _typeFactory,
                _locale,
                _defaultBase64);
    }

    public BaseSettings withTypeFactory(TypeFactory tf) {
        if (_typeFactory == tf) {
            return this;
        }
        return new BaseSettings(_propertyNamingStrategy, tf,
                _locale,
                _defaultBase64);
    }

    public BaseSettings with(Locale l) {
        if (_locale == l) {
            return this;
        }
        return new BaseSettings(_propertyNamingStrategy, _typeFactory,
                l,
                _defaultBase64);
    }

    /**
     * @since 2.1
     */
    public BaseSettings with(Base64Variant base64) {
        if (base64 == _defaultBase64) {
            return this;
        }
        return new BaseSettings(
                _propertyNamingStrategy, _typeFactory,
                _locale,
                base64);
    }

    /*
    /**********************************************************
    /* API
    /**********************************************************
     */
    public PropertyNamingStrategy getPropertyNamingStrategy() {
        return _propertyNamingStrategy;
    }

    public TypeFactory getTypeFactory() {
        return _typeFactory;
    }

    public Locale getLocale() {
        return _locale;
    }

    public Base64Variant getBase64Variant() {
        return _defaultBase64;
    }
}
