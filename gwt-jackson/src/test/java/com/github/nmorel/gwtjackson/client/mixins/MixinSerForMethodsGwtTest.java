/*
 * Copyright 2014 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.mixins;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns.JsonMixIn;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.LeafClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.MixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.ObjectMixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.SimpleBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class MixinSerForMethodsGwtTest extends GwtJacksonTestCase {

    private MixinSerForMethodsTester tester = MixinSerForMethodsTester.INSTANCE;

    ///////

    @JsonMixIns({@JsonMixIn(target = BaseClass.class, mixIn = MixIn.class)})
    public interface BaseClassMixinWriter extends ObjectWriter<BaseClass>, ObjectWriterTester<BaseClass> {

        static BaseClassMixinWriter INSTANCE = GWT.create( BaseClassMixinWriter.class );
    }

    public void testLeafMixin() {
        tester.testLeafMixin( BaseClassMixinWriter.INSTANCE );
    }

    ///////

    @JsonMixIns({@JsonMixIn(target = BaseClass.class, mixIn = MixIn.class)})
    public interface LeafClassMixinWriter extends ObjectWriter<LeafClass>, ObjectWriterTester<LeafClass> {

        static LeafClassMixinWriter INSTANCE = GWT.create( LeafClassMixinWriter.class );
    }

    public void testIntermediateMixin() {
        tester.testIntermediateMixin( LeafClassMixinWriter.INSTANCE );
    }

    ///////

    // MixIn added via MixInConfiguration
    public interface SimpleBeanMixinWriter extends ObjectWriter<SimpleBean>, ObjectWriterTester<SimpleBean> {

        static SimpleBeanMixinWriter INSTANCE = GWT.create( SimpleBeanMixinWriter.class );
    }

    public void testIntermediateMixin2() {
        tester.testIntermediateMixin2( SimpleBeanMixinWriter.INSTANCE );
    }

    ///////

    @JsonMixIns({@JsonMixIn(target = Object.class, mixIn = ObjectMixIn.class)})
    public interface ObjectMixinWriter extends ObjectWriter<BaseClass>, ObjectWriterTester<BaseClass> {

        static ObjectMixinWriter INSTANCE = GWT.create( ObjectMixinWriter.class );
    }

    public void testObjectMixin() {
        tester.testObjectMixin( ObjectMixinWriter.INSTANCE );
    }

}
