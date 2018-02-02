package com.github.nmorel.gwtjackson.jackson.client.util;

import com.google.gwt.regexp.shared.RegExp;

public class CharacterUtil {

    private static RegExp JAVA_IDENTIFIER_START = RegExp.compile("[A-Za-z_$]");
    private static RegExp JAVA_IDENTIFIER_PART = RegExp.compile("[A-Za-z0-9_$]");

    public static boolean isISOControl(int character) {
        return false;
    }

    public static boolean isDefined(int character) {
        return true;
    }

    public static boolean isJavaIdentifierStart(int character) {
        return JAVA_IDENTIFIER_START.test(((char) character) + "");
    }

    public static boolean isJavaIdentifierPart(int character) {
        return JAVA_IDENTIFIER_PART.test(((char) character) + "");
    }
}
