package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.googlecode.objectify.Ref;

public class RefStdKeyDeserializer extends StdKeyDeserializer implements ContextualKeyDeserializer {

    private final JavaType valueType;

    public RefStdKeyDeserializer() {
        this( null );
    }

    public RefStdKeyDeserializer( JavaType valueType ) {
        super( StdKeyDeserializer.TYPE_CLASS, Ref.class );
        this.valueType = valueType;
    }

    @Override
    public Object deserializeKey( String key, DeserializationContext ctxt ) throws IOException {
        JsonParser jsonParser = ctxt.getParser().getCodec().getFactory().createParser( key );
        return new RefStdDeserializer( valueType ).deserialize( jsonParser, ctxt );
    }

    @Override
    public KeyDeserializer createContextual( DeserializationContext ctxt, BeanProperty property ) throws JsonMappingException {
        if ( ctxt.getContextualType() == null || ctxt.getContextualType().containedType( 0 ) == null || ctxt.getContextualType().containedType( 0 ).containedType( 0 ) == null ) {
            throw JsonMappingException.from( ctxt, "Cannot deserialize Ref<T>. Cannot find the Generic Type T." );
        }
        return new RefStdKeyDeserializer( ctxt.getContextualType().containedType( 0 ).containedType( 0 ) );
    }
}
