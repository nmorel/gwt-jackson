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

package com.github.nmorel.gwtjackson.shared.annotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class JsonIncludeTester extends AbstractTester {

    @JsonInclude( value = Include.ALWAYS )
    public static interface MixInIncludeAlways {}

    @JsonInclude( value = Include.NON_DEFAULT )
    public static interface MixInIncludeNonDefault {}

    @JsonInclude( value = Include.NON_EMPTY )
    public static interface MixInIncludeNonEmpty {}

    @JsonInclude( value = Include.NON_NULL )
    public static interface MixInIncludeNonNull {}

    public static class BeanJsonInclude {

        public String stringNull = null;

        public String stringEmpty = "";

        public Integer integerNull = null;

        public Integer integerZero = 0;

        public int intDefault = 0;

        public BigInteger bigIntegerNull = null;

        public BigInteger bigIntegerZero = BigInteger.ZERO;

        public BigDecimal bigDecimalNull = null;

        public BigDecimal bigDecimalZero = BigDecimal.ZERO;

        public List<String> listNull = null;

        public List<String> listEmpty = new ArrayList<String>();

        public String[] arrayNull = null;

        public String[] arrayEmpty = new String[0];

        public Map<String, String> mapNull = null;

        public Map<String, String> mapEmpty = new HashMap<String, String>();

        public Date dateNull = null;

        public Date dateEpoch = new Date( 0l );

        public Timestamp timestampNull = null;

        public Timestamp timestampEpoch = new Timestamp( 0l );
    }

    public static class BeanJsonIncludeOnProperties {

        @JsonInclude(Include.NON_DEFAULT)
        public String stringEmpty = "";

        @JsonInclude(Include.NON_DEFAULT)
        public Integer integerZero = 0;

        @JsonInclude(Include.NON_EMPTY)
        public List<String> listEmpty = new ArrayList<String>();

        @JsonInclude(Include.NON_NULL)
        public String[] arrayNull = null;

        @JsonInclude(Include.ALWAYS)
        public Map<String, String> mapNull = null;
    }

    public static final JsonIncludeTester INSTANCE = new JsonIncludeTester();

    private JsonIncludeTester() {
    }

    public void testSerializeAlways( ObjectWriterTester<BeanJsonInclude> writer ) {

        String expected = "{" +
                "\"stringNull\":null," +
                "\"stringEmpty\":\"\"," +
                "\"integerNull\":null," +
                "\"integerZero\":0," +
                "\"intDefault\":0," +
                "\"bigIntegerNull\":null," +
                "\"bigIntegerZero\":0," +
                "\"bigDecimalNull\":null," +
                "\"bigDecimalZero\":0," +
                "\"listNull\":null," +
                "\"listEmpty\":[]," +
                "\"arrayNull\":null," +
                "\"arrayEmpty\":[]," +
                "\"mapNull\":null," +
                "\"mapEmpty\":{}," +
                "\"dateNull\":null," +
                "\"dateEpoch\":0," +
                "\"timestampNull\":null," +
                "\"timestampEpoch\":0" +
                "}";
        String result = writer.write( new BeanJsonInclude() );

        assertEquals( expected, result );
    }

    public void testSerializeNonDefault( ObjectWriterTester<BeanJsonInclude> writer ) {

        String expected = "{}";
        String result = writer.write( new BeanJsonInclude() );

        assertEquals( expected, result );
    }

    public void testSerializeNonEmpty( ObjectWriterTester<BeanJsonInclude> writer ) {

        String expected = "{" +
                "\"integerZero\":0," +
                "\"intDefault\":0," +
                "\"bigIntegerZero\":0," +
                "\"bigDecimalZero\":0" +
                "}";
        String result = writer.write( new BeanJsonInclude() );

        assertEquals( expected, result );
    }

    public void testSerializeNonNull( ObjectWriterTester<BeanJsonInclude> writer ) {

        String expected = "{" +
                "\"stringEmpty\":\"\"," +
                "\"integerZero\":0," +
                "\"intDefault\":0," +
                "\"bigIntegerZero\":0," +
                "\"bigDecimalZero\":0," +
                "\"listEmpty\":[]," +
                "\"arrayEmpty\":[]," +
                "\"mapEmpty\":{}," +
                "\"dateEpoch\":0," +
                "\"timestampEpoch\":0" +
                "}";
        String result = writer.write( new BeanJsonInclude() );

        assertEquals( expected, result );
    }

    public void testSerializeProperties( ObjectWriterTester<BeanJsonIncludeOnProperties> writer ) {

        String expected = "{\"mapNull\":null}";
        String result = writer.write( new BeanJsonIncludeOnProperties() );

        assertEquals( expected, result );
    }

}
