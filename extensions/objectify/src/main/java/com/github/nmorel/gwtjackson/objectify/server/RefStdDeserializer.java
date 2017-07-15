package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.nmorel.gwtjackson.objectify.shared.RefConstant;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.DeadRef;

public class RefStdDeserializer extends StdDeserializer<Ref<?>> implements ContextualDeserializer {

    private final JavaType valueType;

    public RefStdDeserializer() {
        this( null );
    }

    public RefStdDeserializer( JavaType valueType ) {
        super( Ref.class );
        this.valueType = valueType;
    }

    @Override
    public JsonDeserializer<?> createContextual( DeserializationContext ctxt, BeanProperty property ) throws JsonMappingException {
        if ( ctxt.getContextualType() == null || ctxt.getContextualType().containedType( 0 ) == null ) {
            throw JsonMappingException.from( ctxt, "Cannot deserialize Ref<T>. Cannot find the Generic Type T." );
        }
        return new RefStdDeserializer( ctxt.getContextualType().containedType( 0 ) );
    }

    @Override
    public Ref<?> deserialize( JsonParser jp, DeserializationContext ctxt ) throws IOException {
        if ( valueType == null ) {
            throw JsonMappingException.from( ctxt, "valueType can't be null." );
        }

        JsonNode jsonNode = jp.readValueAsTree();

        JsonParser keyJsonParser = jsonNode.get( RefConstant.KEY ).traverse();
        keyJsonParser.setCodec( jp.getCodec() );
        Key key = keyJsonParser.readValueAs( Key.class );

        JsonParser valueJsonParser = jsonNode.get( RefConstant.VALUE ).traverse();
        valueJsonParser.setCodec( jp.getCodec() );
        Object value = valueJsonParser.readValueAs( valueType.getRawClass() );

        return value != null ? new DeadRef( key, value ) : new DeadRef( key );
    }
}
