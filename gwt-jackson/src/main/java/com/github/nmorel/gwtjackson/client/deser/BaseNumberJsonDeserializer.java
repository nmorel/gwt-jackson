/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.deser;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link JsonDeserializer} for {@link Number}.
 *
 * @author Nicolas Morel
 */
public abstract class BaseNumberJsonDeserializer<N extends Number> extends JsonDeserializer<N> {

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link BigDecimal}
     */
    public static final class BigDecimalJsonDeserializer extends BaseNumberJsonDeserializer<BigDecimal> {

        private static final BigDecimalJsonDeserializer INSTANCE = new BigDecimalJsonDeserializer();

        /**
         * @return an instance of {@link BigDecimalJsonDeserializer}
         */
        public static BigDecimalJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private BigDecimalJsonDeserializer() { }

        @Override
        protected BigDecimal doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return new BigDecimal( reader.nextString() );
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link BigInteger}
     */
    public static final class BigIntegerJsonDeserializer extends BaseNumberJsonDeserializer<BigInteger> {

        private static final BigIntegerJsonDeserializer INSTANCE = new BigIntegerJsonDeserializer();

        /**
         * @return an instance of {@link BigIntegerJsonDeserializer}
         */
        public static BigIntegerJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private BigIntegerJsonDeserializer() { }

        @Override
        protected BigInteger doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return new BigInteger( reader.nextString() );
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link Byte}
     */
    public static final class ByteJsonDeserializer extends BaseNumberJsonDeserializer<Byte> {

        private static final ByteJsonDeserializer INSTANCE = new ByteJsonDeserializer();

        /**
         * @return an instance of {@link ByteJsonDeserializer}
         */
        public static ByteJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private ByteJsonDeserializer() { }

        @Override
        protected Byte doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return (byte) reader.nextInt();
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link Double}
     */
    public static final class DoubleJsonDeserializer extends BaseNumberJsonDeserializer<Double> {

        private static final DoubleJsonDeserializer INSTANCE = new DoubleJsonDeserializer();

        /**
         * @return an instance of {@link DoubleJsonDeserializer}
         */
        public static DoubleJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private DoubleJsonDeserializer() { }

        @Override
        protected Double doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return reader.nextDouble();
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link Float}
     */
    public static final class FloatJsonDeserializer extends BaseNumberJsonDeserializer<Float> {

        private static final FloatJsonDeserializer INSTANCE = new FloatJsonDeserializer();

        /**
         * @return an instance of {@link FloatJsonDeserializer}
         */
        public static FloatJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private FloatJsonDeserializer() { }

        @Override
        protected Float doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return Float.parseFloat( reader.nextString() );
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link Integer}
     */
    public static final class IntegerJsonDeserializer extends BaseNumberJsonDeserializer<Integer> {

        private static final IntegerJsonDeserializer INSTANCE = new IntegerJsonDeserializer();

        /**
         * @return an instance of {@link IntegerJsonDeserializer}
         */
        public static IntegerJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private IntegerJsonDeserializer() { }

        @Override
        protected Integer doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
                return reader.nextInt();
            } else {
                return Integer.parseInt( reader.nextString() );
            }
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link Long}
     */
    public static final class LongJsonDeserializer extends BaseNumberJsonDeserializer<Long> {

        private static final LongJsonDeserializer INSTANCE = new LongJsonDeserializer();

        /**
         * @return an instance of {@link LongJsonDeserializer}
         */
        public static LongJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private LongJsonDeserializer() { }

        @Override
        protected Long doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return reader.nextLong();
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link Short}
     */
    public static final class ShortJsonDeserializer extends BaseNumberJsonDeserializer<Short> {

        private static final ShortJsonDeserializer INSTANCE = new ShortJsonDeserializer();

        /**
         * @return an instance of {@link ShortJsonDeserializer}
         */
        public static ShortJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private ShortJsonDeserializer() { }

        @Override
        protected Short doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
                return (short) reader.nextInt();
            } else {
                return Short.parseShort( reader.nextString() );
            }
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonDeserializer} for {@link Number}
     */
    public static final class NumberJsonDeserializer extends BaseNumberJsonDeserializer<Number> {

        private static final NumberJsonDeserializer INSTANCE = new NumberJsonDeserializer();

        /**
         * @return an instance of {@link NumberJsonDeserializer}
         */
        public static NumberJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private NumberJsonDeserializer() { }

        @Override
        public Number doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return reader.nextNumber();
        }
    }

}
