package com.fasterxml.jackson.core.util;

import java.io.*;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.fasterxml.jackson.core.Version;

/**
 * Functionality for supporting exposing of component {@link Version}s.
 * Also contains other misc methods that have no other place to live in.
 *<p>
 * Note that this class can be used in two roles: first, as a static
 * utility class for loading purposes, and second, as a singleton
 * loader of per-module version information.
 *<p>
 * Note that method for accessing version information changed between versions
 * 2.1 and 2.2; earlier code used file named "VERSION.txt"; but this has serious
 * performance issues on some platforms (Android), so a replacement system
 * was implemented to use class generation and dynamic class loading.
 *<p>
 * Note that functionality for reading "VERSION.txt" was removed completely
 * from Jackson 2.6.
 */
public class VersionUtil {

    private final static RegExp V_SEP = RegExp.compile("[-_./;:]");

    private final Version _v;

    /*
    /**********************************************************
    /* Instance life-cycle
    /**********************************************************
     */

    protected VersionUtil() {
        Version v = null;
        try {
            /* Class we pass only matters for resource-loading: can't use this Class
             * (as it's just being loaded at this point), nor anything that depends on it.
             */
            v = VersionUtil.versionFor(getClass());
        } catch (Exception e) { // not good to dump to stderr; but that's all we have at this low level
            System.err.println("ERROR: Failed to load Version information from " + getClass());
        }
        if (v == null) {
            v = Version.unknownVersion();
        }
        _v = v;
    }

    public Version version() {
        return _v;
    }

    /*
    /**********************************************************
    /* Static load methods
    /**********************************************************
     */

    /**
     * Helper method that will try to load version information for specified
     * class. Implementation is as follows:
     *
     * First, tries to load version info from a class named
     * "PackageVersion" in the same package as the class.
     *
     * If no version information is found, {@link Version#unknownVersion()} is returned.
     */
    public static Version versionFor(Class<?> cls) {
        Version version = packageVersionFor(cls);
        return version == null ? Version.unknownVersion() : version;
    }

    /**
     * Loads version information by introspecting a class named
     * "PackageVersion" in the same package as the given class.
     *<p>
     * If the class could not be found or does not have a public
     * static Version field named "VERSION", returns null.
     */
    public static Version packageVersionFor(Class<?> cls) {
        return Version.unknownVersion();
    }

    /**
     * Method used by <code>PackageVersion</code> classes to decode version injected by Maven build.
     */
    public static Version parseVersion(String s, String groupId, String artifactId) {
        if (s != null && (s = s.trim()).length() > 0) {
            SplitResult parts = V_SEP.split(s);
            return new Version(parseVersionPart(parts.get(0)),
                    (parts.length() > 1) ? parseVersionPart(parts.get(1)) : 0,
                    (parts.length() > 2) ? parseVersionPart(parts.get(2)) : 0,
                    (parts.length() > 3) ? parts.get(3) : null,
                    groupId, artifactId);
        }
        return Version.unknownVersion();
    }

    protected static int parseVersionPart(String s) {
        int number = 0;
        for (int i = 0, len = s.length(); i < len; ++i) {
            char c = s.charAt(i);
            if (c > '9' || c < '0')
                break;
            number = (number * 10) + (c - '0');
        }
        return number;
    }

    private final static void _close(Closeable c) {
        try {
            c.close();
        } catch (IOException e) {
        }
    }

    /*
    /**********************************************************
    /* Orphan utility methods
    /**********************************************************
     */

    public final static void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }
}
