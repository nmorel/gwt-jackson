package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayInteger;

/**
 * Default {@link JsonDeserializer} implementation for array of int.
 *
 * @author Nicolas Morel
 */
public class PrimitiveIntegerArrayJsonDeserializer extends AbstractArrayJsonDeserializer<int[]> {

    private static final PrimitiveIntegerArrayJsonDeserializer INSTANCE = new PrimitiveIntegerArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveIntegerArrayJsonDeserializer}
     */
    public static PrimitiveIntegerArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private static native int[] reinterpretCast( JsArrayInteger value ) /*-{
        return value;
    }-*/;

    private static int DEFAULT;

    private PrimitiveIntegerArrayJsonDeserializer() { }

    @Override
    public int[] doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        JsArrayInteger jsArray = JsArrayInteger.createArray().cast();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            if ( JsonToken.NULL == reader.peek() ) {
                reader.skipValue();
                jsArray.push( DEFAULT );
            } else {
                jsArray.push( reader.nextInt() );
            }
        }
        reader.endArray();

        if ( GWT.isScript() ) {
            return reinterpretCast( jsArray );
        } else {
            int length = jsArray.length();
            int[] ret = new int[length];
            for ( int i = 0; i < length; i++ ) {
                ret[i] = jsArray.get( i );
            }
            return ret;
        }
    }
}
