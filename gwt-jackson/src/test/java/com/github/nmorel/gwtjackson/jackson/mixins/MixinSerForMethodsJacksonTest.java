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

import java.util.List;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.EmptyBean;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.LeafClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.MixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.MixInForSimple;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.ObjectMixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForMethodsTester.SimpleBean;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class MixinSerForMethodsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testLeafMixin() {
        objectMapper.addMixIn( BaseClass.class, MixIn.class );
        MixinSerForMethodsTester.INSTANCE.testLeafMixin( createWriter( BaseClass.class ) );
    }

    @Test
    public void testIntermediateMixin() {
        objectMapper.addMixIn( BaseClass.class, MixIn.class );

        JavaType type = objectMapper.getTypeFactory().constructType( LeafClass.class );
        BeanDescription desc = objectMapper.getSerializationConfig().introspect( type );
        List<BeanPropertyDefinition> props = desc.findProperties();

        MixinSerForMethodsTester.INSTANCE.testIntermediateMixin( createWriter( LeafClass.class ) );
    }

    @Test
    public void testIntermediateMixin2() {
        objectMapper.addMixIn( EmptyBean.class, MixInForSimple.class );
        MixinSerForMethodsTester.INSTANCE.testIntermediateMixin2( createWriter( SimpleBean.class ) );
    }

    @Test
    public void testObjectMixin() {
        objectMapper.addMixIn( Object.class, ObjectMixIn.class );
        MixinSerForMethodsTester.INSTANCE.testObjectMixin( createWriter( BaseClass.class ) );
    }
}
