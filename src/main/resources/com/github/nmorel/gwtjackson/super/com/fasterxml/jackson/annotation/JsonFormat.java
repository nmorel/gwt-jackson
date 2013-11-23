package com.fasterxml.jackson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Super source for {@link com.fasterxml.jackson.annotation.JsonFormat} to remove the use of java.util.Locale and java.util.TimeZone
 * classes
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
    ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonFormat
{
    /**
     * Value that indicates that default {@link java.util.Locale}
     * (from deserialization or serialization context) should be used:
     * annotation does not define value to use.
     */
    public final static String DEFAULT_LOCALE = "##default";

    /**
     * Value that indicates that default {@link java.util.TimeZone}
     * (from deserialization or serialization context) should be used:
     * annotation does not define value to use.
     */
    public final static String DEFAULT_TIMEZONE = "##default";

    /**
     * Datatype-specific additional piece of configuration that may be used
     * to further refine formatting aspects. This may, for example, determine
     * low-level format String used for {@link java.util.Date} serialization;
     * however, exact use is determined by specific <code>JsonSerializer</code>
     */
    public String pattern() default "";

    /**
     * Structure to use for serialization: definition of mapping depends on datatype,
     * but usually has straight-forward counterpart in data format (JSON).
     * Note that commonly only a subset of shapes is available; and if 'invalid' value
     * is chosen, defaults are usually used.
     */
    public Shape shape() default Shape.ANY;

    /**
     * {@link java.util.Locale} to use for serialization (if needed).
     * Special value of {@link #DEFAULT_LOCALE}
     * can be used to mean "just use the default", where default is specified
     * by the serialization context, which in turn defaults to system
     * defaults ({@link java.util.Locale#getDefault()}) unless explicitly
     * set to another locale.
     */
    public String locale() default DEFAULT_LOCALE;

    /**
     * {@link java.util.TimeZone} to use for serialization (if needed).
     * Special value of {@link #DEFAULT_TIMEZONE}
     * can be used to mean "just use the default", where default is specified
     * by the serialization context, which in turn defaults to system
     * defaults ({@link java.util.TimeZone#getDefault()}) unless explicitly
     * set to another locale.
     */
    public String timezone() default DEFAULT_TIMEZONE;

    /*
    /**********************************************************
    /* Value enumeration(s), value class(es)
    /**********************************************************
     */

    /**
     * Value enumeration used for indicating preferred Shape; translates
     * loosely to JSON types, with some extra values to indicate less precise
     * choices (i.e. allowing one of multiple actual shapes)
     */
    public enum Shape
    {
        /**
         * Marker enum value that indicates "default" (or "whatever") choice; needed
         * since Annotations can not have null values for enums.
         */
        ANY,

        /**
         * Value that indicates shape should not be structural (that is, not
         * {@link #ARRAY} or {@link #OBJECT}, but can be any other shape.
         */
        SCALAR,

        /**
         * Value that indicates that (JSON) Array type should be used.
         */
        ARRAY,

        /**
         * Value that indicates that (JSON) Object type should be used.
         */
        OBJECT,

        /**
         * Value that indicates that a numeric (JSON) type should be used
         * (but does not specify whether integer or floating-point representation
         * should be used)
         */
        NUMBER,

        /**
         * Value that indicates that floating-point numeric type should be used
         */
        NUMBER_FLOAT,

        /**
         * Value that indicates that integer number type should be used
         * (and not {@link #NUMBER_FLOAT}).
         */
        NUMBER_INT,

        /**
         * Value that indicates that (JSON) String type should be used.
         */
        STRING,

        /**
         * Value that indicates that (JSON) boolean type
         * (true, false) should be used.
         */
        BOOLEAN
        ;

        public boolean isNumeric() {
            return (this == NUMBER) || (this == NUMBER_INT) || (this == NUMBER_FLOAT);
        }

        public boolean isStructured() {
            return (this == OBJECT) || (this == ARRAY);
        }
    }
}
