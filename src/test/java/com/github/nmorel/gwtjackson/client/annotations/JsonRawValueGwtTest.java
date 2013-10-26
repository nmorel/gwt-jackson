package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRawValueTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRawValueTester.ClassGetter;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonRawValueGwtTest extends GwtJacksonTestCase {

    public interface ClassGetterString extends ObjectWriter<ClassGetter<String>>, ObjectWriterTester<ClassGetter<String>> {

        static ClassGetterString INSTANCE = GWT.create( ClassGetterString.class );
    }

    public interface ClassGetterInteger extends ObjectWriter<ClassGetter<Integer>>, ObjectWriterTester<ClassGetter<Integer>> {

        static ClassGetterInteger INSTANCE = GWT.create( ClassGetterInteger.class );
    }

    private JsonRawValueTester tester = JsonRawValueTester.INSTANCE;

    public void testSimpleStringGetter() {
        tester.testSimpleStringGetter( ClassGetterString.INSTANCE );
    }

    public void testSimpleNonStringGetter() {
        tester.testSimpleNonStringGetter( ClassGetterInteger.INSTANCE );
    }

    public void testNullStringGetter() {
        tester.testNullStringGetter( ClassGetterString.INSTANCE );
    }
}
