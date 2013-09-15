package com.github.nmorel.gwtjackson.jackson.advanced.identity;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdentifiableCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.UUIDNode;
import org.junit.Test;

/** @author Nicolas Morel */
public class ObjectIdDeserializationJacksonTest extends AbstractJacksonTest
{
    private ObjectIdDeserializationTester tester = ObjectIdDeserializationTester.INSTANCE;

    @Test
    public void testSimpleDeserializationClass()
    {
        tester.testSimpleDeserializationClass( createMapper( Identifiable.class ) );
    }

    @Test
    public void testSimpleUUIDForClassRoundTrip()
    {
        tester.testSimpleUUIDForClassRoundTrip( createMapper( UUIDNode.class ) );
    }

    @Test
    public void testSimpleDeserializationProperty()
    {
        tester.testSimpleDeserializationProperty( createMapper( IdWrapper.class ) );
    }

    @Test
    public void testSimpleDeserWithForwardRefs()
    {
        tester.testSimpleDeserWithForwardRefs( createMapper( IdWrapper.class ) );
    }

    @Test
    public void testCustomDeserializationClass()
    {
        tester.testCustomDeserializationClass( createMapper( IdentifiableCustom.class ) );
    }

    @Test
    public void testCustomDeserializationProperty()
    {
        tester.testCustomDeserializationProperty( createMapper( IdWrapperExt.class ) );
    }
}
