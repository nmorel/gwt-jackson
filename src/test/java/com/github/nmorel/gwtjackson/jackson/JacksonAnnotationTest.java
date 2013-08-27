package com.github.nmorel.gwtjackson.jackson;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/** @author Nicolas Morel */
public class JacksonAnnotationTest
{
    public static class Bean
    {
        @JsonProperty(value="tata")
        private String toto;

        public String getToto()
        {
            return toto;
        }

        public void setToto( String toto )
        {
            this.toto = toto;
        }

        @Override
        public String toString()
        {
            return "Bean{" +
                "toto='" + toto + '\'' +
                '}';
        }
    }

    @Test
    public void test() throws IOException
    {
        Bean bean = new Bean();
        bean.setToto( "ohoh" );

        ObjectMapper mapper = new ObjectMapper();

        String encoded = mapper.writeValueAsString( bean );
        System.out.println(encoded);

        System.out.println(mapper.readValue( encoded, Bean.class ));
        System.out.println(mapper.readValue( "{\"toto\":\"ohoh\"}", Bean.class ));
    }

    @Test
    public void substring(){
        System.out.println("1234".substring( 3, 4 ));
        System.out.println("1234".substring( 4 ));
    }
}
