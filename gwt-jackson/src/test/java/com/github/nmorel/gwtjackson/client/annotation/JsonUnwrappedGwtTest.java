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
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonUnwrappedTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonUnwrappedTester.BeanWrapper;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonUnwrappedGwtTest extends GwtJacksonTestCase {

    public interface BeanWrapperWriter extends ObjectWriter<BeanWrapper>, ObjectWriterTester<BeanWrapper> {

        static BeanWrapperWriter INSTANCE = GWT.create( BeanWrapperWriter.class );
    }

    public interface BeanWrapperReader extends ObjectReader<BeanWrapper>, ObjectReaderTester<BeanWrapper> {

        static BeanWrapperReader INSTANCE = GWT.create( BeanWrapperReader.class );
    }

    private JsonUnwrappedTester tester = JsonUnwrappedTester.INSTANCE;

    public void testSerialize() {
        tester.testSerialize( BeanWrapperWriter.INSTANCE );
    }

    // TODO Implements @JsonUnwrapped deserialization
    //    public void testDeserialize() {
    //        tester.testDeserialize( BeanWrapperReader.INSTANCE );
    //    }
}
