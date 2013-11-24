package com.fasterxml.jackson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

/**
 * Super source for {@link com.fasterxml.jackson.annotation.JsonAutoDetect} to remove the use of java.lang.reflect.* classes
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonAutoDetect
{
    /**
     * Enumeration for possible visibility thresholds (minimum visibility)
     * that can be used to limit which methods (and fields) are
     * auto-detected.
     */
    public enum Visibility {
        /**
         * Value that means that all kinds of access modifiers are acceptable,
         * from private to public.
         */
        ANY,
        /**
         * Value that means that any other access modifier other than 'private'
         * is considered auto-detectable.
         */
        NON_PRIVATE,
        /**
         * Value that means access modifiers 'protected' and 'public' are
         * auto-detectable (and 'private' and "package access" == no modifiers
         * are not)
         */
        PROTECTED_AND_PUBLIC,
        /**
         * Value to indicate that only 'public' access modifier is considered
         * auto-detectable.
         */
        PUBLIC_ONLY,
        /**
         * Value that indicates that no access modifiers are auto-detectable:
         * this can be used to explicitly disable auto-detection for specified
         * types.
         */
        NONE,

        /**
         * Value that indicates that default visibility level (whatever it is,
         * depends on context) is to be used. This usually means that inherited
         * value (from parent visibility settings) is to be used.
         */
        DEFAULT;
    }

    /**
     * Minimum visibility required for auto-detecting regular getter methods.
     */
    Visibility getterVisibility() default Visibility.DEFAULT;

    /**
     * Minimum visibility required for auto-detecting is-getter methods.
     */
    Visibility isGetterVisibility() default Visibility.DEFAULT;

    /**
     * Minimum visibility required for auto-detecting setter methods.
     */
    Visibility setterVisibility() default Visibility.DEFAULT;

    /**
     * Minimum visibility required for auto-detecting Creator methods,
     * except for no-argument constructors (which are always detected
     * no matter what).
     */
    Visibility creatorVisibility() default Visibility.DEFAULT;

    /**
     * Minimum visibility required for auto-detecting member fields.
     */
    Visibility fieldVisibility() default Visibility.DEFAULT;
}
