package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link JsonDeserializer} for {@link Number}.
 *
 * @author Nicolas Morel
 */
public abstract class NumberJsonDeserializer<N extends Number> extends JsonDeserializer<N> {

    private static final NumberJsonDeserializer<BigDecimal> BIG_DECIMAL_INSTANCE = new NumberJsonDeserializer<BigDecimal>() {
        @Override
        public BigDecimal doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            return new BigDecimal( reader.nextString() );
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link BigDecimal}
     */
    public static NumberJsonDeserializer<BigDecimal> getBigDecimalInstance() {
        return BIG_DECIMAL_INSTANCE;
    }

    private static final NumberJsonDeserializer<BigInteger> BIG_INTEGER_INSTANCE = new NumberJsonDeserializer<BigInteger>() {
        @Override
        public BigInteger doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            return new BigInteger( reader.nextString() );
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link BigInteger}
     */
    public static NumberJsonDeserializer<BigInteger> getBigIntegerInstance() {
        return BIG_INTEGER_INSTANCE;
    }

    private static final NumberJsonDeserializer<Byte> BYTE_INSTANCE = new NumberJsonDeserializer<Byte>() {
        @Override
        public Byte doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            return (byte) reader.nextInt();
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link Byte}
     */
    public static NumberJsonDeserializer<Byte> getByteInstance() {
        return BYTE_INSTANCE;
    }

    private static final NumberJsonDeserializer<Double> DOUBLE_INSTANCE = new NumberJsonDeserializer<Double>() {
        @Override
        public Double doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            return reader.nextDouble();
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link Double}
     */
    public static NumberJsonDeserializer<Double> getDoubleInstance() {
        return DOUBLE_INSTANCE;
    }

    private static final NumberJsonDeserializer<Float> FLOAT_INSTANCE = new NumberJsonDeserializer<Float>() {
        @Override
        public Float doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            return Float.parseFloat( reader.nextString() );
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link Float}
     */
    public static NumberJsonDeserializer<Float> getFloatInstance() {
        return FLOAT_INSTANCE;
    }

    private static final NumberJsonDeserializer<Integer> INTEGER_INSTANCE = new NumberJsonDeserializer<Integer>() {
        @Override
        public Integer doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
                return reader.nextInt();
            } else {
                return Integer.parseInt( reader.nextString() );
            }
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link Integer}
     */
    public static NumberJsonDeserializer<Integer> getIntegerInstance() {
        return INTEGER_INSTANCE;
    }

    private static final NumberJsonDeserializer<Long> LONG_INSTANCE = new NumberJsonDeserializer<Long>() {
        @Override
        public Long doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            return reader.nextLong();
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link Long}
     */
    public static NumberJsonDeserializer<Long> getLongInstance() {
        return LONG_INSTANCE;
    }

    private static final NumberJsonDeserializer<Short> SHORT_INSTANCE = new NumberJsonDeserializer<Short>() {
        @Override
        public Short doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
            if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
                return (short) reader.nextInt();
            } else {
                return Short.parseShort( reader.nextString() );
            }
        }
    };

    /**
     * @return an instance of {@link NumberJsonDeserializer} that deserialize {@link Short}
     */
    public static NumberJsonDeserializer<Short> getShortInstance() {
        return SHORT_INSTANCE;
    }

}
