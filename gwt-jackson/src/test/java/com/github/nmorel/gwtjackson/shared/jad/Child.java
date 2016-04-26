package com.github.nmorel.gwtjackson.shared.jad;

import java.util.HashMap;
import java.util.Map;

public class Child<T> {
	Map<T, T> typed = new HashMap<T, T>();
	
	Map untyped = new HashMap();

    
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
