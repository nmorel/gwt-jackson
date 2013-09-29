//package com.github.nmorel.gwtjackson.client.mapper.map;
//
//import java.io.IOException;
//import java.util.Map;
//
//import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
//import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
//import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
//import com.github.nmorel.gwtjackson.client.JsonMapper;
//import com.github.nmorel.gwtjackson.client.stream.JsonReader;
//import com.github.nmorel.gwtjackson.client.stream.JsonToken;
//import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
//
///**
// * Base {@link JsonMapper} implementation for {@link Map}.
// *
// * @param <K> Type of the key
// * @param <V> Type of the value
// * @param <M> {@link Map} type
// * @author Nicolas Morel
// */
//public abstract class BaseMapJsonMapper<K, V, M extends Map<K, V>> extends AbstractJsonMapper<M>
//{
//    protected final JsonMapper<K> keyMapper;
//    protected final JsonMapper<V> valueMapper;
//
//    /**
//     * @param keyMapper {@link JsonMapper} used to map the key
//     * @param valueMapper {@link JsonMapper} used to map the value
//     */
//    public BaseMapJsonMapper( JsonMapper<K> keyMapper, JsonMapper<V> valueMapper )
//    {
//        if ( null == keyMapper )
//        {
//            throw new IllegalArgumentException( "keyMapper can't be null" );
//        }
//        if ( null == valueMapper )
//        {
//            throw new IllegalArgumentException( "valueMapper can't be null" );
//        }
//        this.keyMapper = keyMapper;
//        this.valueMapper = valueMapper;
//    }
//
//    @Override
//    protected M doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
//    {
//        M result = newMap();
//
//        reader.beginObject();
//        while ( JsonToken.NAME.equals( reader.peek() ) )
//        {
//            K key = keyMapper.decodeKey( reader.nextName(), ctx );
//            V value = valueMapper.decode( reader, ctx );
//            result.put( key, value );
//        }
//        reader.endObject();
//
//        return result;
//    }
//
//    /**
//     * Instantiates a new map for decoding process.
//     *
//     * @return the new map
//     */
//    protected abstract M newMap();
//
//    @Override
//    public void doEncode( JsonWriter writer, M map, JsonEncodingContext ctx ) throws IOException
//    {
//        writer.beginObject();
//        for ( Map.Entry<K, V> entry : map.entrySet() )
//        {
//            writer.name( keyMapper.encodeKey( entry.getKey(), ctx ) );
//            valueMapper.encode( writer, entry.getValue(), ctx );
//        }
//        writer.endObject();
//    }
//}
