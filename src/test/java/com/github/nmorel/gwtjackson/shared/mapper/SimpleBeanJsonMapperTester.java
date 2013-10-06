package com.github.nmorel.gwtjackson.shared.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.model.AnEnum;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public final class SimpleBeanJsonMapperTester extends AbstractTester {

    public static final SimpleBeanJsonMapperTester INSTANCE = new SimpleBeanJsonMapperTester();

    private SimpleBeanJsonMapperTester() {
    }

    public void testDeserializeValue( ObjectReaderTester<SimpleBean> reader ) {

        String input = "{" +
            "\"string\":\"toto\"," +
            "\"bytePrimitive\":34," +
            "\"byteBoxed\":87," +
            "\"shortPrimitive\":12," +
            "\"shortBoxed\":15," +
            "\"intPrimitive\":234," +
            "\"intBoxed\":456," +
            "\"longPrimitive\":-9223372036854775808," +
            "\"longBoxed\":\"9223372036854775807\"," +
            "\"doublePrimitive\":126.23," +
            "\"doubleBoxed\":1256.98," +
            "\"floatPrimitive\":12.89," +
            "\"floatBoxed\":67.3," +
            "\"booleanPrimitive\":true," +
            "\"booleanBoxed\":\"false\"," +
            "\"charPrimitive\":231," +
            "\"charBoxed\":232," +
            "\"bigInteger\":123456789098765432345678987654," +
            "\"bigDecimal\":\"12345678987654.456789\"," +
            "\"enumProperty\":\"B\"," +
            "\"date\":1345304756543," +
            "\"sqlDate\":\"2012-08-18\"," +
            "\"sqlTime\":\"15:45:56\"," +
            "\"sqlTimestamp\":1345304756546," +
            "\"stringArray\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"booleanPrimitiveArray\":[true, null, false, 1, 0]," +
            "\"bytePrimitiveArray\":\"SGVsbG8=\"," +
            "\"characterPrimitiveArray\":\"çou\"," +
            "\"doublePrimitiveArray\":[45.789,null,5.1024]," +
            "\"floatPrimitiveArray\":[null]," +
            "\"integerPrimitiveArray\":[4,5,6,null,7,8]," +
            "\"longPrimitiveArray\":[9223372036854775807,null,-9223372036854775808]," +
            "\"shortPrimitiveArray\":[9,null,7,8,15]" +
            "}";

        SimpleBean bean = reader.read( input );
        assertNotNull( bean );

        assertEquals( "toto", bean.getString() );
        assertEquals( new Integer( 34 ).byteValue(), bean.getBytePrimitive() );
        assertEquals( new Byte( new Integer( 87 ).byteValue() ), bean.getByteBoxed() );
        assertEquals( new Integer( 12 ).shortValue(), bean.getShortPrimitive() );
        assertEquals( new Short( new Integer( 15 ).shortValue() ), bean.getShortBoxed() );
        assertEquals( 234, bean.getIntPrimitive() );
        assertEquals( new Integer( 456 ), bean.getIntBoxed() );
        assertEquals( Long.MIN_VALUE, bean.getLongPrimitive() );
        assertEquals( new Long( Long.MAX_VALUE ), bean.getLongBoxed() );
        assertEquals( 126.23, bean.getDoublePrimitive() );
        assertEquals( 1256.98, bean.getDoubleBoxed() );
        assertEquals( new Double( 12.89 ).floatValue(), bean.getFloatPrimitive() );
        assertEquals( new Double( 67.3 ).floatValue(), bean.getFloatBoxed() );
        assertEquals( new BigDecimal( "12345678987654.456789" ), bean.getBigDecimal() );
        assertEquals( new BigInteger( "123456789098765432345678987654" ), bean.getBigInteger() );
        assertTrue( bean.isBooleanPrimitive() );
        assertFalse( bean.getBooleanBoxed() );
        assertEquals( AnEnum.B, bean.getEnumProperty() );
        assertEquals( '\u00e7', bean.getCharPrimitive() );
        assertEquals( new Character( '\u00e8' ), bean.getCharBoxed() );
        assertEquals( new Date( 1345304756543l ), bean.getDate() );
        assertEquals( getUTCTime( 2012, 8, 18, 0, 0, 0, 0 ), bean.getSqlDate().getTime() );
        assertEquals( new Time( 15, 45, 56 ), bean.getSqlTime() );
        assertEquals( new java.sql.Timestamp( 1345304756546l ), bean.getSqlTimestamp() );
        assertTrue( Arrays.deepEquals( new String[]{"Hello", null, "World", "!"}, bean.getStringArray() ) );
        assertTrue( Arrays.equals( new boolean[]{true, false, false, true, false}, bean.getBooleanPrimitiveArray() ) );
        assertTrue( Arrays.equals( "Hello".getBytes(), bean.getBytePrimitiveArray() ) );
        assertTrue( Arrays.equals( new char[]{'\u00e7', 'o', 'u'}, bean.getCharacterPrimitiveArray() ) );
        assertTrue( Arrays.equals( new double[]{45.789, 0d, 5.1024}, bean.getDoublePrimitiveArray() ) );
        assertTrue( Arrays.equals( new float[]{0f}, bean.getFloatPrimitiveArray() ) );
        assertTrue( Arrays.equals( new int[]{4, 5, 6, 0, 7, 8}, bean.getIntegerPrimitiveArray() ) );
        assertTrue( Arrays.equals( new long[]{Long.MAX_VALUE, 0l, Long.MIN_VALUE}, bean.getLongPrimitiveArray() ) );
        assertTrue( Arrays.equals( new short[]{9, 0, 7, 8, 15}, bean.getShortPrimitiveArray() ) );
    }

    public void testSerializeValue( ObjectWriterTester<SimpleBean> writer ) {
        SimpleBean bean = new SimpleBean();
        bean.setString( "toto" );
        bean.setBytePrimitive( new Integer( 34 ).byteValue() );
        bean.setByteBoxed( new Integer( 87 ).byteValue() );
        bean.setShortPrimitive( new Integer( 12 ).shortValue() );
        bean.setShortBoxed( new Integer( 15 ).shortValue() );
        bean.setIntPrimitive( 234 );
        bean.setIntBoxed( 456 );
        bean.setLongPrimitive( Long.MIN_VALUE );
        bean.setLongBoxed( Long.MAX_VALUE );
        bean.setDoublePrimitive( 126.23 );
        bean.setDoubleBoxed( 1256.98 );
        bean.setFloatPrimitive( new Double( 12.89 ).floatValue() );
        bean.setFloatBoxed( new Double( 67.3 ).floatValue() );
        bean.setBigDecimal( new BigDecimal( "12345678987654.456789" ) );
        bean.setBigInteger( new BigInteger( "123456789098765432345678987654" ) );
        bean.setBooleanPrimitive( true );
        bean.setBooleanBoxed( false );
        bean.setEnumProperty( AnEnum.A );
        bean.setCharPrimitive( '\u00e7' );
        bean.setCharBoxed( '\u00e8' );
        bean.setDate( getUTCDate( 2012, 8, 18, 15, 45, 56, 543 ) );
        bean.setSqlDate( new java.sql.Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 544 ) ) );
        bean.setSqlTime( new java.sql.Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 545 ) ) );
        bean.setSqlTimestamp( new java.sql.Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 546 ) ) );
        bean.setStringArray( new String[]{"Hello", "World", "!"} );
        bean.setBooleanPrimitiveArray( new boolean[]{true, false, true, false} );
        bean.setBytePrimitiveArray( "Hello".getBytes() );
        bean.setCharacterPrimitiveArray( new char[]{'\u00e7', 'o', 'u'} );
        bean.setDoublePrimitiveArray( new double[]{45.789, 5.1024} );
        bean.setFloatPrimitiveArray( new float[]{} );
        bean.setIntegerPrimitiveArray( new int[]{4, 5, 6, 7, 8} );
        bean.setLongPrimitiveArray( new long[]{Long.MAX_VALUE, Long.MIN_VALUE} );
        bean.setShortPrimitiveArray( new short[]{9, 7, 8, 15} );

        String expected = "{" +
            "\"string\":\"toto\"," +
            "\"bytePrimitive\":34," +
            "\"byteBoxed\":87," +
            "\"shortPrimitive\":12," +
            "\"shortBoxed\":15," +
            "\"intPrimitive\":234," +
            "\"intBoxed\":456," +
            "\"longPrimitive\":-9223372036854775808," +
            "\"longBoxed\":9223372036854775807," +
            "\"doublePrimitive\":126.23," +
            "\"doubleBoxed\":1256.98," +
            "\"floatPrimitive\":12.89," +
            "\"floatBoxed\":67.3," +
            "\"booleanPrimitive\":true," +
            "\"booleanBoxed\":false," +
            "\"charPrimitive\":\"ç\"," +
            "\"charBoxed\":\"è\"," +
            "\"bigInteger\":123456789098765432345678987654," +
            "\"bigDecimal\":12345678987654.456789," +
            "\"enumProperty\":\"A\"," +
            "\"date\":1345304756543," +
            "\"sqlDate\":\"2012-08-18\"," +
            "\"sqlTime\":\"" + bean.getSqlTime().toString() + "\"," +
            "\"sqlTimestamp\":1345304756546," +
            "\"stringArray\":[\"Hello\",\"World\",\"!\"]," +
            "\"booleanPrimitiveArray\":[true,false,true,false]," +
            "\"bytePrimitiveArray\":\"SGVsbG8=\"," +
            "\"characterPrimitiveArray\":\"çou\"," +
            "\"doublePrimitiveArray\":[45.789,5.1024]," +
            "\"floatPrimitiveArray\":[]," +
            "\"integerPrimitiveArray\":[4,5,6,7,8]," +
            "\"longPrimitiveArray\":[9223372036854775807,-9223372036854775808]," +
            "\"shortPrimitiveArray\":[9,7,8,15]" +
            "}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testWriteWithNullProperties( ObjectWriterTester<SimpleBean> writer ) {
        String doubleAndFloatZeroString = GWT.isProdMode() ? "0" : "0.0";

        String expected = "{\"bytePrimitive\":0," +
            "\"shortPrimitive\":0," +
            "\"intPrimitive\":0," +
            "\"longPrimitive\":0," +
            "\"doublePrimitive\":" + doubleAndFloatZeroString + "," +
            "\"floatPrimitive\":" + doubleAndFloatZeroString + "," +
            "\"booleanPrimitive\":false," +
            "\"charPrimitive\":\"\\u0000\"}";

        assertEquals( expected, writer.write( new SimpleBean() ) );
    }
}
