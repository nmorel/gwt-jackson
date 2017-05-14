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

package com.github.nmorel.gwtjackson.shared.mapper;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class PropertyNamingTester extends AbstractTester {

    @JsonPropertyOrder( alphabetic = true )
    public static class PropertyNamingBean {

        private String simpleName;

        private List<String> HTML;

        private String HTMLParser;

        public String getSimpleName() {
            return simpleName;
        }

        public void setSimpleName( String simpleName ) {
            this.simpleName = simpleName;
        }

        public List<String> getHTML() {
            return HTML;
        }

        public void setHTML( List<String> HTML ) {
            this.HTML = HTML;
        }

        public List<String> getHTML( String filter ) {
            return HTML;
        }

        public String getHTMLParser() {
            return HTMLParser;
        }

        public void setHTMLParser( String HTMLParser ) {
            this.HTMLParser = HTMLParser;
        }
    }

    public static final PropertyNamingTester INSTANCE = new PropertyNamingTester();

    private PropertyNamingTester() {

    }

    public void testValue( ObjectMapperTester<PropertyNamingBean> mapper ) {

        PropertyNamingBean bean = new PropertyNamingBean();
        bean.setSimpleName( "simple" );
        bean.setHTML( Arrays.asList("html") );
        bean.setHTMLParser( "htmlparser" );

        String json = mapper.write( bean );
        assertEquals( "{\"html\":[\"html\"],\"htmlparser\":\"htmlparser\",\"simpleName\":\"simple\"}", json );

        bean = mapper.read( json );
        assertEquals( "simple", bean.getSimpleName() );
        assertEquals( Arrays.asList("html"), bean.getHTML() );
        assertEquals( "htmlparser", bean.getHTMLParser() );
    }
}
