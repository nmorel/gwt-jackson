package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester.Animal;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester.ContainerWithField;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester.ContainerWithGetter;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonTypeWithGenericsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testWrapperWithGetter() {
        JsonTypeWithGenericsTester.INSTANCE.testWrapperWithGetter( createMapper( new TypeReference<ContainerWithGetter<Animal>>() {} ) );
    }

    @Test
    public void testWrapperWithField() {
        JsonTypeWithGenericsTester.INSTANCE.testWrapperWithField( createWriter( new TypeReference<ContainerWithField<Animal>>() {} ) );
    }
}
