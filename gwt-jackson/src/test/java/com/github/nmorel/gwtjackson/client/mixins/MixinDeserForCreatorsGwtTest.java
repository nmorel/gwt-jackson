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
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.BaseClass2;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.MixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.MixInFactory;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.MixInIgnore;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class MixinDeserForCreatorsGwtTest extends GwtJacksonTestCase {

    private MixinDeserForCreatorsTester tester = MixinDeserForCreatorsTester.INSTANCE;

    ///////

    public interface BaseClassReader extends ObjectReader<BaseClass>, ObjectReaderTester<BaseClass> {

        static BaseClassReader INSTANCE = GWT.create( BaseClassReader.class );
    }

    public void testForDefaultConstructor() {
        tester.testForDefaultConstructor( BaseClassReader.INSTANCE );
    }

    ///////

    @JsonMixIns( {@JsonMixIn( target = BaseClass.class, mixIn = MixIn.class )} )
    public interface BaseClassMixInReader extends ObjectReader<BaseClass>, ObjectReaderTester<BaseClass> {

        static BaseClassMixInReader INSTANCE = GWT.create( BaseClassMixInReader.class );
    }

    public void testForConstructor() {
        tester.testForConstructor( BaseClassMixInReader.INSTANCE );
    }

    ///////

    @JsonMixIns( {@JsonMixIn( target = BaseClass.class, mixIn = MixInFactory.class )} )
    public interface BaseClassMixInFactoryReader extends ObjectReader<BaseClass>, ObjectReaderTester<BaseClass> {

        static BaseClassMixInFactoryReader INSTANCE = GWT.create( BaseClassMixInFactoryReader.class );
    }

    public void testForFactory() {
        tester.testForFactory( BaseClassMixInFactoryReader.INSTANCE );
    }

    ///////

    @JsonMixIns( {@JsonMixIn( target = BaseClass2.class, mixIn = MixInIgnore.class )} )
    public interface BaseClass2MixInIgnoreReader extends ObjectReader<BaseClass2>, ObjectReaderTester<BaseClass2> {

        static BaseClass2MixInIgnoreReader INSTANCE = GWT.create( BaseClass2MixInIgnoreReader.class );
    }

    public void testForIgnoreCreator() {
        tester.testForIgnoreCreator( BaseClass2MixInIgnoreReader.INSTANCE );
    }

}
