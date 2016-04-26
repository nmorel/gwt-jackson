package com.github.nmorel.gwtjackson.shared.jad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY)
public class Owner {

    List<Parent<?>> parents = new ArrayList<Parent<?>>();

    public List<Parent<?>> getParents() {
        return parents;
    }

    public void setParents(List<Parent<?>> parents) {
        this.parents = parents;
    }

    public static <T> Map<T, T> createMap(T t1, T t2) {
        Map<T, T> map = new HashMap<T, T>();
        map.put(t1, t2);
        return map;
    }

    public static Owner init() {
        Owner owner = new Owner();

        StringParent sp = new StringParent();
        sp.setTyped(createMap("1", "1"));
        sp.setUntyped(createMap("1", "1"));

        Child<String> sc = new Child<String>();
        sc.setTyped(createMap("1", "1"));
        sc.setUntyped(createMap("1", "1"));
        sp.setChild(sc);

        IntegerParent ip = new IntegerParent();
        ip.setTyped(createMap(1, 1));
        ip.setUntyped(createMap(1, 1));

        Child<Integer> ic = new Child<Integer>();
        ic.setTyped(createMap(1, 1));
        ic.setUntyped(createMap(1, 1));
        ip.setChild(ic);

        owner.getParents().add(sp);
        owner.getParents().add(ip);

        return owner;
    }

}
