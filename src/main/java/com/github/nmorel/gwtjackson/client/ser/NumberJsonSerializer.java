package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonSerializer} for {@link Number}.
 *
 * @author Nicolas Morel
 */
public abstract class NumberJsonSerializer<N extends Number> extends JsonSerializer<N> {

    private static final NumberJsonSerializer<BigDecimal> BIG_DECIMAL_INSTANCE = new NumberJsonSerializer<BigDecimal>() {};

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link BigDecimal}
     */
    public static NumberJsonSerializer<BigDecimal> getBigDecimalInstance() {
        return BIG_DECIMAL_INSTANCE;
    }

    private static final NumberJsonSerializer<BigInteger> BIG_INTEGER_INSTANCE = new NumberJsonSerializer<BigInteger>() {};

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link BigInteger}
     */
    public static NumberJsonSerializer<BigInteger> getBigIntegerInstance() {
        return BIG_INTEGER_INSTANCE;
    }

    private static final NumberJsonSerializer<Byte> BYTE_INSTANCE = new NumberJsonSerializer<Byte>() {};

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link Byte}
     */
    public static NumberJsonSerializer<Byte> getByteInstance() {
        return BYTE_INSTANCE;
    }

    private static final NumberJsonSerializer<Double> DOUBLE_INSTANCE = new NumberJsonSerializer<Double>() {
        @Override
        public void doEncode( JsonWriter writer, @Nonnull Double value, JsonEncodingContext ctx ) throws IOException {
            // writer has a special method to write double, let's use instead of default Number method.
            writer.value( (double) value );
        }
    };

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link Double}
     */
    public static NumberJsonSerializer<Double> getDoubleInstance() {
        return DOUBLE_INSTANCE;
    }

    private static final NumberJsonSerializer<Float> FLOAT_INSTANCE = new NumberJsonSerializer<Float>() {};

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link Float}
     */
    public static NumberJsonSerializer<Float> getFloatInstance() {
        return FLOAT_INSTANCE;
    }

    private static final NumberJsonSerializer<Integer> INTEGER_INSTANCE = new NumberJsonSerializer<Integer>() {};

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link Integer}
     */
    public static NumberJsonSerializer<Integer> getIntegerInstance() {
        return INTEGER_INSTANCE;
    }

    private static final NumberJsonSerializer<Long> LONG_INSTANCE = new NumberJsonSerializer<Long>() {

        @Override
        public void doEncode( JsonWriter writer, @Nonnull Long value, JsonEncodingContext ctx ) throws IOException {
            // writer has a special method to write long, let's use instead of default Number method.
            writer.value( (long) value );
        }
    };

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link Long}
     */
    public static NumberJsonSerializer<Long> getLongInstance() {
        return LONG_INSTANCE;
    }

    private static final NumberJsonSerializer<Short> SHORT_INSTANCE = new NumberJsonSerializer<Short>() {};

    /**
     * @return an instance of {@link NumberJsonSerializer} that serialize {@link Short}
     */
    public static NumberJsonSerializer<Short> getShortInstance() {
        return SHORT_INSTANCE;
    }

    @Override
    public void doEncode( JsonWriter writer, @Nonnull N value, JsonEncodingContext ctx ) throws IOException {
        writer.value( value );
    }
}
