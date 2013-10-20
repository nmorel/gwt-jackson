package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanArray;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanList;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanMap;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanArray;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanList;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanMap;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonTypeOnPropertiesJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSimpleField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleField( createMapper( FieldWrapperBean.class ) );
    }

    @Test
    public void testSimpleMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleMethod( createMapper( MethodWrapperBean.class ) );
    }

    @Test
    public void testSimpleListField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleListField( createMapper( FieldWrapperBeanList.class ) );
    }

    @Test
    public void testSimpleListMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleListMethod( createMapper( MethodWrapperBeanList.class ) );
    }

    @Test
    public void testSimpleArrayField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleArrayField( createMapper( FieldWrapperBeanArray.class ) );
    }

    @Test
    public void testSimpleArrayMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleArrayMethod( createMapper( MethodWrapperBeanArray.class ) );
    }

    @Test
    public void testSimpleMapField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleMapField( createMapper( FieldWrapperBeanMap.class ) );
    }

    @Test
    public void testSimpleMapMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleMapMethod( createMapper( MethodWrapperBeanMap.class ) );
    }
}
