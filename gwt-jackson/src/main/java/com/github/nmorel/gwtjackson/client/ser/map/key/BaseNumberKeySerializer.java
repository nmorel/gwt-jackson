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

package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Base implementation of {@link KeySerializer} for {@link Number}s.
 *
 * @author Nicolas Morel
 */
public abstract class BaseNumberKeySerializer<N extends Number> extends KeySerializer<N> {

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link BigDecimal}
     */
    public static final class BigDecimalKeySerializer extends BaseNumberKeySerializer<BigDecimal> {

        private static final BigDecimalKeySerializer INSTANCE = new BigDecimalKeySerializer();

        /**
         * @return an instance of {@link BigDecimalKeySerializer}
         */
        public static BigDecimalKeySerializer getInstance() {
            return INSTANCE;
        }

        private BigDecimalKeySerializer() { }

    }

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link BigInteger}
     */
    public static final class BigIntegerKeySerializer extends BaseNumberKeySerializer<BigInteger> {

        private static final BigIntegerKeySerializer INSTANCE = new BigIntegerKeySerializer();

        /**
         * @return an instance of {@link BigIntegerKeySerializer}
         */
        public static BigIntegerKeySerializer getInstance() {
            return INSTANCE;
        }

        private BigIntegerKeySerializer() { }

    }

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link Byte}
     */
    public static final class ByteKeySerializer extends BaseNumberKeySerializer<Byte> {

        private static final ByteKeySerializer INSTANCE = new ByteKeySerializer();

        /**
         * @return an instance of {@link ByteKeySerializer}
         */
        public static ByteKeySerializer getInstance() {
            return INSTANCE;
        }

        private ByteKeySerializer() { }

    }

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link Double}
     */
    public static final class DoubleKeySerializer extends BaseNumberKeySerializer<Double> {

        private static final DoubleKeySerializer INSTANCE = new DoubleKeySerializer();

        /**
         * @return an instance of {@link DoubleKeySerializer}
         */
        public static DoubleKeySerializer getInstance() {
            return INSTANCE;
        }

        private DoubleKeySerializer() { }

    }

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link Integer}
     */
    public static final class IntegerKeySerializer extends BaseNumberKeySerializer<Integer> {

        private static final IntegerKeySerializer INSTANCE = new IntegerKeySerializer();

        /**
         * @return an instance of {@link IntegerKeySerializer}
         */
        public static IntegerKeySerializer getInstance() {
            return INSTANCE;
        }

        private IntegerKeySerializer() { }

    }

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link Float}
     */
    public static final class FloatKeySerializer extends BaseNumberKeySerializer<Float> {

        private static final FloatKeySerializer INSTANCE = new FloatKeySerializer();

        /**
         * @return an instance of {@link FloatKeySerializer}
         */
        public static FloatKeySerializer getInstance() {
            return INSTANCE;
        }

        private FloatKeySerializer() { }

    }

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link Long}
     */
    public static final class LongKeySerializer extends BaseNumberKeySerializer<Long> {

        private static final LongKeySerializer INSTANCE = new LongKeySerializer();

        /**
         * @return an instance of {@link LongKeySerializer}
         */
        public static LongKeySerializer getInstance() {
            return INSTANCE;
        }

        private LongKeySerializer() { }

    }

    /**
     * Default implementation of {@link BaseNumberKeySerializer} for {@link Short}
     */
    public static final class ShortKeySerializer extends BaseNumberKeySerializer<Short> {

        private static final ShortKeySerializer INSTANCE = new ShortKeySerializer();

        /**
         * @return an instance of {@link ShortKeySerializer}
         */
        public static ShortKeySerializer getInstance() {
            return INSTANCE;
        }

        private ShortKeySerializer() { }

    }

    @Override
    protected String doSerialize( @Nonnull N value, JsonSerializationContext ctx ) {
        return value.toString();
    }
}
