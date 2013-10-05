package com.github.nmorel.gwtjackson.client.advanced.identity;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithEqualsTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithEqualsTester.Foo;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectIdWithEqualsGwtTest extends GwtJacksonTestCase {

    public interface FooMapper extends ObjectMapper<Foo> {

        static FooMapper INSTANCE = GWT.create( FooMapper.class );
    }

    private ObjectIdWithEqualsTester tester = ObjectIdWithEqualsTester.INSTANCE;

    public void testSimpleEquals() {
        tester
            .testSimpleEquals( createMapper( FooMapper.INSTANCE, newDefaultDeserializationContext(), new JsonSerializationContext.Builder()
                .useEqualityForObjectId( true ).build() ) );
    }
}
