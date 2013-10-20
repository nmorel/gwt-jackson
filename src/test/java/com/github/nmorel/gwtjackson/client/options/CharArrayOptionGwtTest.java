package com.github.nmorel.gwtjackson.client.options;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.options.CharArrayOptionTester;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class CharArrayOptionGwtTest extends GwtJacksonTestCase {

    public interface CharArrayMapper extends ObjectMapper<char[]> {

        static CharArrayMapper INSTANCE = GWT.create( CharArrayMapper.class );
    }

    public void testCharArraysAsString() {
        CharArrayOptionTester.INSTANCE.testCharArraysAsString( createMapper( CharArrayMapper.INSTANCE ) );
    }

    public void testCharArraysAsArray() {
        CharArrayOptionTester.INSTANCE
            .testCharArraysAsArray( createMapper( CharArrayMapper.INSTANCE, new JsonDeserializationContext.Builder()
                .build(), new JsonSerializationContext.Builder().writeCharArraysAsJsonArrays( true ).build() ) );
    }
}
