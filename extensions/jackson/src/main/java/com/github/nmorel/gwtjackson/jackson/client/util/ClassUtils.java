package com.github.nmorel.gwtjackson.jackson.client.util;

public class ClassUtils {

    @SuppressWarnings({"unused", "unchecked"})
    public static <T> boolean isInstanceOf(Class<T> type, Object object) {
        try {
            T objectAsType = (T) object;
        } catch (ClassCastException exception) {
            return false;
        }
        return true;
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static <T, F> boolean isAssignableFrom(Class<T> toType, Class<F> fromType) {
        try {
            T type = (T) (F) null;
        } catch (ClassCastException exception) {
            return false;
        }
        return true;
    }
}
