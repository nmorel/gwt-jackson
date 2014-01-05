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

package com.github.nmorel.gwtjackson.benchmark.client;

import java.util.List;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;

/**
 * @author Nicolas Morel
 */
public class GwtJackson extends Mechanism {

    public static interface PersonMapper extends ObjectMapper<List<Person>> {}

    protected GwtJackson() {
        super( new ResultWidget( "gwt-jackson" ) );
    }

    @Override
    public void test( final List<Person> datas, int nbIterations ) {
        final PersonMapper mapper = GWT.create( PersonMapper.class );
        final String jsonInput = mapper.write( datas );
        mapper.read( jsonInput );

        Operation serCommand = new Operation( nbIterations, getResult().getResultSer() ) {

            @Override
            protected void doExecute() {
                mapper.write( datas );
            }
        };

        Operation deserCommand = new Operation( nbIterations, getResult().getResultDeser() ) {

            @Override
            protected void doExecute() {
                mapper.read( jsonInput );
            }
        };

        Scheduler.get().scheduleIncremental( serCommand );
        Scheduler.get().scheduleIncremental( deserCommand );
    }
}
