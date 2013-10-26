package com.github.nmorel.gwtjackson.jackson.annotations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRawValueTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRawValueTester.ClassGetter;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonRawValueJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSimpleStringGetter() {
        JsonRawValueTester.INSTANCE.testSimpleStringGetter( createMapper( new TypeReference<ClassGetter<String>>() {} ) );
    }

    @Test
    public void testSimpleNonStringGetter() {
        JsonRawValueTester.INSTANCE.testSimpleNonStringGetter( createMapper( new TypeReference<ClassGetter<Integer>>() {} ) );
    }

    @Test
    public void testNullStringGetter() {
        JsonRawValueTester.INSTANCE.testNullStringGetter( createMapper( new TypeReference<ClassGetter<String>>() {} ) );
    }
}
