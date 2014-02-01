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
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns.JsonMixIn;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.BaseClass;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.MixIn;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.MixIn2;
import com.github.nmorel.gwtjackson.shared.mixins.MixinSerForFieldsTester.SubClass;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class MixinSerForFieldsGwtTest extends GwtJacksonTestCase {

    private MixinSerForFieldsTester tester = MixinSerForFieldsTester.INSTANCE;

    ///////

    @JsonMixIns({@JsonMixIn(target = BaseClass.class, mixIn = MixIn.class)})
    public interface BaseClassWriter extends ObjectWriter<BaseClass>, ObjectWriterTester<BaseClass> {

        static BaseClassWriter INSTANCE = GWT.create( BaseClassWriter.class );
    }

    public void testFieldMixInsTopLevel() {
        tester.testFieldMixInsTopLevel( BaseClassWriter.INSTANCE );
    }

    ///////

    @JsonMixIns({@JsonMixIn(target = SubClass.class, mixIn = MixIn.class), @JsonMixIn(target = BaseClass.class,
            mixIn = MixIn2.class)})
    public interface SubClassWriter extends ObjectWriter<SubClass>, ObjectWriterTester<SubClass> {

        static SubClassWriter INSTANCE = GWT.create( SubClassWriter.class );
    }

    public void testMultipleFieldMixIns() {
        tester.testMultipleFieldMixIns( SubClassWriter.INSTANCE );
    }

}
