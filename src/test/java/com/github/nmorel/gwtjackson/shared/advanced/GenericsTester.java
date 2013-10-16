package com.github.nmorel.gwtjackson.shared.advanced;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class GenericsTester extends AbstractTester {

    @JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "name" )
    public static class GenericOneType<T> {

        public String name;

        public T genericValue;

        public List<T> listWithGenericValues;
    }

    public static class GenericTwoType<T, V> {

        public T firstType;

        public V secondType;

        public List<T> listFirstType;

        public LinkedHashSet<V> setSecondType;
    }

    public static final GenericsTester INSTANCE = new GenericsTester();

    private GenericsTester() {
    }

    public void testSerializeString( ObjectWriterTester<GenericOneType<String>> writer ) {
        GenericOneType<String> bean = new GenericOneType<String>();
        bean.name = "generic";
        bean.genericValue = "value";
        bean.listWithGenericValues = Arrays.asList( "Hello", "World" );

        String expected = "{" +
            "\"name\":\"generic\"," +
            "\"genericValue\":\"value\"," +
            "\"listWithGenericValues\":[\"Hello\",\"World\"]" +
            "}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeString( ObjectReaderTester<GenericOneType<String>> reader ) {
        String input = "{" +
            "\"name\":\"generic\"," +
            "\"genericValue\":\"value\"," +
            "\"listWithGenericValues\":[\"Hello\",\"World\"]" +
            "}";

        GenericOneType<String> bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( "generic", bean.name );
        assertEquals( "value", bean.genericValue );
        assertEquals( Arrays.asList( "Hello", "World" ), bean.listWithGenericValues );
    }

    public void testSerializeStringString( ObjectWriterTester<GenericTwoType<String, String>> writer ) {
        GenericTwoType<String, String> bean = new GenericTwoType<String, String>();
        bean.firstType = "first";
        bean.secondType = "second";
        bean.listFirstType = Arrays.asList( "Hello", "World" );
        bean.setSecondType = new LinkedHashSet<String>( Arrays.asList( "Bonjour", "le", "monde" ) );

        String expected = "{" +
            "\"firstType\":\"first\"," +
            "\"secondType\":\"second\"," +
            "\"listFirstType\":[\"Hello\",\"World\"]," +
            "\"setSecondType\":[\"Bonjour\",\"le\",\"monde\"]" +
            "}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeStringString( ObjectReaderTester<GenericTwoType<String, String>> reader ) {
        String input = "{" +
            "\"firstType\":\"first\"," +
            "\"secondType\":\"second\"," +
            "\"listFirstType\":[\"Hello\",\"World\"]," +
            "\"setSecondType\":[\"Bonjour\",\"le\",\"monde\"]" +
            "}";

        GenericTwoType<String, String> bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( "first", bean.firstType );
        assertEquals( "second", bean.secondType );
        assertEquals( Arrays.asList( "Hello", "World" ), bean.listFirstType );
        assertEquals( new LinkedHashSet<String>( Arrays.asList( "Bonjour", "le", "monde" ) ), bean.setSecondType );
    }

    public void testSerializeIntegerString( ObjectWriterTester<GenericTwoType<Integer, String>> writer ) {
        GenericTwoType<Integer, String> bean = new GenericTwoType<Integer, String>();
        bean.firstType = 1;
        bean.secondType = "second";
        bean.listFirstType = Arrays.asList( 1, 2 );
        bean.setSecondType = new LinkedHashSet<String>( Arrays.asList( "Bonjour", "le", "monde" ) );

        String expected = "{" +
            "\"firstType\":1," +
            "\"secondType\":\"second\"," +
            "\"listFirstType\":[1,2]," +
            "\"setSecondType\":[\"Bonjour\",\"le\",\"monde\"]" +
            "}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeIntegerString( ObjectReaderTester<GenericTwoType<Integer, String>> reader ) {
        String input = "{" +
            "\"firstType\":1," +
            "\"secondType\":\"second\"," +
            "\"listFirstType\":[1,2]," +
            "\"setSecondType\":[\"Bonjour\",\"le\",\"monde\"]" +
            "}";

        GenericTwoType<Integer, String> bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( 1, (int) bean.firstType );
        assertEquals( "second", bean.secondType );
        assertEquals( Arrays.asList( 1, 2 ), bean.listFirstType );
        assertEquals( new LinkedHashSet<String>( Arrays.asList( "Bonjour", "le", "monde" ) ), bean.setSecondType );
    }

    public void testSerializeIntegerGenericString( ObjectWriterTester<GenericTwoType<Integer, GenericOneType<String>>> writer ) {
        GenericOneType<String> gen1 = new GenericOneType<String>();
        gen1.name = "generic1";
        gen1.genericValue = "value1";
        gen1.listWithGenericValues = Arrays.asList( "Hello", "World" );

        GenericOneType<String> gen2 = new GenericOneType<String>();
        gen2.name = "generic2";
        gen2.genericValue = "value2";
        gen2.listWithGenericValues = Arrays.asList( "I", "Am", "2" );

        GenericTwoType<Integer, GenericOneType<String>> bean = new GenericTwoType<Integer, GenericOneType<String>>();
        bean.firstType = 1;
        bean.secondType = gen1;
        bean.listFirstType = Arrays.asList( 1, 2 );
        bean.setSecondType = new LinkedHashSet<GenericOneType<String>>();
        bean.setSecondType.add( gen2 );
        bean.setSecondType.add( gen1 );

        String expected = "{" +
            "\"firstType\":1," +
            "\"secondType\":{" +
            "\"name\":\"generic1\"," +
            "\"genericValue\":\"value1\"," +
            "\"listWithGenericValues\":[\"Hello\",\"World\"]" +
            "}," +
            "\"listFirstType\":[1,2]," +
            "\"setSecondType\":[" +
            "{" +
            "\"name\":\"generic2\"," +
            "\"genericValue\":\"value2\"," +
            "\"listWithGenericValues\":[\"I\",\"Am\",\"2\"]" +
            "}," +
            "\"generic1\"]" +
            "}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeIntegerGenericString( ObjectReaderTester<GenericTwoType<Integer, GenericOneType<String>>> reader ) {
        String input = "{" +
            "\"firstType\":1," +
            "\"secondType\":{" +
            "\"name\":\"generic1\"," +
            "\"genericValue\":\"value1\"," +
            "\"listWithGenericValues\":[\"Hello\",\"World\"]" +
            "}," +
            "\"listFirstType\":[1,2]," +
            "\"setSecondType\":[" +
            "{" +
            "\"name\":\"generic2\"," +
            "\"genericValue\":\"value2\"," +
            "\"listWithGenericValues\":[\"I\",\"Am\",\"2\"]" +
            "}," +
            "\"generic1\"]" +
            "}";

        GenericTwoType<Integer, GenericOneType<String>> bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( 1, (int) bean.firstType );

        assertEquals( "generic1", bean.secondType.name );
        assertEquals( "value1", bean.secondType.genericValue );
        assertEquals( Arrays.asList( "Hello", "World" ), bean.secondType.listWithGenericValues );

        assertEquals( Arrays.asList( 1, 2 ), bean.listFirstType );

        Iterator<GenericOneType<String>> iterator = bean.setSecondType.iterator();

        GenericOneType<String> gen2 = iterator.next();
        assertEquals( "generic2", gen2.name );
        assertEquals( "value2", gen2.genericValue );
        assertEquals( Arrays.asList( "I", "Am", "2" ), gen2.listWithGenericValues );

        GenericOneType<String> gen1 = iterator.next();
        assertSame( bean.secondType, gen1 );
    }

}
