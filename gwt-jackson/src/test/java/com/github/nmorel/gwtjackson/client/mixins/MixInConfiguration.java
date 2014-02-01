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

import com.github.nmorel.gwtjackson.client.AbstractConfiguration;

/**
 * Configuration class to define all the MixIn annotations used in tests
 *
 * @author Nicolas Morel.
 */
public class MixInConfiguration extends AbstractConfiguration {

    @Override
    protected void configure() {
        // MixinDeserForClassTester
        //        addMixInAnnotations( LeafClassToMixIn.class, MixinDeserForClassTester.MixIn.class );
        //        addMixInAnnotations( BaseClass.class, MixinDeserForClassTester.MixIn.class );

        //        // MixinDeserForCreatorsTester
        //        addMixInAnnotations( MixinDeserForCreatorsTester.BaseClassWithPrivateCtor.class,
        // MixinDeserForCreatorsTester.MixInForPrivate
        //                .class );
        //        addMixInAnnotations( MixinDeserForCreatorsTester.BaseClass.class, MixinDeserForCreatorsTester.MixIn.class );
        //        addMixInAnnotations( MixinDeserForCreatorsTester.StringWrapper.class, MixinDeserForCreatorsTester.StringWrapperMixIn
        // .class );
        //
        //        // MixinDeserForMethodsTester
        //        addMixInAnnotations( MixinDeserForMethodsTester.BaseClass.class, MixinDeserForMethodsTester.MixIn.class );
        //
        //        // MixinInheritanceTester
        //        addMixInAnnotations( MixinInheritanceTester.Beano.class, MixinInheritanceTester.BeanoMixinSub.class );
        //        addMixInAnnotations( MixinInheritanceTester.Beano2.class, MixinInheritanceTester.BeanoMixinSub2.class );
        //
        //        // MixinSerForClassTester
        //        addMixInAnnotations( MixinSerForClassTester.LeafClassToMixin.class, MixinSerForClassTester.MixIn.class );
        //        addMixInAnnotations( MixinSerForClassTester.BaseClass.class, MixinSerForClassTester.MixIn.class );
        //        addMixInAnnotations( MixinSerForClassTester.BaseClass.class, MixinSerForClassTester.MixInAutoDetect.class );
        //
        //        // MixinSerForFieldsTester
        //        addMixInAnnotations( MixinSerForFieldsTester.BaseClass.class, MixinSerForFieldsTester.MixIn.class );
        //        addMixInAnnotations( MixinSerForFieldsTester.SubClass.class, MixinSerForFieldsTester.MixIn.class );
        //        addMixInAnnotations( MixinSerForFieldsTester.BaseClass.class, MixinSerForFieldsTester.MixIn2.class );
        //
        //        // MixinSerForMethodsTester
        //        addMixInAnnotations( MixinSerForMethodsTester.BaseClass.class, MixinSerForMethodsTester.MixIn.class );
        //        addMixInAnnotations( MixinSerForMethodsTester.EmptyBean.class, MixinSerForMethodsTester.MixInForSimple.class );
    }
}

