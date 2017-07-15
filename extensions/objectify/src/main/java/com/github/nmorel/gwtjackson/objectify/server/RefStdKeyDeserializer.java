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

package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
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
