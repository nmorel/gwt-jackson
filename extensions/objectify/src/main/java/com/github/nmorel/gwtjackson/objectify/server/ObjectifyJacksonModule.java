package com.github.nmorel.gwtjackson.objectify.server;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

public class ObjectifyJacksonModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public ObjectifyJacksonModule() {
        super( "Objectify", Version.unknownVersion() );

        // Objectify Key
        addSerializer( Key.class, new KeyStdSerializer() );
        addDeserializer( Key.class, new KeyStdDeserializer() );
        addKeySerializer( Key.class, new KeyJsonSerializer() );
        addKeyDeserializer( Key.class, new KeyStdKeyDeserializer() );

        // Objectify Ref
        addSerializer( Ref.class, new RefStdSerializer() );
        addDeserializer( Ref.class, new RefStdDeserializer() );
        addKeySerializer( Ref.class, new RefJsonSerializer() );
        addKeyDeserializer( Ref.class, new RefStdKeyDeserializer() );

        // Native datastore Key
        addSerializer( com.google.appengine.api.datastore.Key.class, new RawKeyStdSerializer() );
        addDeserializer( com.google.appengine.api.datastore.Key.class, new RawKeyStdDeserializer() );
        addKeySerializer( com.google.appengine.api.datastore.Key.class, new RawKeyJsonSerializer() );
        addKeyDeserializer( com.google.appengine.api.datastore.Key.class, new RawKeyStdKeyDeserializer() );
    }

}
