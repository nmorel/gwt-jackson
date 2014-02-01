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

import java.util.HashMap;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.MixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.MixIn2;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.SubClass;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class MixinSerForFieldsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testFieldMixInsTopLevel() {
        objectMapper.addMixInAnnotations( BaseClass.class, MixIn.class );
        MixinSerForFieldsTester.INSTANCE.testFieldMixInsTopLevel( createWriter( BaseClass.class ) );
    }

    @Test
    public void testMultipleFieldMixIns() {
        HashMap<Class<?>, Class<?>> mixins = new HashMap<Class<?>, Class<?>>();
        mixins.put( SubClass.class, MixIn.class );
        mixins.put( BaseClass.class, MixIn2.class );
        objectMapper.setMixInAnnotations( mixins );
        MixinSerForFieldsTester.INSTANCE.testMultipleFieldMixIns( createWriter( SubClass.class ) );
    }
}
