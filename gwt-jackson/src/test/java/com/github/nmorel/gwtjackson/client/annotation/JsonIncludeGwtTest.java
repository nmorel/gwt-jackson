/*
 * Copyright 2015 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.annotation;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns.JsonMixIn;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.BeanJsonInclude;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.BeanJsonIncludeOnProperties;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeAlways;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeNonDefault;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeNonEmpty;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeNonNull;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeUseDefaults;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonIncludeGwtTest extends GwtJacksonTestCase {

    private JsonIncludeTester tester = JsonIncludeTester.INSTANCE;

    /* ################################ */

    @JsonMixIns( value = {@JsonMixIn( target = BeanJsonInclude.class, mixIn = MixInIncludeAlways.class )} )
    public interface BeanJsonIncludeAlwaysMapper extends ObjectWriter<BeanJsonInclude>, ObjectWriterTester<BeanJsonInclude> {

        static BeanJsonIncludeAlwaysMapper INSTANCE = GWT.create( BeanJsonIncludeAlwaysMapper.class );
    }

    public void testSerializeAlways() {
        tester.testSerializeAlways( createWriter( BeanJsonIncludeAlwaysMapper.INSTANCE, JsonSerializationContext.builder()
                .serializeNulls( false ).build() ) );
    }

    /* ################################ */

    @JsonMixIns( value = {@JsonMixIn( target = BeanJsonInclude.class, mixIn = MixInIncludeNonDefault.class )} )
    public interface BeanJsonIncludeNonDefaultMapper extends ObjectWriter<BeanJsonInclude>, ObjectWriterTester<BeanJsonInclude> {

        static BeanJsonIncludeNonDefaultMapper INSTANCE = GWT.create( BeanJsonIncludeNonDefaultMapper.class );
    }

    public void testSerializeNonDefault() {
        tester.testSerializeNonDefault( BeanJsonIncludeNonDefaultMapper.INSTANCE );
    }

    /* ################################ */

    @JsonMixIns( value = {@JsonMixIn( target = BeanJsonInclude.class, mixIn = MixInIncludeNonEmpty.class )} )
    public interface BeanJsonIncludeNonEmptyMapper extends ObjectWriter<BeanJsonInclude>, ObjectWriterTester<BeanJsonInclude> {

        static BeanJsonIncludeNonEmptyMapper INSTANCE = GWT.create( BeanJsonIncludeNonEmptyMapper.class );
    }

    public void testSerializeNonEmpty() {
        tester.testSerializeNonEmpty( BeanJsonIncludeNonEmptyMapper.INSTANCE );
    }

    /* ################################ */

    @JsonMixIns( value = {@JsonMixIn( target = BeanJsonInclude.class, mixIn = MixInIncludeNonNull.class )} )
    public interface BeanJsonIncludeNonNullMapper extends ObjectWriter<BeanJsonInclude>, ObjectWriterTester<BeanJsonInclude> {

        static BeanJsonIncludeNonNullMapper INSTANCE = GWT.create( BeanJsonIncludeNonNullMapper.class );
    }

    public void testSerializeNonNull() {
        tester.testSerializeNonNull( BeanJsonIncludeNonNullMapper.INSTANCE );
    }

    /* ################################ */

    @JsonMixIns( value = {@JsonMixIn( target = BeanJsonInclude.class, mixIn = MixInIncludeUseDefaults.class )} )
    public interface BeanJsonIncludeUseDefaultsMapper extends ObjectWriter<BeanJsonInclude>, ObjectWriterTester<BeanJsonInclude> {

        static BeanJsonIncludeUseDefaultsMapper INSTANCE = GWT.create( BeanJsonIncludeUseDefaultsMapper.class );
    }

    public void testSerializeUseDefaults() {
        tester.testSerializeUseDefaults( BeanJsonIncludeUseDefaultsMapper.INSTANCE );
    }

    /* ################################ */

    public interface BeanJsonIncludeOnPropertiesMapper extends ObjectWriter<BeanJsonIncludeOnProperties>,
            ObjectWriterTester<BeanJsonIncludeOnProperties> {

        static BeanJsonIncludeOnPropertiesMapper INSTANCE = GWT.create( BeanJsonIncludeOnPropertiesMapper.class );
    }

    public void testSerializeProperties() {
        tester.testSerializeProperties( BeanJsonIncludeOnPropertiesMapper.INSTANCE );
    }
}
