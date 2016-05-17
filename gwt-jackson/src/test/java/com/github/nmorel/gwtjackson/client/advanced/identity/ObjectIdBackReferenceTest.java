package com.github.nmorel.gwtjackson.client.advanced.identity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.core.client.GWT;

import junit.framework.TestCase;

public class ObjectIdBackReferenceTest extends TestCase {
	@JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id" )
	public static class Owner {
		private List<Child> children; 
		
		@JsonCreator
		Owner( @JsonProperty("children") List<Child> children) {
			this.children = children;
		}
		public Owner() {
			children = new ArrayList<Child>();
		}
		@JsonProperty("children")
		public List<Child> getChildren() {
			return children;
		}
		
		public Child addChild( String name ) {
			Child child = new Child( name );
			child.setOwner(this);
			children.add(child);
			return child;
		}
	}
	
	public static class Child {
		private Owner owner;
		private String name;
		
		@JsonCreator
		public Child( @JsonProperty("name") String name ) {
			this( null, name );
		}
		
		public Child( Owner owner, String name ) {
			this.owner = owner;
			this.name = name;
		}
		
		@JsonProperty("owner")
		@JsonIdentityReference
		void setOwner( Owner owner ) {
			this.owner = owner;
		}
		
		public Owner getOwner() {
			return owner;
		}
		
		
		@JsonProperty("name")
		public String getName() {
			return name;
		}
		
		public void setName( String name ) {
			this.name = name;
		}
	}
    
    public void testSerialize() throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	Owner origOwner = new Owner();
    	Child origChild = origOwner.addChild("item1");
    	String json = mapper.writeValueAsString(origOwner);
    	Owner jsonOwner = mapper.readValue(json, Owner.class);
    	Child jsonChild = jsonOwner.getChildren().get(0);
    	assertEquals("item1", jsonChild.getName());
    }
}
