package com.github.nmorel.gwtjackson.client.advanced.identity;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdentifiableCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.UUIDNode;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectIdDeserializationGwtTest extends GwtJacksonTestCase {

    public interface IdentifiableMapper extends ObjectReader<Identifiable>, JsonDecoderTester<Identifiable> {

        static IdentifiableMapper INSTANCE = GWT.create( IdentifiableMapper.class );
    }

    public interface UUIDNodeMapper extends ObjectMapper<UUIDNode>, JsonMapperTester<UUIDNode> {

        static UUIDNodeMapper INSTANCE = GWT.create( UUIDNodeMapper.class );
    }

    public interface IdWrapperMapper extends ObjectReader<IdWrapper>, JsonDecoderTester<IdWrapper> {

        static IdWrapperMapper INSTANCE = GWT.create( IdWrapperMapper.class );
    }

    public interface IdentifiableCustomMapper extends ObjectReader<IdentifiableCustom>, JsonDecoderTester<IdentifiableCustom> {

        static IdentifiableCustomMapper INSTANCE = GWT.create( IdentifiableCustomMapper.class );
    }

    public interface IdWrapperExtMapper extends ObjectReader<IdWrapperExt>, JsonDecoderTester<IdWrapperExt> {

        static IdWrapperExtMapper INSTANCE = GWT.create( IdWrapperExtMapper.class );
    }

    private ObjectIdDeserializationTester tester = ObjectIdDeserializationTester.INSTANCE;

    public void testSimpleDeserializationClass() {
        tester.testSimpleDeserializationClass( IdentifiableMapper.INSTANCE );
    }

    public void testSimpleUUIDForClassRoundTrip() {
        tester.testSimpleUUIDForClassRoundTrip( UUIDNodeMapper.INSTANCE );
    }

    public void testSimpleDeserializationProperty() {
        tester.testSimpleDeserializationProperty( IdWrapperMapper.INSTANCE );
    }

    public void testSimpleDeserWithForwardRefs() {
        tester.testSimpleDeserWithForwardRefs( IdWrapperMapper.INSTANCE );
    }

    public void testCustomDeserializationClass() {
        tester.testCustomDeserializationClass( IdentifiableCustomMapper.INSTANCE );
    }

    public void testCustomDeserializationProperty() {
        tester.testCustomDeserializationProperty( IdWrapperExtMapper.INSTANCE );
    }
}
