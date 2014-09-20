/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.deser.bean;

import java.io.Serializable;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.NumberJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BooleanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.ArrayListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.LinkedHashMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.StringKeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractSerializableBeanJsonDeserializer extends AbstractBeanJsonDeserializer<Serializable> {

    private ArrayListJsonDeserializer<Serializable> listJsonDeserializer;

    private LinkedHashMapJsonDeserializer<String, Serializable> mapJsonDeserializer;

    @Override
    protected boolean canDeserialize() {
        return true;
    }

    @Override
    public Serializable deserializeWrapped( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                            IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo,
                                            String typeInformation ) {
        switch ( reader.peek() ) {
            case NUMBER:
                return NumberJsonDeserializer.getInstance().doDeserialize( reader, ctx, params );
            case STRING:
                return StringJsonDeserializer.getInstance().doDeserialize( reader, ctx, params );
            case BOOLEAN:
                return BooleanJsonDeserializer.getInstance().doDeserialize( reader, ctx, params );
            case BEGIN_ARRAY:
                if ( null == listJsonDeserializer ) {
                    listJsonDeserializer = ArrayListJsonDeserializer.newInstance( this );
                }
                return listJsonDeserializer.doDeserialize( reader, ctx, params );
            case BEGIN_OBJECT:
                if ( null == mapJsonDeserializer ) {
                    mapJsonDeserializer = LinkedHashMapJsonDeserializer.newInstance( StringKeyDeserializer.getInstance(), this );
                }
                return mapJsonDeserializer.doDeserialize( reader, ctx, params );
            case NULL:
                reader.nextNull();
                return null;
            default:
                throw ctx.traceError( "Unexpected token " + reader.peek() + " for java.io.Serializable deserialization", reader );
        }
    }

}
