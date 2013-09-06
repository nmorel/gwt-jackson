package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

/** @author Nicolas Morel */
public final class JsonAutoDetectTester extends AbstractTester
{
    @JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility = JsonAutoDetect.Visibility.NONE )
    public static class BeanOne
    {
        public String publicFieldVisible;
        protected String protectedFieldVisible;
        private Integer notVisibleField;
        private String visibleWithSetter;
        @JsonProperty
        private String fieldWithJsonProperty;

        public String getVisibleWithSetter()
        {
            return visibleWithSetter;
        }

        public void setVisibleWithSetter( String visibleWithSetter )
        {
            this.visibleWithSetter = visibleWithSetter;
        }

        String getFieldWithJsonProperty()
        {
            return fieldWithJsonProperty;
        }

        void setFieldWithJsonProperty( String fieldWithJsonProperty )
        {
            this.fieldWithJsonProperty = fieldWithJsonProperty;
        }
    }

    public static final JsonAutoDetectTester INSTANCE = new JsonAutoDetectTester();

    private JsonAutoDetectTester()
    {
    }

    public void testEncodingAutoDetection( JsonEncoderTester<BeanOne> encoder )
    {
        BeanOne bean = new BeanOne();
        bean.notVisibleField = 1;
        bean.setVisibleWithSetter( "Hello" );
        bean.protectedFieldVisible = "protectedField";
        bean.publicFieldVisible = "publicField";
        bean.fieldWithJsonProperty = "jsonProperty";

        String expected = "{\"publicFieldVisible\":\"publicField\"," +
            "\"protectedFieldVisible\":\"protectedField\"," +
            "\"fieldWithJsonProperty\":\"jsonProperty\"}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingAutoDetection( JsonDecoderTester<BeanOne> decoder )
    {
        String input = "{\"visibleWithSetter\":\"Hello\"," +
            "\"publicFieldVisible\":\"publicField\"," +
            "\"protectedFieldVisible\":\"protectedField\"," +
            "\"notVisibleField\":2," +
            "\"fieldWithJsonProperty\":\"jsonProperty\"}";

        BeanOne result = decoder.decode( input );

        assertNull( result.notVisibleField );
        assertEquals( "Hello", result.visibleWithSetter );
        assertEquals( "protectedField", result.protectedFieldVisible );
        assertEquals( "publicField", result.publicFieldVisible );
        assertEquals( "jsonProperty", result.fieldWithJsonProperty );
    }

}
