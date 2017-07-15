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
        key( Key.class ).serializer( KeyKeySerializer.class )
                .deserializer( KeyKeyDeserializer.class );
    }
}
