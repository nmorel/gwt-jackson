package com.github.nmorel.gwtjackson.client.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class SimpleBeanJsonMapperTest extends AbstractJsonMapperTest<SimpleBeanJsonMapperTest.SimpleBeanMapper>
{
    public static enum TestEnum
    {
        A, B, C, D;
    }

    public static interface SimpleBeanMapper extends JsonMapper<SimpleBean>
    {
    }

    public static class SimpleBean
    {
        private String string;
        private byte bytePrimitive;
        private Byte byteBoxed;
        private short shortPrimitive;
        private Short shortBoxed;
        private int intPrimitive;
        private Integer intBoxed;
        private long longPrimitive;
        private Long longBoxed;
        private double doublePrimitive;
        private Double doubleBoxed;
        private float floatPrimitive;
        private Float floatBoxed;
        private boolean booleanPrimitive;
        private Boolean booleanBoxed;
        private char charPrimitive;
        private Character charBoxed;
        private BigInteger bigInteger;
        private BigDecimal bigDecimal;
        private TestEnum enumProperty;
        private java.util.Date date;
        private java.sql.Date sqlDate;
        private java.sql.Time sqlTime;
        private java.sql.Timestamp sqlTimestamp;

        public String getString()
        {
            return string;
        }

        public void setString( String string )
        {
            this.string = string;
        }

        public byte getBytePrimitive()
        {
            return bytePrimitive;
        }

        public void setBytePrimitive( byte bytePrimitive )
        {
            this.bytePrimitive = bytePrimitive;
        }

        public Byte getByteBoxed()
        {
            return byteBoxed;
        }

        public void setByteBoxed( Byte byteBoxed )
        {
            this.byteBoxed = byteBoxed;
        }

        public short getShortPrimitive()
        {
            return shortPrimitive;
        }

        public void setShortPrimitive( short shortPrimitive )
        {
            this.shortPrimitive = shortPrimitive;
        }

        public Short getShortBoxed()
        {
            return shortBoxed;
        }

        public void setShortBoxed( Short shortBoxed )
        {
            this.shortBoxed = shortBoxed;
        }

        public int getIntPrimitive()
        {
            return intPrimitive;
        }

        public void setIntPrimitive( int intPrimitive )
        {
            this.intPrimitive = intPrimitive;
        }

        public Integer getIntBoxed()
        {
            return intBoxed;
        }

        public void setIntBoxed( Integer intBoxed )
        {
            this.intBoxed = intBoxed;
        }

        public long getLongPrimitive()
        {
            return longPrimitive;
        }

        public void setLongPrimitive( long longPrimitive )
        {
            this.longPrimitive = longPrimitive;
        }

        public Long getLongBoxed()
        {
            return longBoxed;
        }

        public void setLongBoxed( Long longBoxed )
        {
            this.longBoxed = longBoxed;
        }

        public double getDoublePrimitive()
        {
            return doublePrimitive;
        }

        public void setDoublePrimitive( double doublePrimitive )
        {
            this.doublePrimitive = doublePrimitive;
        }

        public Double getDoubleBoxed()
        {
            return doubleBoxed;
        }

        public void setDoubleBoxed( Double doubleBoxed )
        {
            this.doubleBoxed = doubleBoxed;
        }

        public float getFloatPrimitive()
        {
            return floatPrimitive;
        }

        public void setFloatPrimitive( float floatPrimitive )
        {
            this.floatPrimitive = floatPrimitive;
        }

        public Float getFloatBoxed()
        {
            return floatBoxed;
        }

        public void setFloatBoxed( Float floatBoxed )
        {
            this.floatBoxed = floatBoxed;
        }

        public boolean isBooleanPrimitive()
        {
            return booleanPrimitive;
        }

        public void setBooleanPrimitive( boolean booleanPrimitive )
        {
            this.booleanPrimitive = booleanPrimitive;
        }

        public Boolean getBooleanBoxed()
        {
            return booleanBoxed;
        }

        public void setBooleanBoxed( Boolean booleanBoxed )
        {
            this.booleanBoxed = booleanBoxed;
        }

        public char getCharPrimitive()
        {
            return charPrimitive;
        }

        public void setCharPrimitive( char charPrimitive )
        {
            this.charPrimitive = charPrimitive;
        }

        public Character getCharBoxed()
        {
            return charBoxed;
        }

        public void setCharBoxed( Character charBoxed )
        {
            this.charBoxed = charBoxed;
        }

        public BigInteger getBigInteger()
        {
            return bigInteger;
        }

        public void setBigInteger( BigInteger bigInteger )
        {
            this.bigInteger = bigInteger;
        }

        public BigDecimal getBigDecimal()
        {
            return bigDecimal;
        }

        public void setBigDecimal( BigDecimal bigDecimal )
        {
            this.bigDecimal = bigDecimal;
        }

        public TestEnum getEnumProperty()
        {
            return enumProperty;
        }

        public void setEnumProperty( TestEnum enumProperty )
        {
            this.enumProperty = enumProperty;
        }

        public Date getDate()
        {
            return date;
        }

        public void setDate( Date date )
        {
            this.date = date;
        }

        public java.sql.Date getSqlDate()
        {
            return sqlDate;
        }

        public void setSqlDate( java.sql.Date sqlDate )
        {
            this.sqlDate = sqlDate;
        }

        public Time getSqlTime()
        {
            return sqlTime;
        }

        public void setSqlTime( Time sqlTime )
        {
            this.sqlTime = sqlTime;
        }

        public Timestamp getSqlTimestamp()
        {
            return sqlTimestamp;
        }

        public void setSqlTimestamp( Timestamp sqlTimestamp )
        {
            this.sqlTimestamp = sqlTimestamp;
        }
    }

    @Override
    protected SimpleBeanMapper createMapper()
    {
        return GWT.create( SimpleBeanMapper.class );
    }

    @Override
    protected void testEncodeValue( SimpleBeanMapper mapper )
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
        bean.setEnumProperty( TestEnum.A );
        bean.setCharPrimitive( '\u00e7' );
        bean.setCharBoxed( '\u00e8' );
        bean.setDate( getUTCDate( 2012, 8, 18, 15, 45, 56, 543 ) );
        bean.setSqlDate( new java.sql.Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 544 ) ) );
        bean.setSqlTime( new java.sql.Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 545 ) ) );
        bean.setSqlTimestamp( new java.sql.Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 546 ) ) );

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
            "\"charPrimitive\":231," +
            "\"charBoxed\":232," +
            "\"bigInteger\":\"123456789098765432345678987654\"," +
            "\"bigDecimal\":\"12345678987654.456789\"," +
            "\"enumProperty\":\"A\"," +
            "\"date\":\"2012-08-18T17:45:56.543+02:00\"," +
            "\"sqlDate\":\"2012-08-18T17:45:56.544+02:00\"," +
            "\"sqlTime\":\"2012-08-18T17:45:56.545+02:00\"," +
            "\"sqlTimestamp\":\"2012-08-18T17:45:56.546+02:00\"}";

        assertEquals( expected, mapper.encode( bean ) );
    }

    @Override
    protected void testDecodeValue( SimpleBeanMapper mapper )
    {
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
            "\"bigInteger\":\"123456789098765432345678987654\"," +
            "\"bigDecimal\":\"12345678987654.456789\"," +
            "\"enumProperty\":\"B\"," +
            "\"date\":\"2012-08-18T17:45:56.543+02:00\"," +
            "\"sqlDate\":\"2012-08-18T17:45:56.544+02:00\"," +
            "\"sqlTime\":\"2012-08-18T17:45:56.545+02:00\"," +
            "\"sqlTimestamp\":\"2012-08-18T17:45:56.546+02:00\"}";

        SimpleBean bean = mapper.decode( input );
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
        assertEquals( TestEnum.B, bean.getEnumProperty() );
        assertEquals( '\u00e7', bean.getCharPrimitive() );
        assertEquals( new Character( '\u00e8' ), bean.getCharBoxed() );
        assertEquals( getUTCDate( 2012, 8, 18, 15, 45, 56, 543 ), bean.getDate() );
        assertEquals( new java.sql.Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 544 ) ), bean.getSqlDate() );
        assertEquals( new java.sql.Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 545 ) ), bean.getSqlTime() );
        assertEquals( new java.sql.Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 546 ) ), bean.getSqlTimestamp() );
    }

    public void testWriteBeanWithNullProperties()
    {
        SimpleBeanMapper mapper = GWT.create( SimpleBeanMapper.class );

        String doubleAndFloatZeroString = GWT.isProdMode() ? "0" : "0.0";

        String expected = "{\"bytePrimitive\":0," +
            "\"shortPrimitive\":0," +
            "\"intPrimitive\":0," +
            "\"longPrimitive\":0," +
            "\"doublePrimitive\":" + doubleAndFloatZeroString + "," +
            "\"floatPrimitive\":" + doubleAndFloatZeroString + "," +
            "\"booleanPrimitive\":false," +
            "\"charPrimitive\":0}";

        assertEquals( expected, mapper.encode( new SimpleBean() ) );
    }
}
