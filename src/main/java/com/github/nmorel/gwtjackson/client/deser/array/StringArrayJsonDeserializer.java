package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;

/**
 * Default {@link JsonDeserializer} implementation for array of {@link String}.
 *
 * @author Nicolas Morel
 */
public class StringArrayJsonDeserializer extends AbstractArrayJsonDeserializer<String[]> {

    private static final StringArrayJsonDeserializer INSTANCE = new StringArrayJsonDeserializer();

    /**
     * @return an instance of {@link StringArrayJsonDeserializer}
     */
    public static StringArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private static native String[] reinterpretCast( JsArrayString value ) /*-{
        return value;
    }-*/;

    private StringArrayJsonDeserializer() { }

    @Override
    public String[] doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        JsArrayString jsArray = JsArrayString.createArray().cast();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            if ( JsonToken.NULL == reader.peek() ) {
                reader.skipValue();
                jsArray.push( null );
            } else {
                jsArray.push( reader.nextString() );
            }
        }
        reader.endArray();

        if ( GWT.isScript() ) {
            return reinterpretCast( jsArray );
        } else {
            int length = jsArray.length();
            String[] ret = new String[length];
            for ( int i = 0; i < length; i++ ) {
                ret[i] = jsArray.get( i );
            }
            return ret;
        }
    }
}
