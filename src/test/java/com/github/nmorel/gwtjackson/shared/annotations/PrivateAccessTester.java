package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/** @author Nicolas Morel */
public final class PrivateAccessTester extends AbstractTester
{
    @JsonAutoDetect( getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.ANY )
    @JsonPropertyOrder( alphabetic = true )
    public static class PrivateBean
    {
        @JsonProperty( value = "private" )
        private String privateField;
        private boolean usedPrivateGetter;
        private boolean usedPrivateSetter;
        private PrivateBean privateAccessors;

        private PrivateBean getPrivateAccessors()
        {
            usedPrivateGetter = true;
            return privateAccessors;
        }

        private void setPrivateAccessors( PrivateBean privateAccessors )
        {
            usedPrivateSetter = true;
            this.privateAccessors = privateAccessors;
        }
    }

    public static final PrivateAccessTester INSTANCE = new PrivateAccessTester();

    private PrivateAccessTester()
    {
    }

    public void testEncodingPrivateField( JsonEncoderTester<PrivateBean> encoder )
    {
        PrivateBean bean = new PrivateBean();
        bean.privateField = "IAmAPrivateField";
        PrivateBean internalBean = new PrivateBean();
        internalBean.privateField = "IHavePrivateAccessors";
        bean.privateAccessors = internalBean;

        String expected = "{\"private\":\"IAmAPrivateField\",\"privateAccessors\":{\"private\":\"IHavePrivateAccessors\"}}";
        String result = encoder.encode( bean );

        assertTrue( bean.usedPrivateGetter );
        assertFalse( bean.usedPrivateSetter );
        assertEquals( expected, result );
    }

    public void testDecodingPrivateField( JsonDecoderTester<PrivateBean> decoder )
    {
        String input = "{\"private\":\"IAmAPrivateField\",\"privateAccessors\":{\"private\":\"IHavePrivateAccessors\"}}";

        PrivateBean result = decoder.decode( input );

        assertTrue( result.usedPrivateSetter );
        assertFalse( result.usedPrivateGetter );
        assertEquals( "IAmAPrivateField", result.privateField );
        assertEquals( "IHavePrivateAccessors", result.privateAccessors.privateField );
    }

}
