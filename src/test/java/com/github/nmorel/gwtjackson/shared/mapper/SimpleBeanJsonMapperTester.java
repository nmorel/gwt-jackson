package com.github.nmorel.gwtjackson.shared.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.model.AnEnum;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public final class SimpleBeanJsonMapperTester extends AbstractTester
{
    public static final SimpleBeanJsonMapperTester INSTANCE = new SimpleBeanJsonMapperTester();

    private SimpleBeanJsonMapperTester()
    {
    }

    public void testDecodeValue( JsonDecoderTester<SimpleBean> decoder )
    {
        java.sql.Time time = new java.sql.Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 545 ) );

        String input = "{\"string\":\"toto\"," +
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
            "\"sqlTime\":\"" + time.toString() + "\"," +
            "\"sqlTimestamp\":1345304756546," +
            "\"integerArray\":[1,2,3,4]}";

        SimpleBean bean = decoder.decode( input );
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
        assertEquals( getUTCDate( 2012, 8, 18, 15, 45, 56, 543 ), bean.getDate() );
        assertEquals( new java.sql.Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 544 ) ).toString(), bean.getSqlDate().toString() );
        assertEquals( time.toString(), bean.getSqlTime().toString() );
        assertEquals( new java.sql.Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 546 ) ), bean.getSqlTimestamp() );
        assertTrue( Arrays.deepEquals( new Integer[]{1, 2, 3, 4}, bean.getIntegerArray() ) );
    }

    public void testEncodeValue( JsonEncoderTester<SimpleBean> encoder )
    {
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
        bean.setIntegerArray( new Integer[]{1, 2, 3, 4} );

        String expected = "{\"string\":\"toto\"," +
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
            "\"integerArray\":[1,2,3,4]}";

        assertEquals( expected, encoder.encode( bean ) );
    }

    public void testWriteWithNullProperties( JsonEncoderTester<SimpleBean> encoder )
    {
        String doubleAndFloatZeroString = GWT.isProdMode() ? "0" : "0.0";

        String expected = "{\"bytePrimitive\":0," +
            "\"shortPrimitive\":0," +
            "\"intPrimitive\":0," +
            "\"longPrimitive\":0," +
            "\"doublePrimitive\":" + doubleAndFloatZeroString + "," +
            "\"floatPrimitive\":" + doubleAndFloatZeroString + "," +
            "\"booleanPrimitive\":false," +
            "\"charPrimitive\":\"\\u0000\"}";

        assertEquals( expected, encoder.encode( new SimpleBean() ) );
    }
}
