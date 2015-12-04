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

package com.github.nmorel.gwtjackson.guava.client;

import java.util.Arrays;
import java.util.List;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectGwtTest extends GwtJacksonGuavaTestCase {

    public interface BeanWithObjectMapper extends ObjectMapper<BeanWithObject>, ObjectMapperTester<BeanWithObject> {

        static BeanWithObjectMapper INSTANCE = GWT.create( BeanWithObjectMapper.class );
    }

    public static class BeanWithObject<T> {

        private List<T> list;

        public BeanWithObject() {

        }

        public List<T> getList() {
            return list;
        }

        public void setList( List<T> list ) {
            this.list = list;
        }
    }

    public void testSerialize() {
        BeanWithObject<Integer> bean = new BeanWithObject<Integer>();
        bean.setList( Arrays.asList( 1, 2, 3, 4 ) );

        String actual = BeanWithObjectMapper.INSTANCE.write( bean );

        assertEquals( "{\"list\":[1,2,3,4]}", actual );
    }

    public void testDeserialize() {
        BeanWithObject<Integer> actual = BeanWithObjectMapper.INSTANCE.read( "{\"list\":[1,2,3,4]}" );

        assertEquals( Arrays.asList( 1, 2, 3, 4 ), actual.getList() );
    }
}
