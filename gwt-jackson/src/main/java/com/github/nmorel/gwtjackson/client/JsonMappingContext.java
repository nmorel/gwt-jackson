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

package com.github.nmorel.gwtjackson.client;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.logging.Logger;

import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.EnumSetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.EnumMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.EnumKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.RawValueJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.ArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.EnumKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;

/**
 * Base class for serialization and deserialization context
 *
 * @author Nicolas Morel
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class JsonMappingContext {

    public abstract Logger getLogger();

    /*##############################*/
    /*#####    Deserializers   #####*/
    /*##############################*/

    public <T> JsonDeserializer<T[]> newArrayJsonDeserializer( JsonDeserializer<T> deserializer,
                                                               ArrayJsonDeserializer.ArrayCreator<T> arrayCreator ) {
        return ArrayJsonDeserializer.newInstance( deserializer, arrayCreator );
    }

    public <T extends Enum<T>> JsonDeserializer<EnumSet<T>> newEnumSetJsonDeserializer( Class<T> enumClass,
                                                                                        JsonDeserializer<T> deserializer ) {
        return EnumSetJsonDeserializer.newInstance( enumClass, deserializer );
    }

    public <T extends Enum<T>> JsonDeserializer<T> newEnumJsonDeserializer( Class<T> enumClass ) {
        return EnumJsonDeserializer.newInstance( enumClass );
    }

    public <E extends Enum<E>, V> JsonDeserializer<EnumMap<E, V>> newEnumMapJsonDeserializer( Class<E> enumClass,
                                                                                              KeyDeserializer<E> keyDeserializer,
                                                                                              JsonDeserializer<V> valueDeserializer ) {
        return EnumMapJsonDeserializer.newInstance( enumClass, keyDeserializer, valueDeserializer );
    }

    /*##############################*/
    /*####   Key deserializers  ####*/
    /*##############################*/

    public <T extends Enum<T>> KeyDeserializer<T> newEnumKeyDeserializer( Class<T> enumClass ) {
        return EnumKeyDeserializer.newInstance( enumClass );
    }

    /*##############################*/
    /*#######   Serializers  #######*/
    /*##############################*/

    public <T> JsonSerializer<T> getRawValueJsonSerializer() {
        return RawValueJsonSerializer.getInstance();
    }

    public <E extends Enum<E>> JsonSerializer<E> getEnumJsonSerializer() {
        return EnumJsonSerializer.getInstance();
    }

    public <T> JsonSerializer<T[]> newArrayJsonSerializer( JsonSerializer<T> serializer ) {
        return ArrayJsonSerializer.newInstance( serializer );
    }

    /*##############################*/
    /*#####   Key serializers  #####*/
    /*##############################*/

    public <T extends Enum<T>> KeySerializer<T> getEnumKeySerializer() {
        return EnumKeySerializer.getInstance();
    }
}
