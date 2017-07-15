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

package com.github.nmorel.gwtjackson.objectify.rebind;

import com.github.nmorel.gwtjackson.client.AbstractConfiguration;
import com.github.nmorel.gwtjackson.objectify.client.KeyJsonDeserializer;
import com.github.nmorel.gwtjackson.objectify.client.KeyJsonSerializer;
import com.github.nmorel.gwtjackson.objectify.client.KeyKeyDeserializer;
import com.github.nmorel.gwtjackson.objectify.client.KeyKeySerializer;
import com.github.nmorel.gwtjackson.objectify.client.RawKeyJsonDeserializer;
import com.github.nmorel.gwtjackson.objectify.client.RawKeyJsonSerializer;
import com.github.nmorel.gwtjackson.objectify.client.RawKeyKeyDeserializer;
import com.github.nmorel.gwtjackson.objectify.client.RawKeyKeySerializer;
import com.github.nmorel.gwtjackson.objectify.client.RefJsonDeserializer;
import com.github.nmorel.gwtjackson.objectify.client.RefJsonSerializer;
import com.github.nmorel.gwtjackson.objectify.client.RefKeyDeserializer;
import com.github.nmorel.gwtjackson.objectify.client.RefKeySerializer;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

public class ObjectifyConfiguration extends AbstractConfiguration {

    @Override
    protected void configure() {
        type( com.google.appengine.api.datastore.Key.class ).serializer( RawKeyJsonSerializer.class )
                .deserializer( RawKeyJsonDeserializer.class );
        type( Key.class ).serializer( KeyJsonSerializer.class ).deserializer( KeyJsonDeserializer.class );
        type( Ref.class ).serializer( RefJsonSerializer.class ).deserializer( RefJsonDeserializer.class );

        key( com.google.appengine.api.datastore.Key.class ).serializer( RawKeyKeySerializer.class )
                .deserializer( RawKeyKeyDeserializer.class );
        key( Key.class ).serializer( KeyKeySerializer.class ).deserializer( KeyKeyDeserializer.class );
        key( Ref.class ).serializer( RefKeySerializer.class ).deserializer( RefKeyDeserializer.class );
    }
}
