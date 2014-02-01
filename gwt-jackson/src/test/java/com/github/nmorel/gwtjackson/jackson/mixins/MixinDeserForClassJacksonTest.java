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
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester.LeafClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForClassTester.MixIn;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class MixinDeserForClassJacksonTest extends AbstractJacksonTest {

    @Test
    public void testClassMixInsTopLevel() {
        objectMapper.addMixInAnnotations( LeafClass.class, MixIn.class );
        MixinDeserForClassTester.INSTANCE.testClassMixInsTopLevel( createReader( LeafClass.class ) );
    }

    @Test
    public void testClassMixInsMidLevel() {
        objectMapper.addMixInAnnotations( BaseClass.class, MixIn.class );
        MixinDeserForClassTester.INSTANCE.testClassMixInsMidLevel( createReader( BaseClass.class ), createReader( LeafClass.class ) );
    }

    @Test
    public void testClassMixInsForObjectClass() {
        objectMapper.addMixInAnnotations( Object.class, MixIn.class );
        MixinDeserForClassTester.INSTANCE.testClassMixInsForObjectClass( createReader( BaseClass.class ), createReader( LeafClass.class ) );
    }
}
