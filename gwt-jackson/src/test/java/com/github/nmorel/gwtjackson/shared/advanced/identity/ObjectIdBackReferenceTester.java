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

package com.github.nmorel.gwtjackson.shared.advanced.identity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * @author Nicolas Morel
 */
public final class ObjectIdBackReferenceTester extends AbstractTester {

@JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id" )
public static class Owner {

    @JsonProperty( "children" )
    private List<Child> children;

    Owner() {
        this.children = new ArrayList<Child>();
    }

    Owner( List<Child> children ) {
        this.children = children;
    }

    public List<Child> getChildren() {
        return children;
    }

    public Child addChild( String name ) {
        Child child = new Child( name );
        child.setOwner( this );
        children.add( child );
        return child;
    }
}

    public static class Child {

        private Owner owner;

        private String name;

        @JsonCreator
        public Child( @JsonProperty( "name" ) String name ) {
            this( null, name );
        }

        public Child( Owner owner, String name ) {
            this.owner = owner;
            this.name = name;
        }

        public Owner getOwner() {
            return owner;
        }

        @JsonProperty( "owner" )
        @JsonIdentityReference
        void setOwner( Owner owner ) {
            this.owner = owner;
        }

        @JsonProperty( "name" )
        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }
    }

    public static final ObjectIdBackReferenceTester INSTANCE = new ObjectIdBackReferenceTester();

    private ObjectIdBackReferenceTester() {
    }

    public void testMapper( ObjectMapperTester<Owner> mapper ) {
        Owner origOwner = new Owner();
        Child origChild = origOwner.addChild( "item1" );
        String json = mapper.write( origOwner );
        Owner jsonOwner = mapper.read( json );
        Child jsonChild = jsonOwner.getChildren().get( 0 );
        assertEquals( "item1", jsonChild.getName() );
    }
}
