package com.github.nmorel.gwtjackson.shared.jad;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY)
public class Parent<T> {

    Child<T> child;

    Map<T, T> typed = new HashMap<T, T>();

    Map untyped = new HashMap();

    public Child<T> getChild() {
        return child;
    }

    public void setChild(Child<T> child) {
        this.child = child;
    }

    public Map<T, T> getTyped() {
        return typed;
    }

    public void setTyped(Map<T, T> typed) {
        this.typed = typed;
    }

    public Map getUntyped() {
        return untyped;
    }

    public void setUntyped(Map untyped) {
        this.untyped = untyped;
    }
}
