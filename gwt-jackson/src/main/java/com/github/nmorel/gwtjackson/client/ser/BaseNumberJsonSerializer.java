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

package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonSerializer} for {@link Number}.
 *
 * @author Nicolas Morel
 */
public abstract class BaseNumberJsonSerializer<N extends Number> extends JsonSerializer<N> {

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link BigDecimal}
     */
    public static final class BigDecimalJsonSerializer extends BaseNumberJsonSerializer<BigDecimal> {

        private static final BigDecimalJsonSerializer INSTANCE = new BigDecimalJsonSerializer();

        /**
         * @return an instance of {@link BigDecimalJsonSerializer}
         */
        public static BigDecimalJsonSerializer getInstance() {
            return INSTANCE;
        }

        private BigDecimalJsonSerializer() { }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link BigInteger}
     */
    public static final class BigIntegerJsonSerializer extends BaseNumberJsonSerializer<BigInteger> {

        private static final BigIntegerJsonSerializer INSTANCE = new BigIntegerJsonSerializer();

        /**
         * @return an instance of {@link BigIntegerJsonSerializer}
         */
        public static BigIntegerJsonSerializer getInstance() {
            return INSTANCE;
        }

        private BigIntegerJsonSerializer() { }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Byte}
     */
    public static final class ByteJsonSerializer extends BaseNumberJsonSerializer<Byte> {

        private static final ByteJsonSerializer INSTANCE = new ByteJsonSerializer();

        /**
         * @return an instance of {@link ByteJsonSerializer}
         */
        public static ByteJsonSerializer getInstance() {
            return INSTANCE;
        }

        private ByteJsonSerializer() { }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Double}
     */
    public static final class DoubleJsonSerializer extends BaseNumberJsonSerializer<Double> {

        private static final DoubleJsonSerializer INSTANCE = new DoubleJsonSerializer();

        /**
         * @return an instance of {@link DoubleJsonSerializer}
         */
        public static DoubleJsonSerializer getInstance() {
            return INSTANCE;
        }

        private DoubleJsonSerializer() { }

        @Override
        public void doSerialize( JsonWriter writer, @Nonnull Double value, JsonSerializationContext ctx ) throws IOException {
            // writer has a special method to write double, let's use instead of default Number method.
            writer.value( value.doubleValue() );
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Float}
     */
    public static final class FloatJsonSerializer extends BaseNumberJsonSerializer<Float> {

        private static final FloatJsonSerializer INSTANCE = new FloatJsonSerializer();

        /**
         * @return an instance of {@link FloatJsonSerializer}
         */
        public static FloatJsonSerializer getInstance() {
            return INSTANCE;
        }

        private FloatJsonSerializer() { }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Integer}
     */
    public static final class IntegerJsonSerializer extends BaseNumberJsonSerializer<Integer> {

        private static final IntegerJsonSerializer INSTANCE = new IntegerJsonSerializer();

        /**
         * @return an instance of {@link IntegerJsonSerializer}
         */
        public static IntegerJsonSerializer getInstance() {
            return INSTANCE;
        }

        private IntegerJsonSerializer() { }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Long}
     */
    public static final class LongJsonSerializer extends BaseNumberJsonSerializer<Long> {

        private static final LongJsonSerializer INSTANCE = new LongJsonSerializer();

        /**
         * @return an instance of {@link LongJsonSerializer}
         */
        public static LongJsonSerializer getInstance() {
            return INSTANCE;
        }

        private LongJsonSerializer() { }

        @Override
        public void doSerialize( JsonWriter writer, @Nonnull Long value, JsonSerializationContext ctx ) throws IOException {
            // writer has a special method to write long, let's use instead of default Number method.
            writer.value( value.longValue() );
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Short}
     */
    public static final class ShortJsonSerializer extends BaseNumberJsonSerializer<Short> {

        private static final ShortJsonSerializer INSTANCE = new ShortJsonSerializer();

        /**
         * @return an instance of {@link ShortJsonSerializer}
         */
        public static ShortJsonSerializer getInstance() {
            return INSTANCE;
        }

        private ShortJsonSerializer() { }
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull N value, JsonSerializationContext ctx ) throws IOException {
        writer.value( value );
    }
}
