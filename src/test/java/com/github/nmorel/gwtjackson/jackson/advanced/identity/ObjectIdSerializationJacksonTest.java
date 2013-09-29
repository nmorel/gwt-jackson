package com.github.nmorel.gwtjackson.jackson.advanced.identity;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.AlwaysContainer;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.Broken;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdWrapperCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdentifiableWithProp;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.TreeNode;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdSerializationJacksonTest extends AbstractJacksonTest {

    private ObjectIdSerializationTester tester = ObjectIdSerializationTester.INSTANCE;

    @Test
    public void testSimpleSerializationClass() {
        tester.testSimpleSerializationClass( createMapper( Identifiable.class ) );
    }

    @Test
    public void testSimpleSerializationProperty() {
        tester.testSimpleSerializationProperty( createMapper( IdWrapper.class ) );
    }

    @Test
    public void testCustomPropertyForClass() {
        tester.testCustomPropertyForClass( createMapper( IdentifiableWithProp.class ) );
    }

    @Test
    public void testCustomPropertyViaProperty() {
        tester.testCustomPropertyViaProperty( createMapper( IdWrapperCustom.class ) );
    }

    @Test
    public void testAlwaysAsId() {
        tester.testAlwaysAsId( createMapper( AlwaysContainer.class ) );
    }

    @Test
    public void testAlwaysIdForTree() {
        tester.testAlwaysIdForTree( createMapper( TreeNode.class ) );
    }

    @Test
    public void testInvalidProp() {
        tester.testInvalidProp( createMapper( Broken.class ) );
    }
}
