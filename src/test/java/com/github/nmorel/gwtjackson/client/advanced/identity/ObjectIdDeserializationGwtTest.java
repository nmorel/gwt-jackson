package com.github.nmorel.gwtjackson.client.advanced.identity;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdentifiableCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.UUIDNode;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class ObjectIdDeserializationGwtTest extends GwtJacksonTestCase
{
    public interface IdentifiableMapper extends JsonMapper<Identifiable>, JsonMapperTester<Identifiable>
    {
        static IdentifiableMapper INSTANCE = GWT.create( IdentifiableMapper.class );
    }

    public interface UUIDNodeMapper extends JsonMapper<UUIDNode>, JsonMapperTester<UUIDNode>
    {
        static UUIDNodeMapper INSTANCE = GWT.create( UUIDNodeMapper.class );
    }

    public interface IdWrapperMapper extends JsonMapper<IdWrapper>, JsonMapperTester<IdWrapper>
    {
        static IdWrapperMapper INSTANCE = GWT.create( IdWrapperMapper.class );
    }

    public interface IdentifiableCustomMapper extends JsonMapper<IdentifiableCustom>, JsonMapperTester<IdentifiableCustom>
    {
        static IdentifiableCustomMapper INSTANCE = GWT.create( IdentifiableCustomMapper.class );
    }

    public interface IdWrapperExtMapper extends JsonMapper<IdWrapperExt>, JsonMapperTester<IdWrapperExt>
    {
        static IdWrapperExtMapper INSTANCE = GWT.create( IdWrapperExtMapper.class );
    }

    private ObjectIdDeserializationTester tester = ObjectIdDeserializationTester.INSTANCE;

    public void testSimpleDeserializationClass()
    {
        tester.testSimpleDeserializationClass( IdentifiableMapper.INSTANCE );
    }

    public void testSimpleUUIDForClassRoundTrip()
    {
        tester.testSimpleUUIDForClassRoundTrip( UUIDNodeMapper.INSTANCE );
    }

    public void testSimpleDeserializationProperty()
    {
        tester.testSimpleDeserializationProperty( IdWrapperMapper.INSTANCE );
    }

    public void testSimpleDeserWithForwardRefs()
    {
        tester.testSimpleDeserWithForwardRefs( IdWrapperMapper.INSTANCE );
    }

    public void testCustomDeserializationClass()
    {
        tester.testCustomDeserializationClass( IdentifiableCustomMapper.INSTANCE );
    }

    public void testCustomDeserializationProperty()
    {
        tester.testCustomDeserializationProperty( IdWrapperExtMapper.INSTANCE );
    }
}
