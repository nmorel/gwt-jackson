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

package com.github.nmorel.gwtjackson.client.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.IntegerJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer.ArrayCreator;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveIntegerArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.ListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.IntegerJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.LongJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BooleanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.ArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveBooleanArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveIntegerArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class SingleArrayOptionGwtTest extends GwtJacksonTestCase {

    public interface StringListBeanWriter extends ObjectWriter<StringListBean> {

        static StringListBeanWriter INSTANCE = GWT.create( StringListBeanWriter.class );
    }

    public interface ListXBeanReader extends ObjectReader<List<XBean>> {

        static ListXBeanReader INSTANCE = GWT.create( ListXBeanReader.class );
    }

    public interface ArrayXBeanReader extends ObjectReader<XBean[]> {

        static ArrayXBeanReader INSTANCE = GWT.create( ArrayXBeanReader.class );
    }

    static class StringListBean {

        @SuppressWarnings( "unused" )
        public Collection<String> values;

        public StringListBean( Collection<String> v ) { values = v; }

    }

    static class XBean {

        public int x;
    }

    private JsonSerializationContext createSerializationContext() {
        return new JsonSerializationContext.Builder().writeSingleElemArraysUnwrapped( true ).build();
    }

    public void testSerialize() {
        JsonSerializationContext context = createSerializationContext();

        // Lists:
        ArrayList<String> strs = new ArrayList<String>();
        strs.add( "xyz" );
        JsonWriter writer = context.newJsonWriter();
        IterableJsonSerializer.newInstance( StringJsonSerializer.getInstance() ).serialize( writer, strs, context );
        assertEquals( ("\"xyz\""), writer.getOutput() );

        ArrayList<Integer> ints = new ArrayList<Integer>();
        ints.add( 13 );
        writer = context.newJsonWriter();
        IterableJsonSerializer.newInstance( IntegerJsonSerializer.getInstance() ).serialize( writer, ints, context );
        assertEquals( "13", writer.getOutput() );

        // other Collections, like Sets:
        HashSet<Long> longs = new HashSet<Long>();
        longs.add( 42L );
        writer = context.newJsonWriter();
        IterableJsonSerializer.newInstance( LongJsonSerializer.getInstance() ).serialize( writer, longs, context );
        assertEquals( "42", writer.getOutput() );

        // [Issue#180]
        final String EXP_STRINGS = "{\"values\":\"foo\"}";
        assertEquals( EXP_STRINGS, StringListBeanWriter.INSTANCE.write( new StringListBean( Collections
                .singletonList( "foo" ) ), createSerializationContext() ) );

        final Set<String> SET = new HashSet<String>();
        SET.add( "foo" );
        assertEquals( EXP_STRINGS, StringListBeanWriter.INSTANCE.write( new StringListBean( SET ), createSerializationContext() ) );

        // arrays:
        writer = context.newJsonWriter();
        PrimitiveBooleanArrayJsonSerializer.getInstance().serialize( writer, new boolean[]{true}, context );
        assertEquals( "true", writer.getOutput() );

        writer = context.newJsonWriter();
        ArrayJsonSerializer.newInstance( BooleanJsonSerializer.getInstance() ).serialize( writer, new Boolean[]{Boolean.TRUE}, context );
        assertEquals( "true", writer.getOutput() );

        writer = context.newJsonWriter();
        PrimitiveIntegerArrayJsonSerializer.getInstance().serialize( writer, new int[]{3}, context );
        assertEquals( "3", writer.getOutput() );

        writer = context.newJsonWriter();
        ArrayJsonSerializer.newInstance( StringJsonSerializer.getInstance() ).serialize( writer, new String[]{"foo"}, context );
        assertEquals( "\"foo\"", writer.getOutput() );
    }

    private JsonDeserializationContext createDeserializationContext() {
        return new JsonDeserializationContext.Builder().acceptSingleValueAsArray( true ).build();
    }

    public void testDeserialize() {
        JsonDeserializationContext context = createDeserializationContext();

        // first with simple scalar types (numbers), with collections
        List<Integer> ints = ListJsonDeserializer.newInstance( IntegerJsonDeserializer.getInstance() ).deserialize( context
                .newJsonReader( "4" ), context );
        assertEquals( 1, ints.size() );
        assertEquals( Integer.valueOf( 4 ), ints.get( 0 ) );

        List<String> strings = ListJsonDeserializer.newInstance( StringJsonDeserializer.getInstance() ).deserialize( context
                .newJsonReader( "\"abc\"" ), context );
        assertEquals( 1, strings.size() );
        assertEquals( "abc", strings.get( 0 ) );

        // and arrays:
        int[] intArray = PrimitiveIntegerArrayJsonDeserializer.getInstance().deserialize( context.newJsonReader( "-7" ), context );
        assertEquals( 1, intArray.length );
        assertEquals( -7, intArray[0] );

        String[] stringArray = ArrayJsonDeserializer.newInstance( StringJsonDeserializer.getInstance(), new ArrayCreator<String>() {
            @Override
            public String[] create( int length ) {
                return new String[length];
            }
        } ).deserialize( context.newJsonReader( "\"xyz\"" ), context );
        assertEquals( 1, stringArray.length );
        assertEquals( "xyz", stringArray[0] );

        // and then with Beans:
        List<XBean> xbeanList = ListXBeanReader.INSTANCE.read( "{\"x\":4}", createDeserializationContext() );
        assertEquals( 1, xbeanList.size() );
        assertEquals( XBean.class, xbeanList.get( 0 ).getClass() );

        XBean[] xbeanArray = ArrayXBeanReader.INSTANCE.read( "{\"x\":29}", createDeserializationContext() );
        assertEquals( 1, xbeanArray.length );
        assertEquals( XBean.class, xbeanArray[0].getClass() );
    }

}
