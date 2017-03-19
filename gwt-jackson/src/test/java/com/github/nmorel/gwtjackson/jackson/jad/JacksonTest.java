package com.github.nmorel.gwtjackson.jackson.jad;

import junit.framework.TestCase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.jad.IntegerParent;
import com.github.nmorel.gwtjackson.shared.jad.Owner;
import com.github.nmorel.gwtjackson.shared.jad.StringParent;

public class JacksonTest extends TestCase {

    public void test() throws Exception {
        Owner owner = Owner.init();

        String json = new ObjectMapper().writer().writeValueAsString(owner);
System.out.println(json);
        owner = new ObjectMapper().readValue(json, Owner.class);

        StringParent sp = (StringParent) owner.getParents().get(0);
        IntegerParent ip = (IntegerParent) owner.getParents().get(1);

        // Raw map : keys are strings
        assertNotNull(sp.getUntyped().get("1"));
        assertNotNull(ip.getUntyped().get("1"));
        assertNotNull(sp.getChild().getUntyped().get("1"));
        assertNotNull(ip.getChild().getUntyped().get("1"));

        // Typed map : keys are integer or string
        assertNotNull(sp.getTyped().get("1"));
        assertNotNull(sp.getChild().getTyped().get("1"));
        assertNotNull(ip.getTyped().get(1));
        assertNotNull(ip.getChild().getTyped().get(1));
    }
}
