/*
 * Copyright 2013 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.deser.bean;

import java.util.Map;

/**
 * @author Nicolas Morel
 */
public class Instance<T> {

    private final T instance;

    private final Map<String, String> bufferedProperties;

    public Instance( T instance, Map<String, String> bufferedProperties ) {
        this.instance = instance;
        this.bufferedProperties = bufferedProperties;
    }

    public T getInstance() {
        return instance;
    }

    public Map<String, String> getBufferedProperties() {
        return bufferedProperties;
    }
}
