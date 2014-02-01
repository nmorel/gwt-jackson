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
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns.JsonMixIn;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.Beano;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.Beano2;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.BeanoMixinSub;
import com.github.nmorel.gwtjackson.shared.mixins.MixinInheritanceTester.BeanoMixinSub2;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class MixinInheritanceGwtTest extends GwtJacksonTestCase {

    private MixinInheritanceTester tester = MixinInheritanceTester.INSTANCE;

    ///////

    @JsonMixIns({@JsonMixIn(target = Beano.class, mixIn = BeanoMixinSub.class)})
    public interface BeanoMapper extends ObjectMapper<Beano>, ObjectMapperTester<Beano> {

        static BeanoMapper INSTANCE = GWT.create( BeanoMapper.class );
    }

    public void testMixinFieldInheritance() {
        tester.testMixinFieldInheritance( BeanoMapper.INSTANCE );
    }

    ///////

    @JsonMixIns({@JsonMixIn(target = Beano2.class, mixIn = BeanoMixinSub2.class)})
    public interface Beano2Mapper extends ObjectMapper<Beano2>, ObjectMapperTester<Beano2> {

        static Beano2Mapper INSTANCE = GWT.create( Beano2Mapper.class );
    }

    public void testMixinMethodInheritance() {
        tester.testMixinMethodInheritance( Beano2Mapper.INSTANCE );
    }

}
