/*
 * Copyright 2017 Nicolas Morel
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

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.EnumWithAnnotationDeserializerTest.Direction;

public class EnumWithAnnotationDeserializerTest extends AbstractJsonDeserializerTest<Direction> {

    public enum Direction {
        UP(1),
        DOWN(2),
        LEFT(3),
        RIGHT(4);

        private int value;
        private static Map<Integer, Direction> enumHash = new HashMap<Integer, Direction>();

        Direction(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return value;
        }

        @JsonCreator
        public static Direction fromValue(int val) {
            if (enumHash.isEmpty()) {
                for (Direction direction : Direction.values()) {
                    enumHash.put(direction.getValue(), direction);
                }
            }
            return enumHash.get(val);
        }
    }

    @Override
    protected JsonDeserializer<Direction> createDeserializer() {
        return EnumJsonDeserializer.newInstance( Direction.class );
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( Direction.UP, "1" );
        assertDeserialization( Direction.DOWN, "2" );
        assertDeserialization( Direction.LEFT, "3" );
        assertDeserialization( Direction.RIGHT, "4" );
        try {
            assertDeserialization( null, "0" );
            fail( "IllegalArgumentException should be thrown!" );
        } catch ( IllegalArgumentException ex ) {
        }
        assertDeserialization( JsonDeserializationContext.builder().readUnknownEnumValuesAsNull( true ).build(), null, "0" );
    }


}
