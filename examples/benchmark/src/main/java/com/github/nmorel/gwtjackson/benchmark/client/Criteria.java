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

/**
 * @author Nicolas Morel
 */
public class Criteria {

    private int nbItems;

    private int nbIterations;

    private boolean singletonMapper;

    private boolean serialization;

    private boolean deserialization;

    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems( int nbItems ) {
        this.nbItems = nbItems;
    }

    public int getNbIterations() {
        return nbIterations;
    }

    public void setNbIterations( int nbIterations ) {
        this.nbIterations = nbIterations;
    }

    public boolean isSingletonMapper() {
        return singletonMapper;
    }

    public void setSingletonMapper( boolean singletonMapper ) {
        this.singletonMapper = singletonMapper;
    }

    public boolean isSerialization() {
        return serialization;
    }

    public void setSerialization( boolean serialization ) {
        this.serialization = serialization;
    }

    public boolean isDeserialization() {
        return deserialization;
    }

    public void setDeserialization( boolean deserialization ) {
        this.deserialization = deserialization;
    }
}
