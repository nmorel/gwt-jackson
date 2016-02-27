/*
 * Copyright 2016 Nicolas Morel
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

package com.github.nmorel.gwtjackson.shared.annotations;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.model.Animal;

/**
 * @author Nicolas Morel
 */
public final class JsonDeserializeBuilderTester extends AbstractTester {

    public static final JsonDeserializeBuilderTester INSTANCE = new JsonDeserializeBuilderTester();

    private JsonDeserializeBuilderTester() {
    }

    public void testDeserialize( ObjectReaderTester<Animal> reader ) {
        String input = "{\"name\":\"Human\",\"numberOfLegs\":2}";

        Animal result = reader.read( input );

        assertEquals( "Human", result.name() );
        assertEquals( 2, result.numberOfLegs() );
    }

}
