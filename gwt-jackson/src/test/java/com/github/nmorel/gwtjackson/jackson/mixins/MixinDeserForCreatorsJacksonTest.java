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
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.BaseClass2;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.MixInFactory;
import com.github.nmorel.gwtjackson.shared.mixins.MixinDeserForCreatorsTester.MixInIgnore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class MixinDeserForCreatorsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testForDefaultConstructor() {
        MixinDeserForCreatorsTester.INSTANCE.testForDefaultConstructor( createReader( BaseClass.class ) );
    }

    @Test
    public void testForConstructor() {
        objectMapper.addMixIn( BaseClass.class, MixinDeserForCreatorsTester.MixIn.class );
        MixinDeserForCreatorsTester.INSTANCE.testForConstructor( createReader( BaseClass.class ) );
    }

    @Test
    public void testForFactory() {
        objectMapper.addMixIn( BaseClass.class, MixInFactory.class );
        MixinDeserForCreatorsTester.INSTANCE.testForFactory( createReader( BaseClass.class ) );
    }

    @Test
    public void testForIgnoreCreator() {
        objectMapper.addMixIn( BaseClass2.class, MixInIgnore.class );
        MixinDeserForCreatorsTester.INSTANCE.testForIgnoreCreator( createReader( BaseClass2.class ) );
    }
}
