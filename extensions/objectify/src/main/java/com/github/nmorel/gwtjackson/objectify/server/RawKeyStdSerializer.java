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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyConstant;
import com.google.appengine.api.datastore.Key;

public class RawKeyStdSerializer extends StdSerializer<Key> {

    public RawKeyStdSerializer() {
        super( Key.class );
    }

    @Override
    public void serialize( Key value, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        jgen.writeStartObject();
        boolean includeNull = Include.NON_NULL != provider.getConfig().getDefaultPropertyInclusion().getValueInclusion();
        if ( value.getParent() != null || includeNull ) {
            jgen.writeObjectField( RawKeyConstant.PARENT, value.getParent() );
        }
        if ( value.getKind() != null || includeNull ) {
            jgen.writeStringField( RawKeyConstant.KIND, value.getKind() );
        }
        jgen.writeNumberField( RawKeyConstant.ID, value.getId() );
        if ( value.getName() != null || includeNull ) {
            jgen.writeStringField( RawKeyConstant.NAME, value.getName() );
        }
        jgen.writeEndObject();
    }

}
