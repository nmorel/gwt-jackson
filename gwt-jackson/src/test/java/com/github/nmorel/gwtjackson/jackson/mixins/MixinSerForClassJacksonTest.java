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
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForClassTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForClassTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForClassTester.LeafClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForClassTester.LeafClassToMixin;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForClassTester.MixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForClassTester.MixInAutoDetect;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class MixinSerForClassJacksonTest extends AbstractJacksonTest {

    @Test
    public void testClassMixInsTopLevel() {
        objectMapper.addMixInAnnotations( LeafClassToMixin.class, MixIn.class );
        objectMapper.addMixInAnnotations( BaseClass.class, MixIn.class );
        MixinSerForClassTester.INSTANCE.testClassMixInsTopLevel( createWriter( LeafClassToMixin.class ), createWriter( LeafClass.class ) );
    }

    @Test
    public void testClassMixInsMidLevel() {
        objectMapper.addMixInAnnotations( BaseClass.class, MixInAutoDetect.class );
        MixinSerForClassTester.INSTANCE.testClassMixInsMidLevel( createWriter( LeafClass.class ) );
    }
}
