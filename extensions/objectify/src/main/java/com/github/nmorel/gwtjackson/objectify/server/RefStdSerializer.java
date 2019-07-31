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
import com.github.nmorel.gwtjackson.objectify.shared.RefConstant;
import com.googlecode.objectify.Ref;

public class RefStdSerializer extends StdSerializer<Ref> {

    public RefStdSerializer() {
        super( Ref.class );
    }

    @Override
    public void serialize( Ref ref, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        jgen.writeStartObject();
        boolean includeNull = Include.NON_NULL != provider.getConfig().getDefaultPropertyInclusion().getValueInclusion();
        if ( ref.key() != null || includeNull ) {
            jgen.writeObjectField( RefConstant.KEY, ref.key() );
        }
        if ( ref.getValue() != null || includeNull ) {
            jgen.writeObjectField( RefConstant.VALUE, ref.getValue() );
        }
        jgen.writeEndObject();
    }

}
