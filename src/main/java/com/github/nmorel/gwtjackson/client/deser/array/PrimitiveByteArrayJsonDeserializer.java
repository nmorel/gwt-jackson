package com.github.nmorel.gwtjackson.client.deser.array;

import java.io.IOException;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.ByteJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.utils.Base64;

/**
 * Default {@link JsonDeserializer} implementation for array of byte.
 *
 * @author Nicolas Morel
 */
public class PrimitiveByteArrayJsonDeserializer extends AbstractArrayJsonDeserializer<byte[]> {

    private static final PrimitiveByteArrayJsonDeserializer INSTANCE = new PrimitiveByteArrayJsonDeserializer();

    /**
     * @return an instance of {@link PrimitiveByteArrayJsonDeserializer}
     */
    public static PrimitiveByteArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveByteArrayJsonDeserializer() { }

    @Override
    public byte[] doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        List<Byte> list = deserializeIntoList( reader, ctx, ByteJsonDeserializer.getInstance() );

        byte[] result = new byte[list.size()];
        int i = 0;
        for ( Byte value : list ) {
            if ( null != value ) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    @Override
    protected byte[] doDeserializeNonArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( JsonToken.STRING == reader.peek() ) {
            return Base64.decode( reader.nextString() ).getBytes();
        } else if ( ctx.isAcceptSingleValueAsArray() ) {
            return doDeserializeSingleArray( reader, ctx );
        } else {
            throw ctx.traceError( "Cannot deserialize a byte[] out of " + reader.peek() + " token", reader );
        }
    }

    @Override
    protected byte[] doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return new byte[]{ByteJsonDeserializer.getInstance().deserialize( reader, ctx )};
    }
}
