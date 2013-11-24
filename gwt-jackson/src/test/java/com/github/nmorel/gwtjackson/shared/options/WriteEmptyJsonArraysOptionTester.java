/*
 * Copyright 2013 Nicolas Morel
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

package com.github.nmorel.gwtjackson.shared.options;

import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class WriteEmptyJsonArraysOptionTester extends AbstractTester {

    public static class EmptyListBean {

        public List<String> empty = new ArrayList<String>();
    }

    public static class EmptyArrayBean {

        public String[] empty = new String[0];
    }

    public static final WriteEmptyJsonArraysOptionTester INSTANCE = new WriteEmptyJsonArraysOptionTester();

    private WriteEmptyJsonArraysOptionTester() {
    }

    public void testWriteEmptyList( ObjectWriterTester<EmptyListBean> writer ) {
        assertEquals( "{\"empty\":[]}", writer.write( new EmptyListBean() ) );
    }

    public void testWriteEmptyArray( ObjectWriterTester<EmptyArrayBean> writer ) {
        assertEquals( "{\"empty\":[]}", writer.write( new EmptyArrayBean() ) );
    }

    public void testWriteNonEmptyList( ObjectWriterTester<EmptyListBean> writer ) {
        assertEquals( "{}", writer.write( new EmptyListBean() ) );
    }

    public void testWriteNonEmptyArray( ObjectWriterTester<EmptyArrayBean> writer ) {
        assertEquals( "{}", writer.write( new EmptyArrayBean() ) );
    }

}
