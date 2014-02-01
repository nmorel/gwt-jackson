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
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns.JsonMixIn;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester.LeafClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester.MixIn;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class MixinDeserForClassGwtTest extends GwtJacksonTestCase {

    private MixinDeserForClassTester tester = MixinDeserForClassTester.INSTANCE;

    ///////

    @JsonMixIns( {@JsonMixIn( target = LeafClass.class, mixIn = MixIn.class )} )
    public interface LeafClassTopLevelReader extends ObjectReader<LeafClass>, ObjectReaderTester<LeafClass> {

        static LeafClassTopLevelReader INSTANCE = GWT.create( LeafClassTopLevelReader.class );
    }

    public void testClassMixInsTopLevel() {
        tester.testClassMixInsTopLevel( LeafClassTopLevelReader.INSTANCE );
    }

    ///////

    @JsonMixIns( {@JsonMixIn( target = BaseClass.class, mixIn = MixIn.class )} )
    public interface BaseClassMidLevelReader extends ObjectReader<BaseClass>, ObjectReaderTester<BaseClass> {

        static BaseClassMidLevelReader INSTANCE = GWT.create( BaseClassMidLevelReader.class );
    }

    @JsonMixIns( {@JsonMixIn( target = BaseClass.class, mixIn = MixIn.class )} )
    public interface LeafClassMidLevelReader extends ObjectReader<LeafClass>, ObjectReaderTester<LeafClass> {

        static LeafClassMidLevelReader INSTANCE = GWT.create( LeafClassMidLevelReader.class );
    }

    public void testClassMixInsMidLevel() {
        tester.testClassMixInsMidLevel( BaseClassMidLevelReader.INSTANCE, LeafClassMidLevelReader.INSTANCE );
    }

    ///////

    @JsonMixIns( {@JsonMixIn( target = Object.class, mixIn = MixIn.class )} )
    public interface BaseClassObjectMixInReader extends ObjectReader<BaseClass>, ObjectReaderTester<BaseClass> {

        static BaseClassObjectMixInReader INSTANCE = GWT.create( BaseClassObjectMixInReader.class );
    }

    @JsonMixIns( {@JsonMixIn( target = Object.class, mixIn = MixIn.class )} )
    public interface LeafClassObjectReader extends ObjectReader<LeafClass>, ObjectReaderTester<LeafClass> {

        static LeafClassObjectReader INSTANCE = GWT.create( LeafClassObjectReader.class );
    }

    public void testClassMixInsForObjectClass() {
        tester.testClassMixInsForObjectClass( BaseClassObjectMixInReader.INSTANCE, LeafClassObjectReader.INSTANCE );
    }
}
