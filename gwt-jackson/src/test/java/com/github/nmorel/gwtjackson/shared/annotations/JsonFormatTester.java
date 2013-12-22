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

package com.github.nmorel.gwtjackson.shared.annotations;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * @author Nicolas Morel
 */
public final class JsonFormatTester extends AbstractTester {

    // currently, jackson does not take JsonFormat on parent into account
    @JsonFormat( pattern = "/yyyy/MM/dd/HH/mm/ss" )
    public static class FormatDateBean {

        @JsonFormat( shape = Shape.STRING, pattern = "/yyyy/MM/dd/" )
        public Date dateString;

        // we asked for a number so we should get the date in milliseconds and the pattern ignored
        @JsonFormat( shape = Shape.NUMBER, pattern = "/yyyy/MM/dd/" )
        public Date dateNumber;

        public Date date;

        @JsonFormat( shape = Shape.STRING, pattern = "/yyyy/MM/dd/" )
        public Timestamp timestampString;

        // we asked for a number so we should get the date in milliseconds and the pattern ignored
        @JsonFormat( shape = Shape.NUMBER, pattern = "/yyyy/MM/dd/" )
        public Timestamp timestampNumber;

        public Timestamp timestamp;
    }

    public static final JsonFormatTester INSTANCE = new JsonFormatTester();

    private JsonFormatTester() {
    }

    public void testFormatDate( ObjectMapperTester<FormatDateBean> mapper ) {
        long millis = getUTCTime( 2013, 12, 25, 0, 0, 0, 0 );
        Date date = new Date( millis );
        Timestamp timestamp = new Timestamp( millis );

        FormatDateBean bean = new FormatDateBean();
        bean.dateString = date;
        bean.dateNumber = date;
        bean.date = date;
        bean.timestampString = timestamp;
        bean.timestampNumber = timestamp;
        bean.timestamp = timestamp;

        String expected = "{" +
            "\"dateString\":\"/2013/12/25/\"," +
            "\"dateNumber\":" + millis + "," +
            "\"date\":\"2013-12-25T00:00:00.000+0000\"," +
            "\"timestampString\":\"/2013/12/25/\"," +
            "\"timestampNumber\":" + millis + "," +
            "\"timestamp\":\"2013-12-25T00:00:00.000+0000\"" +
            "}";
        String result = mapper.write( bean );
        assertEquals( expected, result );

        FormatDateBean actual = mapper.read( expected );
        assertEquals( date, actual.dateString );
        assertEquals( date, actual.dateNumber );
        assertEquals( date, actual.date );
        assertEquals( timestamp, actual.timestampString);
        assertEquals( timestamp, actual.timestampNumber );
        assertEquals( timestamp, actual.timestamp );
    }

}
