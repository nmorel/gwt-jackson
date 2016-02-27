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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = AutoValue_Animal.Builder.class)
public abstract class Animal {
    public static abstract class Builder {
        public abstract Animal build();

        @JsonProperty("name")
        public abstract Builder name(final String name);

        @JsonProperty("numberOfLegs")
        public abstract Builder numberOfLegs(int numberOfLegs);
    }

    public static Builder builder() {
        return new AutoValue_Animal.Builder();
    }

    @JsonProperty("name")
    public abstract String name();

    @JsonProperty("numberOfLegs")
    public abstract int numberOfLegs();
}
