package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.CharacterJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonDeserializer} implementation for array of char.
 *
 * @author Nicolas Morel
 */
public class PrimitiveCharacterArrayJsonDeserializer extends AbstractArrayJsonDeserializer<char[]> {

    private static final PrimitiveCharacterArrayJsonDeserializer INSTANCE = new PrimitiveCharacterArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveCharacterArrayJsonDeserializer}
     */
    public static PrimitiveCharacterArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveCharacterArrayJsonDeserializer() { }

    @Override
    public char[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        List<Character> list = deserializeIntoList( reader, ctx, CharacterJsonDeserializer.getInstance() );

        char[] result = new char[list.size()];
        int i = 0;
        for ( Character value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    @Override
    protected char[] doDeserializeNonArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( JsonToken.STRING == reader.peek() ) {
            return reader.nextString().toCharArray();
        } else if ( ctx.isAcceptSingleValueAsArray() ) {
            return doDeserializeSingleArray( reader, ctx );
        } else {
            throw ctx.traceError( "Cannot deserialize a char[] out of " + reader.peek() + " token", reader );
        }
    }

    @Override
    protected char[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return new char[]{CharacterJsonDeserializer.getInstance().deserialize( reader, ctx )};
    }
}
