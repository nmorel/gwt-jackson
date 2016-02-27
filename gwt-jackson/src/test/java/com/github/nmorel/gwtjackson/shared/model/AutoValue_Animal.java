/*
 * Copyright 2016 Nicolas Morel
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

package com.github.nmorel.gwtjackson.shared.model;

final class AutoValue_Animal extends Animal {

    private final String name;

    private final int numberOfLegs;

    private AutoValue_Animal(
            String name,
            int numberOfLegs ) {
        if ( name == null ) {
            throw new NullPointerException( "Null name" );
        }
        this.name = name;
        this.numberOfLegs = numberOfLegs;
    }

    @com.fasterxml.jackson.annotation.JsonProperty( value = "name" )
    @Override
    public String name() {
        return name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty( value = "numberOfLegs" )
    @Override
    public int numberOfLegs() {
        return numberOfLegs;
    }

    @Override
    public String toString() {
        return "Animal{"
                + "name=" + name + ", "
                + "numberOfLegs=" + numberOfLegs
                + "}";
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this ) {
            return true;
        }
        if ( o instanceof Animal ) {
            Animal that = (Animal) o;
            return (this.name.equals( that.name() ))
                    && (this.numberOfLegs == that.numberOfLegs());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= name.hashCode();
        h *= 1000003;
        h ^= numberOfLegs;
        return h;
    }

    static final class Builder extends Animal.Builder {

        private String name;

        private Integer numberOfLegs;

        Builder() {
        }

        Builder( Animal source ) {
            this.name = source.name();
            this.numberOfLegs = source.numberOfLegs();
        }

        @Override
        public Animal.Builder name( String name ) {
            this.name = name;
            return this;
        }

        @Override
        public Animal.Builder numberOfLegs( int numberOfLegs ) {
            this.numberOfLegs = numberOfLegs;
            return this;
        }

        @Override
        public Animal build() {
            String missing = "";
            if ( name == null ) {
                missing += " name";
            }
            if ( numberOfLegs == null ) {
                missing += " numberOfLegs";
            }
            if ( !missing.isEmpty() ) {
                throw new IllegalStateException( "Missing required properties:" + missing );
            }
            return new AutoValue_Animal(
                    this.name,
                    this.numberOfLegs );
        }
    }
}
