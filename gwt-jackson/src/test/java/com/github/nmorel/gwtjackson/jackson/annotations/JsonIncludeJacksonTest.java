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

package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.BeanJsonInclude;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.BeanJsonIncludeOnProperties;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeAlways;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeNonDefault;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeNonEmpty;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeNonNull;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIncludeTester.MixInIncludeUseDefaults;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonIncludeJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeAlways() {
        objectMapper.addMixIn( BeanJsonInclude.class, MixInIncludeAlways.class );
        JsonIncludeTester.INSTANCE.testSerializeAlways( createWriter( BeanJsonInclude.class ) );
    }

    @Test
    public void testSerializeNonDefault() {
        objectMapper.addMixIn( BeanJsonInclude.class, MixInIncludeNonDefault.class );
        JsonIncludeTester.INSTANCE.testSerializeNonDefault( createWriter( BeanJsonInclude.class ) );
    }

    @Test
    public void testSerializeNonEmpty() {
        objectMapper.addMixIn( BeanJsonInclude.class, MixInIncludeNonEmpty.class );
        JsonIncludeTester.INSTANCE.testSerializeNonEmpty( createWriter( BeanJsonInclude.class ) );
    }

    @Test
    public void testSerializeNonNull() {
        objectMapper.addMixIn( BeanJsonInclude.class, MixInIncludeNonNull.class );
        JsonIncludeTester.INSTANCE.testSerializeNonNull( createWriter( BeanJsonInclude.class ) );
    }

    @Test
    public void testSerializeUseDefaults() {
        objectMapper.addMixIn( BeanJsonInclude.class, MixInIncludeUseDefaults.class );
        JsonIncludeTester.INSTANCE.testSerializeUseDefaults( createWriter( BeanJsonInclude.class ) );
    }

    @Test
    public void testSerializeProperties() {
        objectMapper.addMixIn( BeanJsonInclude.class, MixInIncludeNonNull.class );
        JsonIncludeTester.INSTANCE.testSerializeProperties( createWriter( BeanJsonIncludeOnProperties.class ) );
    }
}
