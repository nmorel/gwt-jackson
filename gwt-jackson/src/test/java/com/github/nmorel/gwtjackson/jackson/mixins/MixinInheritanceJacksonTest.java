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
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.Beano;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.Beano2;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.BeanoMixinSub;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.BeanoMixinSub2;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class MixinInheritanceJacksonTest extends AbstractJacksonTest {

    @Test
    public void testMixinFieldInheritance() {
        objectMapper.addMixInAnnotations( Beano.class, BeanoMixinSub.class );
        MixinInheritanceTester.INSTANCE.testMixinFieldInheritance( createMapper( Beano.class ) );
    }

    @Test
    public void testMixinMethodInheritance() {
        objectMapper.addMixInAnnotations( Beano2.class, BeanoMixinSub2.class );
        MixinInheritanceTester.INSTANCE.testMixinMethodInheritance( createMapper( Beano2.class ) );
    }
}
