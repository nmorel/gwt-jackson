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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author Nicolas Morel
 */
public abstract class Operation<T> implements RepeatingCommand {

    private final int nbIterations;

    private int count = 0;

    private long totalTime = 0l;

    private T result;

    public Operation( int nbIterations ) {
        this.nbIterations = nbIterations;
    }

    @Override
    public boolean execute() {
        long startTime = System.currentTimeMillis();
        result = doExecute();
        totalTime += System.currentTimeMillis() - startTime;
        if ( ++count == nbIterations ) {
            Scheduler.get().scheduleDeferred( new ScheduledCommand() {
                @Override
                public void execute() {
                    onEnd();
                }
            } );
            return false;
        } else {
            return true;
        }
    }

    protected abstract T doExecute();

    protected abstract void onEnd();

    public long getTotalTime() {
        return totalTime;
    }

    public T getResult() {
        return result;
    }
}
