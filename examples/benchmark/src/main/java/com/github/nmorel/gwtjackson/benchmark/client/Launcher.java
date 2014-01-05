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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author Nicolas Morel
 */
public class Launcher {

    private List<Operation> operations;

    public Launcher( List<Operation> operations ) {
        this.operations = operations;
    }

    public void launch() {
        if ( operations.isEmpty() ) {
            return;
        }

        final Operation operation = operations.remove( 0 );
        Scheduler.get().scheduleIncremental( new RepeatingCommand() {
            @Override
            public boolean execute() {
                boolean res = operation.execute();
                if ( !res ) {
                    Scheduler.get().scheduleDeferred( new ScheduledCommand() {
                        @Override
                        public void execute() {
                            launch();
                        }
                    } );
                }
                return res;
            }
        } );
    }
}
