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
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.googlecode.objectify.Ref;

public class RefJsonSerializer extends JsonSerializer<Ref> {

    private final RefStdSerializer refStdSerializer = new RefStdSerializer();

    @Override
    public void serialize( Ref value, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jgen.getCodec().getFactory().createGenerator( writer );
        refStdSerializer.serialize( value, jsonGenerator, provider );
        jsonGenerator.close();
        jgen.writeFieldName( writer.toString() );
    }
}
