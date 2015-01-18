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

package com.github.nmorel.gwtjackson.client.advanced;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

/**
 * @author Nicolas Morel
 */
public class JavaScriptObjectGwtTest extends GwtJacksonTestCase {

    // ====== Simple JsArray
    public interface JsArrayStringMapper extends ObjectMapper<JsArrayString>, ObjectMapperTester<JsArrayString> {

        static JsArrayStringMapper INSTANCE = GWT.create( JsArrayStringMapper.class );
    }

    public void testRootJsArrayString() {
        JsArrayString array = JavaScriptObject.createArray().cast();
        array.push( "Hello" );
        array.push( "World" );
        array.push( "!" );

        String json = JsArrayStringMapper.INSTANCE.write( array );
        assertEquals( "[\"Hello\",\"World\",\"!\"]", json );

        array = JsArrayStringMapper.INSTANCE.read( json );
        assertEquals( 3, array.length() );
        assertEquals( "Hello", array.get( 0 ) );
        assertEquals( "World", array.get( 1 ) );
        assertEquals( "!", array.get( 2 ) );
    }

    // ====== Simple JSO with no annotation

    public static class DefaultPerson extends JavaScriptObject {

        protected DefaultPerson() {}

        public final native String getFirstName() /*-{
            return this.firstName;
        }-*/;

        public final native void setFirstName( String firstName ) /*-{
            this.firstName = firstName;
        }-*/;

        public final native String getLastName() /*-{
            return this.lastName;
        }-*/;

        public final native void setLastName( String lastName ) /*-{
            this.lastName = lastName;
        }-*/;
    }
    public interface DefaultPersonMapper extends ObjectMapper<DefaultPerson>, ObjectMapperTester<DefaultPerson> {

        static DefaultPersonMapper INSTANCE = GWT.create( DefaultPersonMapper.class );
    }

    public void testRootObject() {
        DefaultPerson person = JavaScriptObject.createObject().cast();
        person.setFirstName( "John" );
        person.setLastName( "Doe" );

        String json = DefaultPersonMapper.INSTANCE.write( person );
        assertEquals( "{\"firstName\":\"John\",\"lastName\":\"Doe\"}", json );

        person = DefaultPersonMapper.INSTANCE.read( json );
        assertEquals( "John", person.getFirstName() );
        assertEquals( "Doe", person.getLastName() );
    }

    // ====== Wrapped JSO with no annotation

    public static class DefaultPersonWrapper  {

        public DefaultPerson person;

    }

    public interface DefaultPersonWrapperMapper extends ObjectMapper<DefaultPersonWrapper>, ObjectMapperTester<DefaultPersonWrapper> {

        static DefaultPersonWrapperMapper INSTANCE = GWT.create( DefaultPersonWrapperMapper.class );
    }

    public void testObject() {
        DefaultPersonWrapper wrapper = new DefaultPersonWrapper();
        DefaultPerson person = JavaScriptObject.createObject().cast();
        person.setFirstName( "John" );
        person.setLastName( "Doe" );
        wrapper.person = person;

        String json = DefaultPersonWrapperMapper.INSTANCE.write( wrapper );
        assertEquals( "{\"person\":{\"firstName\":\"John\",\"lastName\":\"Doe\"}}", json );

        wrapper = DefaultPersonWrapperMapper.INSTANCE.read( json );
        assertEquals( "John", wrapper.person.getFirstName() );
        assertEquals( "Doe", wrapper.person.getLastName() );
    }

    // ====== Object with annotations on properties

    public static class PersonWithAnnotation extends JavaScriptObject {

        protected PersonWithAnnotation() {}

        @JsonProperty( "first" )
        public final native String getFirstName() /*-{
            return this.firstName;
        }-*/;

        public final native void setFirstName( String firstName ) /*-{
            this.firstName = firstName;
        }-*/;

        @JsonProperty( "last" )
        public final native String getLastName() /*-{
            return this.lastName;
        }-*/;

        public final native void setLastName( String lastName ) /*-{
            this.lastName = lastName;
        }-*/;
    }
}
