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

package com.github.nmorel.gwtjackson.jackson.mixins;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsDelegationTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsDelegationTester.BaseClassWithPrivateCtor;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsDelegationTester.MixInForPrivate;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsDelegationTester.StringWrapper;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsDelegationTester.StringWrapperMixIn;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class MixinDeserForCreatorsDelegationJacksonTest extends AbstractJacksonTest {

    @Test
    public void testForConstructor() {
        objectMapper.addMixIn( BaseClassWithPrivateCtor.class, MixInForPrivate.class );
        MixinDeserForCreatorsDelegationTester.INSTANCE.testForConstructor( createReader( BaseClassWithPrivateCtor.class ) );
    }

    @Test
    public void testForFactoryAndCtor() {
        objectMapper.addMixIn( MixinDeserForCreatorsDelegationTester.BaseClass.class, MixinDeserForCreatorsDelegationTester.MixIn.class );
        MixinDeserForCreatorsDelegationTester.INSTANCE
                .testForFactoryAndCtor( createReader( MixinDeserForCreatorsDelegationTester.BaseClass.class ) );
    }

    @Test
    public void testFactoryMixIn() {
        objectMapper.addMixIn( StringWrapper.class, StringWrapperMixIn.class );
        MixinDeserForCreatorsDelegationTester.INSTANCE.testFactoryMixIn( createReader( StringWrapper.class ) );
    }
}
