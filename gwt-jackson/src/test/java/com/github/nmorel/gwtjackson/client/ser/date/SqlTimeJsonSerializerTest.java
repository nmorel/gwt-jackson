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

package com.github.nmorel.gwtjackson.client.ser.date;

import java.sql.Time;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.BaseDateJsonSerializer.SqlTimeJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class SqlTimeJsonSerializerTest extends AbstractJsonSerializerTest<Time> {

    @Override
    protected SqlTimeJsonSerializer createSerializer() {
        return SqlTimeJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        Time time = new Time( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        assertSerialization( "\"" + time.toString() + "\"", time );
    }
}
