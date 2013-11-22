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

package com.github.nmorel.gwtjackson.client.advanced.identity;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Company;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Wrapper;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectIdGwtTest extends GwtJacksonTestCase {

    public interface WrapperMapper extends ObjectMapper<Wrapper>, ObjectMapperTester<Wrapper> {

        static WrapperMapper INSTANCE = GWT.create( WrapperMapper.class );
    }

    public interface CompanyMapper extends ObjectMapper<Company>, ObjectMapperTester<Company> {

        static CompanyMapper INSTANCE = GWT.create( CompanyMapper.class );
    }

    private ObjectIdTester tester = ObjectIdTester.INSTANCE;

    public void testColumnMetadata() {
        tester.testColumnMetadata( WrapperMapper.INSTANCE );
    }

    public void testMixedRefsIssue188() {
        tester.testMixedRefsIssue188( CompanyMapper.INSTANCE );
    }
}
