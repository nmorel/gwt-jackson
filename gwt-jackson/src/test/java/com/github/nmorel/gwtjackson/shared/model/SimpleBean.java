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

package com.github.nmorel.gwtjackson.shared.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class SimpleBean {

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

    private AnEnum enumProperty;

    private Date date;

    private java.sql.Date sqlDate;

    private Time sqlTime;

    private Timestamp sqlTimestamp;

    private String[] stringArray;

    private boolean[] booleanPrimitiveArray;

    private byte[] bytePrimitiveArray;

    private char[] characterPrimitiveArray;

    private double[] doublePrimitiveArray;

    private float[] floatPrimitiveArray;

    private int[] integerPrimitiveArray;

    private long[] longPrimitiveArray;

    private short[] shortPrimitiveArray;

    public String getString() {
        return string;
    }

    public void setString( String string ) {
        this.string = string;
    }

    public byte getBytePrimitive() {
        return bytePrimitive;
    }

    public void setBytePrimitive( byte bytePrimitive ) {
        this.bytePrimitive = bytePrimitive;
    }

    public Byte getByteBoxed() {
        return byteBoxed;
    }

    public void setByteBoxed( Byte byteBoxed ) {
        this.byteBoxed = byteBoxed;
    }

    public short getShortPrimitive() {
        return shortPrimitive;
    }

    public void setShortPrimitive( short shortPrimitive ) {
        this.shortPrimitive = shortPrimitive;
    }

    public Short getShortBoxed() {
        return shortBoxed;
    }

    public void setShortBoxed( Short shortBoxed ) {
        this.shortBoxed = shortBoxed;
    }

    public int getIntPrimitive() {
        return intPrimitive;
    }

    public void setIntPrimitive( int intPrimitive ) {
        this.intPrimitive = intPrimitive;
    }

    public Integer getIntBoxed() {
        return intBoxed;
    }

    public void setIntBoxed( Integer intBoxed ) {
        this.intBoxed = intBoxed;
    }

    public long getLongPrimitive() {
        return longPrimitive;
    }

    public void setLongPrimitive( long longPrimitive ) {
        this.longPrimitive = longPrimitive;
    }

    public Long getLongBoxed() {
        return longBoxed;
    }

    public void setLongBoxed( Long longBoxed ) {
        this.longBoxed = longBoxed;
    }

    public double getDoublePrimitive() {
        return doublePrimitive;
    }

    public void setDoublePrimitive( double doublePrimitive ) {
        this.doublePrimitive = doublePrimitive;
    }

    public Double getDoubleBoxed() {
        return doubleBoxed;
    }

    public void setDoubleBoxed( Double doubleBoxed ) {
        this.doubleBoxed = doubleBoxed;
    }

    public float getFloatPrimitive() {
        return floatPrimitive;
    }

    public void setFloatPrimitive( float floatPrimitive ) {
        this.floatPrimitive = floatPrimitive;
    }

    public Float getFloatBoxed() {
        return floatBoxed;
    }

    public void setFloatBoxed( Float floatBoxed ) {
        this.floatBoxed = floatBoxed;
    }

    public boolean isBooleanPrimitive() {
        return booleanPrimitive;
    }

    public void setBooleanPrimitive( boolean booleanPrimitive ) {
        this.booleanPrimitive = booleanPrimitive;
    }

    public Boolean getBooleanBoxed() {
        return booleanBoxed;
    }

    public void setBooleanBoxed( Boolean booleanBoxed ) {
        this.booleanBoxed = booleanBoxed;
    }

    public char getCharPrimitive() {
        return charPrimitive;
    }

    public void setCharPrimitive( char charPrimitive ) {
        this.charPrimitive = charPrimitive;
    }

    public Character getCharBoxed() {
        return charBoxed;
    }

    public void setCharBoxed( Character charBoxed ) {
        this.charBoxed = charBoxed;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger( BigInteger bigInteger ) {
        this.bigInteger = bigInteger;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal( BigDecimal bigDecimal ) {
        this.bigDecimal = bigDecimal;
    }

    public AnEnum getEnumProperty() {
        return enumProperty;
    }

    public void setEnumProperty( AnEnum enumProperty ) {
        this.enumProperty = enumProperty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate( java.sql.Date sqlDate ) {
        this.sqlDate = sqlDate;
    }

    public Time getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime( Time sqlTime ) {
        this.sqlTime = sqlTime;
    }

    public Timestamp getSqlTimestamp() {
        return sqlTimestamp;
    }

    public void setSqlTimestamp( Timestamp sqlTimestamp ) {
        this.sqlTimestamp = sqlTimestamp;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray( String[] stringArray ) {
        this.stringArray = stringArray;
    }

    public boolean[] getBooleanPrimitiveArray() {
        return booleanPrimitiveArray;
    }

    public void setBooleanPrimitiveArray( boolean[] booleanPrimitiveArray ) {
        this.booleanPrimitiveArray = booleanPrimitiveArray;
    }

    public byte[] getBytePrimitiveArray() {
        return bytePrimitiveArray;
    }

    public void setBytePrimitiveArray( byte[] bytePrimitiveArray ) {
        this.bytePrimitiveArray = bytePrimitiveArray;
    }

    public char[] getCharacterPrimitiveArray() {
        return characterPrimitiveArray;
    }

    public void setCharacterPrimitiveArray( char[] characterPrimitiveArray ) {
        this.characterPrimitiveArray = characterPrimitiveArray;
    }

    public double[] getDoublePrimitiveArray() {
        return doublePrimitiveArray;
    }

    public void setDoublePrimitiveArray( double[] doublePrimitiveArray ) {
        this.doublePrimitiveArray = doublePrimitiveArray;
    }

    public float[] getFloatPrimitiveArray() {
        return floatPrimitiveArray;
    }

    public void setFloatPrimitiveArray( float[] floatPrimitiveArray ) {
        this.floatPrimitiveArray = floatPrimitiveArray;
    }

    public int[] getIntegerPrimitiveArray() {
        return integerPrimitiveArray;
    }

    public void setIntegerPrimitiveArray( int[] integerPrimitiveArray ) {
        this.integerPrimitiveArray = integerPrimitiveArray;
    }

    public long[] getLongPrimitiveArray() {
        return longPrimitiveArray;
    }

    public void setLongPrimitiveArray( long[] longPrimitiveArray ) {
        this.longPrimitiveArray = longPrimitiveArray;
    }

    public short[] getShortPrimitiveArray() {
        return shortPrimitiveArray;
    }

    public void setShortPrimitiveArray( short[] shortPrimitiveArray ) {
        this.shortPrimitiveArray = shortPrimitiveArray;
    }
}
