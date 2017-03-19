package com.github.nmorel.gwtjackson.client.jad;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.jad.IntegerParent;
import com.github.nmorel.gwtjackson.shared.jad.Owner;
import com.github.nmorel.gwtjackson.shared.jad.StringParent;
import com.google.gwt.core.client.GWT;

public class GWTJacksonTest extends GwtJacksonTestCase {
	
    public interface OwnerMapper extends ObjectMapper<Owner>, ObjectMapperTester<Owner> {

        static OwnerMapper INSTANCE = GWT.create( OwnerMapper.class );
    }
    
	public void test() throws Exception  {
	    Owner owner = Owner.init();
		
        String json = OwnerMapper.INSTANCE.write(owner);
        // parents.child.typed is not serialized
        //
        //{
        //   "@c": ".Owner",
        //   "parents": [
        //      {
        //         "@c": ".StringParent",
        //         "child": {
        //            "untyped": { "1": "1" }
        //         },
        //         "typed": { "1": "1" },
        //         "untyped": { "1": "1" }
        //      },
        //      {
        //         "@c": ".IntegerParent",
        //         "child": {
        //            "untyped": {  "1": 1 }
        //         },
        //         "typed": {  "1": 1 },
        //         "untyped": { "1": 1 }
        //      }
        //   ]
        //}

        owner = OwnerMapper.INSTANCE.read("{\"@c\":\".Owner\",\"parents\":[{\"@c\":\".StringParent\",\"child\":{\"typed\":{\"1\":\"1\"},\"untyped\":{\"1\":\"1\"}},\"typed\":{\"1\":\"1\"},\"untyped\":{\"1\":\"1\"}},{\"@c\":\".IntegerParent\",\"child\":{\"typed\":{\"1\":1},\"untyped\":{\"1\":1}},\"typed\":{\"1\":1},\"untyped\":{\"1\":1}}]}");

        StringParent sp = (StringParent)owner.getParents().get(0);
        IntegerParent ip = (IntegerParent)owner.getParents().get(1);
        
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
