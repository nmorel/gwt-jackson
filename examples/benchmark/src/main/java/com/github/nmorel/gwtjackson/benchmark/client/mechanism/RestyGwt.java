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

package com.github.nmorel.gwtjackson.benchmark.client.mechanism;

import com.github.nmorel.gwtjackson.benchmark.client.data.DataContainer;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONParser;
import org.fusesource.restygwt.client.JsonEncoderDecoder;

/**
 * @author Nicolas Morel
 */
public class RestyGwt extends Mechanism {

    private static class DataContainerMapperDecorator implements ObjectMapper<DataContainer> {

        private DataContainerMapper mapper;

        @Override
        public DataContainer read( String input ) throws JsonDeserializationException {
            return mapper.decode( JSONParser.parseLenient( input ) );
        }

        @Override
        public DataContainer read( String input, JsonDeserializationContext ctx ) throws JsonDeserializationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String write( DataContainer value ) throws JsonSerializationException {
            return mapper.encode( value ).toString();
        }

        @Override
        public String write( DataContainer value, JsonSerializationContext ctx ) throws JsonSerializationException {
            throw new UnsupportedOperationException();
        }
    }

    public static interface DataContainerMapper extends JsonEncoderDecoder<DataContainer> {}

    private static final DataContainerMapperDecorator decorator = new DataContainerMapperDecorator();

    public RestyGwt() {
        super( "RestyGWT" );
    }

    @Override
    protected ObjectMapper<DataContainer> newMapper() {
        decorator.mapper = GWT.create( DataContainerMapper.class );
        return decorator;
    }
}
