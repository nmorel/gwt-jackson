package com.fasterxml.jackson.databind.util;

import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.github.nmorel.gwtjackson.jackson.client.util.ClassUtils;

public final class ClassUtil {

    private final static Class<?> CLS_OBJECT = Object.class;

    private final static Annotation[] NO_ANNOTATIONS = new Annotation[0];

    /*
    /**********************************************************
    /* Helper classes
    /**********************************************************
     */

    /* 21-Feb-2016, tatu: Unfortunately `Collections.emptyIterator()` only
     *   comes with JDK7, so we'll still have to include our bogus implementation
     *   for as long as we want JDK6 runtime compatibility
     */
    private final static class EmptyIterator<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final static EmptyIterator<?> EMPTY_ITERATOR = new EmptyIterator<Object>();

    /*
    /**********************************************************
    /* Simple factory methods
    /**********************************************************
     */

    /**
     * @since 2.7
     */
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> emptyIterator() {
        // 21-Feb-2016, tatu: As per above, use a locally defined empty iterator
        //        return Collections.emptyIterator();
        return (Iterator<T>) EMPTY_ITERATOR;
    }

    /*
    /**********************************************************
    /* Methods that deal with inheritance
    /**********************************************************
     */

    /**
     * Method that will find all sub-classes and implemented interfaces
     * of a given class or interface. Classes are listed in order of
     * precedence, starting with the immediate super-class, followed by
     * interfaces class directly declares to implemented, and then recursively
     * followed by parent of super-class and so forth.
     * Note that <code>Object.class</code> is not included in the list
     * regardless of whether <code>endBefore</code> argument is defined or not.
     *
     * @param endBefore Super-type to NOT include in results, if any; when
     *    encountered, will be ignored (and no super types are checked).
     *
     * @since 2.7
     */
    public static List<JavaType> findSuperTypes(JavaType type, Class<?> endBefore,
            boolean addClassItself) {
        if ((type == null) || type.hasRawClass(endBefore) || type.hasRawClass(Object.class)) {
            return Collections.emptyList();
        }
        List<JavaType> result = new ArrayList<JavaType>(8);
        _addSuperTypes(type, endBefore, result, addClassItself);
        return result;
    }

    /**
     * @since 2.7
     */
    public static List<Class<?>> findRawSuperTypes(Class<?> cls, Class<?> endBefore, boolean addClassItself) {
        if ((cls == null) || (cls == endBefore) || (cls == Object.class)) {
            return Collections.emptyList();
        }
        List<Class<?>> result = new ArrayList<Class<?>>(8);
        _addRawSuperTypes(cls, endBefore, result, addClassItself);
        return result;
    }

    /**
     * Method for finding all super classes (but not super interfaces) of given class,
     * starting with the immediate super class and ending in the most distant one.
     * Class itself is included if <code>addClassItself</code> is true.
     *
     * @since 2.7
     */
    public static List<Class<?>> findSuperClasses(Class<?> cls, Class<?> endBefore,
            boolean addClassItself) {
        List<Class<?>> result = new LinkedList<Class<?>>();
        if ((cls != null) && (cls != endBefore)) {
            if (addClassItself) {
                result.add(cls);
            }
            while ((cls = cls.getSuperclass()) != null) {
                if (cls == endBefore) {
                    break;
                }
                result.add(cls);
            }
        }
        return result;
    }

    @Deprecated // since 2.7
    public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore) {
        return findSuperTypes(cls, endBefore, new ArrayList<Class<?>>(8));
    }

    @Deprecated // since 2.7
    public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore, List<Class<?>> result) {
        _addRawSuperTypes(cls, endBefore, result, false);
        return result;
    }

    private static void _addSuperTypes(JavaType type, Class<?> endBefore, Collection<JavaType> result,
            boolean addClassItself) {
        if (type == null) {
            return;
        }
        final Class<?> cls = type.getRawClass();
        if (cls == endBefore || cls == Object.class) {
            return;
        }
        if (addClassItself) {
            if (result.contains(type)) { // already added, no need to check supers
                return;
            }
            result.add(type);
        }
        _addSuperTypes(type.getSuperClass(), endBefore, result, true);
    }

    private static void _addRawSuperTypes(Class<?> cls, Class<?> endBefore, Collection<Class<?>> result,
            boolean addClassItself) {
        if (cls == endBefore || cls == null || cls == Object.class) {
            return;
        }
        if (addClassItself) {
            if (result.contains(cls)) { // already added, no need to check supers
                return;
            }
            result.add(cls);
        }
        for (Class<?> intCls : _interfaces(cls)) {
            _addRawSuperTypes(intCls, endBefore, result, true);
        }
        _addRawSuperTypes(cls.getSuperclass(), endBefore, result, true);
    }

    /*
    /**********************************************************
    /* Class type detection methods
    /**********************************************************
     */

    /**
     * @return Null if class might be a bean; type String (that identifies
     *   why it's not a bean) if not
     */
    public static String canBeABeanType(Class<?> type) {
        // First: language constructs that ain't beans:
        if (type.isArray()) {
            return "array";
        }
        if (type.isEnum()) {
            return "enum";
        }
        if (type.isPrimitive()) {
            return "primitive";
        }

        // Anything else? Seems valid, then
        return null;
    }

    /**
     * Helper method used to weed out dynamic Proxy types; types that do
     * not expose concrete method API that we could use to figure out
     * automatic Bean (property) based serialization.
     */
    public static boolean isProxyType(Class<?> type) {
        // As per [databind#57], should NOT disqualify JDK proxy:
        /*
        // Then: well-known proxy (etc) classes
        if (Proxy.isProxyClass(type)) {
            return true;
        }
        */
        String name = type.getName();
        // Hibernate uses proxies heavily as well:
        if (name.startsWith("net.sf.cglib.proxy.")
                || name.startsWith("org.hibernate.proxy.")) {
            return true;
        }
        // Not one of known proxies, nope:
        return false;
    }

    /**
     * Helper method that checks if given class is a concrete one;
     * that is, not an interface or abstract class.
     */
    public static boolean isConcrete(Class<?> type) {
        return true;
    }

    public static boolean isCollectionMapOrArray(Class<?> type) {
        if (type.isArray())
            return true;
        if (ClassUtils.isAssignableFrom(Collection.class, type))
            return true;
        if (ClassUtils.isAssignableFrom(Map.class, type))
            return true;
        return false;
    }

    /*
    /**********************************************************
    /* Type name handling methods
    /**********************************************************
     */

    /**
     * Helper method used to construct appropriate description
     * when passed either type (Class) or an instance; in latter
     * case, class of instance is to be used.
     */
    public static String getClassDescription(Object classOrInstance) {
        if (classOrInstance == null) {
            return "unknown";
        }
        Class<?> cls = (classOrInstance instanceof Class<?>) ? (Class<?>) classOrInstance : classOrInstance.getClass();
        return cls.getName();
    }

    /*
    /**********************************************************
    /* Class loading
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Method type detection methods
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Exception handling
    /**********************************************************
     */

    /**
     * Method that can be used to find the "root cause", innermost
     * of chained (wrapped) exceptions.
     */
    public static Throwable getRootCause(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    /**
     * Method that will unwrap root causes of given Throwable, and throw
     * the innermost {@link Exception} or {@link Error} as is.
     * This is useful in cases where mandatory wrapping is added, which
     * is often done by Reflection API.
     */
    public static void throwRootCause(Throwable t) throws Exception {
        t = getRootCause(t);
        if (t instanceof Exception) {
            throw (Exception) t;
        }
        throw (Error) t;
    }

    /**
     * Method that works like {@link #throwRootCause} if (and only if)
     * root cause is an {@link IOException}; otherwise returns root cause
     *
     * @since 2.8
     */
    public static Throwable throwRootCauseIfIOE(Throwable t) throws IOException {
        t = getRootCause(t);
        if (t instanceof IOException) {
            throw (IOException) t;
        }
        return t;
    }

    /**
     * Method that will wrap 't' as an {@link IllegalArgumentException} if it
     * is a checked exception; otherwise (runtime exception or error) throw as is
     */
    public static void throwAsIAE(Throwable t) {
        throwAsIAE(t, t.getMessage());
    }

    /**
     * Method that will wrap 't' as an {@link IllegalArgumentException} (and with
     * specified message) if it
     * is a checked exception; otherwise (runtime exception or error) throw as is
     */
    public static void throwAsIAE(Throwable t, String msg) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        if (t instanceof Error) {
            throw (Error) t;
        }
        throw new IllegalArgumentException(msg, t);
    }

    /**
     * Method that will locate the innermost exception for given Throwable;
     * and then wrap it as an {@link IllegalArgumentException} if it
     * is a checked exception; otherwise (runtime exception or error) throw as is
     */
    public static void unwrapAndThrowAsIAE(Throwable t) {
        throwAsIAE(getRootCause(t));
    }

    /**
     * Method that will locate the innermost exception for given Throwable;
     * and then wrap it as an {@link IllegalArgumentException} if it
     * is a checked exception; otherwise (runtime exception or error) throw as is
     */
    public static void unwrapAndThrowAsIAE(Throwable t, String msg) {
        throwAsIAE(getRootCause(t), msg);
    }

    /**
     * Helper method that encapsulate logic in trying to close output generator
     * in case of failure; useful mostly in forcing flush()ing as otherwise
     * error conditions tend to be hard to diagnose. However, it is often the
     * case that output state may be corrupt so we need to be prepared for
     * secondary exception without masking original one.
     *
     * @since 2.8
     */
    public static void closeOnFailAndThrowAsIAE(JsonGenerator g, Exception fail)
            throws IOException {
        /* 04-Mar-2014, tatu: Let's try to prevent auto-closing of
         *    structures, which typically causes more damage.
         */
        g.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
        try {
            g.close();
        } catch (Exception e) {
            fail.addSuppressed(e);
        }
        if (fail instanceof IOException) {
            throw (IOException) fail;
        }
        if (fail instanceof RuntimeException) {
            throw (RuntimeException) fail;
        }
        throw new RuntimeException(fail);
    }

    /**
     * Helper method that encapsulate logic in trying to close given {@link Closeable}
     * in case of failure; useful mostly in forcing flush()ing as otherwise
     * error conditions tend to be hard to diagnose. However, it is often the
     * case that output state may be corrupt so we need to be prepared for
     * secondary exception without masking original one.
     *
     * @since 2.8
     */
    public static void closeOnFailAndThrowAsIAE(JsonGenerator g,
            Closeable toClose, Exception fail)
            throws IOException {
        if (g != null) {
            g.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
            try {
                g.close();
            } catch (Exception e) {
                fail.addSuppressed(e);
            }
        }
        if (toClose != null) {
            try {
                toClose.close();
            } catch (Exception e) {
                fail.addSuppressed(e);
            }
        }
        if (fail instanceof IOException) {
            throw (IOException) fail;
        }
        if (fail instanceof RuntimeException) {
            throw (RuntimeException) fail;
        }
        throw new RuntimeException(fail);
    }

    /*
    /**********************************************************
    /* Instantiation
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Primitive type support
    /**********************************************************
     */

    /**
     * Helper method used to get default value for wrappers used for primitive types
     * (0 for Integer etc)
     */
    public static Object defaultValue(Class<?> cls) {
        if (cls == Integer.TYPE) {
            return Integer.valueOf(0);
        }
        if (cls == Long.TYPE) {
            return Long.valueOf(0L);
        }
        if (cls == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (cls == Double.TYPE) {
            return Double.valueOf(0.0);
        }
        if (cls == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (cls == Byte.TYPE) {
            return Byte.valueOf((byte) 0);
        }
        if (cls == Short.TYPE) {
            return Short.valueOf((short) 0);
        }
        if (cls == Character.TYPE) {
            return '\0';
        }
        throw new IllegalArgumentException("Class " + cls.getName() + " is not a primitive type");
    }

    /**
     * Helper method for finding wrapper type for given primitive type (why isn't
     * there one in JDK?)
     */
    public static Class<?> wrapperType(Class<?> primitiveType) {
        if (primitiveType == Integer.TYPE) {
            return Integer.class;
        }
        if (primitiveType == Long.TYPE) {
            return Long.class;
        }
        if (primitiveType == Boolean.TYPE) {
            return Boolean.class;
        }
        if (primitiveType == Double.TYPE) {
            return Double.class;
        }
        if (primitiveType == Float.TYPE) {
            return Float.class;
        }
        if (primitiveType == Byte.TYPE) {
            return Byte.class;
        }
        if (primitiveType == Short.TYPE) {
            return Short.class;
        }
        if (primitiveType == Character.TYPE) {
            return Character.class;
        }
        throw new IllegalArgumentException("Class " + primitiveType.getName() + " is not a primitive type");
    }

    /**
     * Method that can be used to find primitive type for given class if (but only if)
     * it is either wrapper type or primitive type; returns `null` if type is neither.
     *
     * @since 2.7
     */
    public static Class<?> primitiveType(Class<?> type) {
        if (type.isPrimitive()) {
            return type;
        }

        if (type == Integer.class) {
            return Integer.TYPE;
        }
        if (type == Long.class) {
            return Long.TYPE;
        }
        if (type == Boolean.class) {
            return Boolean.TYPE;
        }
        if (type == Double.class) {
            return Double.TYPE;
        }
        if (type == Float.class) {
            return Float.TYPE;
        }
        if (type == Byte.class) {
            return Byte.TYPE;
        }
        if (type == Short.class) {
            return Short.TYPE;
        }
        if (type == Character.class) {
            return Character.TYPE;
        }
        return null;
    }

    /*
    /**********************************************************
    /* Access checking/handling methods
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Enum type detection
    /**********************************************************
     */

    /**
     * Helper method that can be used to dynamically figure out
     * enumeration type of given {@link EnumSet}, without having
     * access to its declaration.
     * Code is needed to work around design flaw in JDK.
     */
    public static Class<? extends Enum> findEnumType(EnumSet<?> s) {
        // First things first: if not empty, easy to determine
        if (!s.isEmpty()) {
            return findEnumType(s.iterator().next());
        }
        // Otherwise need to locate using an internal field
        return Enum.class;
    }

    /**
     * Helper method that can be used to dynamically figure out
     * enumeration type of given {@link EnumSet}, without having
     * access to its declaration.
     * Code is needed to work around design flaw in JDK.
     */
    public static Class<? extends Enum> findEnumType(EnumMap<?, ?> m) {
        if (!m.isEmpty()) {
            return findEnumType(m.keySet().iterator().next());
        }
        // Otherwise need to locate using an internal field
        return Enum.class;
    }

    /**
     * Helper method that can be used to dynamically figure out formal
     * enumeration type (class) for given enumeration. This is either
     * class of enum instance (for "simple" enumerations), or its
     * superclass (for enums with instance fields or methods)
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Enum<?>> findEnumType(Enum<?> en) {
        // enums with "body" are sub-classes of the formal type
        Class<?> ec = en.getClass();
        if (ec.getSuperclass() != Enum.class) {
            ec = ec.getSuperclass();
        }
        return (Class<? extends Enum<?>>) ec;
    }

    /**
     * Helper method that can be used to dynamically figure out formal
     * enumeration type (class) for given class of an enumeration value.
     * This is either class of enum instance (for "simple" enumerations),
     * or its superclass (for enums with instance fields or methods)
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Enum<?>> findEnumType(Class<?> cls) {
        // enums with "body" are sub-classes of the formal type
        if (cls.getSuperclass() != Enum.class) {
            cls = cls.getSuperclass();
        }
        return (Class<? extends Enum<?>>) cls;
    }

    /*
    /**********************************************************
    /* Jackson-specific stuff
    /**********************************************************
     */

    /**
     * Method that can be called to determine if given Object is the default
     * implementation Jackson uses; as opposed to a custom serializer installed by
     * a module or calling application. Determination is done using
     * {@link JacksonStdImpl} annotation on handler (serializer, deserializer etc)
     * class.
     */
    public static boolean isJacksonStdImpl(Object impl) {
        return (impl != null) && isJacksonStdImpl(impl.getClass());
    }

    public static boolean isJacksonStdImpl(Class<?> implClass) {
        return false;
    }

    public static boolean isBogusClass(Class<?> cls) {
        return (cls == Void.class || cls == Void.TYPE
                || cls == com.fasterxml.jackson.databind.annotation.NoClass.class);
    }

    /**
     * @since 2.7
     */
    public static boolean isObjectOrPrimitive(Class<?> cls) {
        return (cls == CLS_OBJECT) || cls.isPrimitive();
    }

    /*
    /**********************************************************
    /* Access to various Class definition aspects; possibly
    /* cacheable; and attempts was made in 2.7.0 - 2.7.7; however
    /* unintented retention (~= memory leak) wrt [databind#1363]
    /* resulted in removal of caching
    /**********************************************************
     */

    /**
     * @since 2.7
     */
    public static String getPackageName(Class<?> cls) {
        return null;
    }

    /**
     * @since 2.7
     */
    public static boolean hasEnclosingMethod(Class<?> cls) {
        return false;
    }

    /**
     * @since 2.7
     */
    public static Annotation[] findClassAnnotations(Class<?> cls) {
        return NO_ANNOTATIONS;
    }

    // // // Then methods that do NOT cache access but were considered
    // // // (and could be added to do caching if it was proven effective)

    /**
     * @since 2.7
     */
    public static Class<?> getDeclaringClass(Class<?> cls) {
        return isObjectOrPrimitive(cls) ? null : null;
    }

    /**
     * @since 2.7
     */
    public static Type getGenericSuperclass(Class<?> cls) {
        return cls.getSuperclass();
    }

    /**
     * @since 2.7
     */
    public static Type[] getGenericInterfaces(Class<?> cls) {
        return new Type[0];
    }

    /**
     * @since 2.7
     */
    public static Class<?> getEnclosingClass(Class<?> cls) {
        // Caching does not seem worthwhile, as per profiling
        return isObjectOrPrimitive(cls) ? null : null;
    }

    private static Class<?>[] _interfaces(Class<?> cls) {
        return new Class<?>[0];
    }

    /*
    /**********************************************************
    /* Helper classes
    /**********************************************************
     */

    /*
    /**********************************************************
    /* Helper classed used for caching
    /**********************************************************
     */

}
