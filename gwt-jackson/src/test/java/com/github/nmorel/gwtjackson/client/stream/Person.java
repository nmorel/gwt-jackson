/*
 * Copyright 2015 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.stream;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Nicolas Morel
 */
public class Person extends JavaScriptObject {

    protected Person() {}

    public final native String getFirstName() /*-{
        return this.firstName;
    }-*/;

    public final native void setFirstName(String firstName) /*-{
        this.firstName = firstName;
    }-*/;

    public final native String getLastName() /*-{
        return this.lastName;
    }-*/;

    public final native void setLastName(String lastName) /*-{
        this.lastName = lastName;
    }-*/;

    public final native String getBio() /*-{
        return this.bio;
    }-*/;

    public final native void setBio(String bio) /*-{
        this.bio = bio;
    }-*/;
}
