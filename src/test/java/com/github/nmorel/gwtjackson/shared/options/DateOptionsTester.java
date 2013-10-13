package com.github.nmorel.gwtjackson.shared.options;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class DateOptionsTester extends AbstractTester {

    public static class BeanWithDates {

        public Date date;

        public java.sql.Date sqlDate;

        public Time sqlTime;

        public Timestamp sqlTimestamp;

        public Map<Date, String> mapDate;

        public Map<java.sql.Date, String> mapSqlDate;

        public Map<Time, String> mapSqlTime;

        public Map<Timestamp, String> mapSqlTimestamp;
    }

    public static final DateOptionsTester INSTANCE = new DateOptionsTester();

    private DateOptionsTester() {
    }

    public void testSerializeDatesAsTimestamps( ObjectWriterTester<BeanWithDates> writer ) {
        BeanWithDates bean = new BeanWithDates();
        bean.date = new Date( 1345304756540l );
        bean.sqlDate = new java.sql.Date( 1345304756541l );
        bean.sqlTime = new Time( 1345304756542l );
        bean.sqlTimestamp = new Timestamp( 1345304756543l );

        Map<Date, String> mapDate = new HashMap<Date, String>();
        mapDate.put( new Date( 1345304756544l ), "java.util.Date" );
        bean.mapDate = mapDate;

        Map<java.sql.Date, String> mapSqlDate = new HashMap<java.sql.Date, String>();
        mapSqlDate.put( new java.sql.Date( 1345304756545l ), "java.sql.Date" );
        bean.mapSqlDate = mapSqlDate;

        Map<Time, String> mapSqlTime = new HashMap<Time, String>();
        mapSqlTime.put( new Time( 1345304756546l ), "java.sql.Time" );
        bean.mapSqlTime = mapSqlTime;

        Map<Timestamp, String> mapSqlTimestamp = new HashMap<Timestamp, String>();
        mapSqlTimestamp.put( new Timestamp( 1345304756547l ), "java.sql.Timestamp" );
        bean.mapSqlTimestamp = mapSqlTimestamp;

        String expected = "{" +
            "\"date\":1345304756540," +
            "\"sqlDate\":\"" + bean.sqlDate.toString() + "\"," +
            "\"sqlTime\":\"" + bean.sqlTime.toString() + "\"," +
            "\"sqlTimestamp\":1345304756543," +
            "\"mapDate\":{\"1345304756544\":\"java.util.Date\"}," +
            "\"mapSqlDate\":{\"1345304756545\":\"java.sql.Date\"}," +
            "\"mapSqlTime\":{\"1345304756546\":\"java.sql.Time\"}," +
            "\"mapSqlTimestamp\":{\"1345304756547\":\"java.sql.Timestamp\"}" +
            "}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeDatesAsTimestamps( ObjectReaderTester<BeanWithDates> reader ) {

        String input = "{" +
            "\"date\":1345304756540," +
            "\"sqlDate\":\"2012-08-18\"," +
            "\"sqlTime\":\"15:45:56\"," +
            "\"sqlTimestamp\":1345304756543," +
            "\"mapDate\":{\"1345304756544\":\"java.util.Date\"}" +
            "}";

        BeanWithDates bean = reader.read( input );
        assertEquals( new Date( 1345304756540l ), bean.date );
        assertEquals( getUTCTime( 2012, 8, 18, 0, 0, 0, 0 ), bean.sqlDate.getTime() );
        assertEquals( new Time( 15, 45, 56 ), bean.sqlTime );
        assertEquals( new java.sql.Timestamp( 1345304756543l ), bean.sqlTimestamp );

        Map<Date, String> mapDate = new HashMap<Date, String>();
        mapDate.put( new Date( 1345304756544l ), "java.util.Date" );
        assertEquals( mapDate, bean.mapDate );

        // Jackson is not able to deserialize java.sql.* types as string so we don't bother testing it
    }

    public void testSerializeDatesNotAsTimestamps( ObjectWriterTester<BeanWithDates> writer ) {
        BeanWithDates bean = new BeanWithDates();
        bean.date = getUTCDate( 2012, 8, 18, 15, 45, 56, 540 );
        bean.sqlDate = new java.sql.Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 541 ) );
        bean.sqlTime = new java.sql.Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 542 ) );
        bean.sqlTimestamp = new java.sql.Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) );

        Map<Date, String> mapDate = new HashMap<Date, String>();
        mapDate.put( getUTCDate( 2012, 8, 18, 15, 45, 56, 544 ), "java.util.Date" );
        bean.mapDate = mapDate;

        Map<java.sql.Date, String> mapSqlDate = new HashMap<java.sql.Date, String>();
        mapSqlDate.put( new java.sql.Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 545 ) ), "java.sql.Date" );
        bean.mapSqlDate = mapSqlDate;

        Map<Time, String> mapSqlTime = new HashMap<Time, String>();
        mapSqlTime.put( new java.sql.Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 546 ) ), "java.sql.Time" );
        bean.mapSqlTime = mapSqlTime;

        Map<Timestamp, String> mapSqlTimestamp = new HashMap<Timestamp, String>();
        mapSqlTimestamp.put( new java.sql.Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 547 ) ), "java.sql.Timestamp" );
        bean.mapSqlTimestamp = mapSqlTimestamp;

        String expected = "{" +
            "\"date\":\"2012-08-18T15:45:56.540+0000\"," +
            "\"sqlDate\":\"" + bean.sqlDate.toString() + "\"," +
            "\"sqlTime\":\"" + bean.sqlTime.toString() + "\"," +
            "\"sqlTimestamp\":\"2012-08-18T15:45:56.543+0000\"," +
            "\"mapDate\":{\"2012-08-18T15:45:56.544+0000\":\"java.util.Date\"}," +
            "\"mapSqlDate\":{\"2012-08-18T15:45:56.545+0000\":\"java.sql.Date\"}," +
            "\"mapSqlTime\":{\"2012-08-18T15:45:56.546+0000\":\"java.sql.Time\"}," +
            "\"mapSqlTimestamp\":{\"2012-08-18T15:45:56.547+0000\":\"java.sql.Timestamp\"}" +
            "}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeDatesNotAsTimestamps( ObjectReaderTester<BeanWithDates> reader ) {

        String input = "{" +
            "\"date\":\"2012-08-18T15:45:56.540+0000\"," +
            "\"sqlDate\":\"2012-08-18\"," +
            "\"sqlTime\":\"15:45:56\"," +
            "\"sqlTimestamp\":\"2012-08-18T15:45:56.543+0000\"," +
            "\"mapDate\":{\"2012-08-18T15:45:56.544+0000\":\"java.util.Date\"}" +
            "}";

        BeanWithDates bean = reader.read( input );
        assertEquals( new Date( 1345304756540l ), bean.date );
        assertEquals( getUTCTime( 2012, 8, 18, 0, 0, 0, 0 ), bean.sqlDate.getTime() );
        assertEquals( new Time( 15, 45, 56 ), bean.sqlTime );
        assertEquals( new java.sql.Timestamp( 1345304756543l ), bean.sqlTimestamp );

        Map<Date, String> mapDate = new HashMap<Date, String>();
        mapDate.put( new Date( 1345304756544l ), "java.util.Date" );
        assertEquals( mapDate, bean.mapDate );

        // Jackson is not able to deserialize java.sql.* types as string so we don't bother testing it
    }

}
