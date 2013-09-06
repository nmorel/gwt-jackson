package com.github.nmorel.gwtjackson.shared.advanced;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

/** @author Nicolas Morel */
public final class InheritanceTester extends AbstractTester
{
    public static interface InterfaceBean
    {
        @JsonProperty( value = "propertyOnInterface" )
        String getInterfaceProperty();

        void setInterfaceProperty( String interfaceProperty );
    }

    @JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE )
    public static abstract class ParentBean implements InterfaceBean
    {
        public String protectedField;
        @JsonProperty
        String defaultButAnnotated;

        @JsonProperty( value = "parentProperty" )
        public abstract String getProperty();

        public abstract void setProperty( String property );
    }

    @JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility = JsonAutoDetect.Visibility
        .PUBLIC_ONLY,
        setterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY )
    @JsonPropertyOrder( value = {"parentProperty", "protectedField", "propertyOnInterface", "defaultButAnnotated"} )
    public static class ChildBean extends ParentBean
    {
        private String property;
        private String interfaceProperty;

        @Override
        public String getProperty()
        {
            return property;
        }

        @Override
        public void setProperty( String property )
        {
            this.property = property;
        }

        @Override
        public String getInterfaceProperty()
        {
            return interfaceProperty;
        }

        @Override
        public void setInterfaceProperty( String interfaceProperty )
        {
            this.interfaceProperty = interfaceProperty;
        }
    }

    public static final InheritanceTester INSTANCE = new InheritanceTester();

    private InheritanceTester()
    {
    }

    public void testEncoding( JsonEncoderTester<ChildBean> encoder )
    {
        ChildBean bean = new ChildBean();
        bean.protectedField = "protectedOnParent";
        bean.defaultButAnnotated = "defaultOnParent";
        bean.property = "propertyOnChild";
        bean.interfaceProperty = "propertyOnInterface";

        String expected = "{\"parentProperty\":\"propertyOnChild\"," +
            "\"protectedField\":\"protectedOnParent\"," +
            "\"propertyOnInterface\":\"propertyOnInterface\"," +
            "\"defaultButAnnotated\":\"defaultOnParent\"}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecoding( JsonDecoderTester<ChildBean> decoder )
    {
        String input = "{\"protectedField\":\"protectedOnParent\"," +
            "\"defaultButAnnotated\":\"defaultOnParent\"," +
            "\"parentProperty\":\"propertyOnChild\"," +
            "\"propertyOnInterface\":\"propertyOnInterface\"}";

        ChildBean result = decoder.decode( input );
        assertEquals( "protectedOnParent", result.protectedField );
        assertEquals( "defaultOnParent", result.defaultButAnnotated );
        assertEquals( "propertyOnChild", result.property );
        assertEquals( "propertyOnInterface", result.interfaceProperty );
    }

}
