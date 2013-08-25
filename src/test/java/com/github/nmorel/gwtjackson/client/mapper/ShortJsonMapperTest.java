package com.github.nmorel.gwtjackson.client.mapper;

/** @author Nicolas Morel */
public class ShortJsonMapperTest extends AbstractJsonMapperTest<ShortJsonMapper>
{
    @Override
    protected ShortJsonMapper createMapper()
    {
        return new ShortJsonMapper();
    }

    @Override
    protected void testDecodeValue( ShortJsonMapper mapper )
    {
        assertEquals( new Short("34"), mapper.decode( "34" ) );
        assertEquals( new Short("-1"), mapper.decode( "\"-1\"" ) );
        assertEquals( (Short) Short.MIN_VALUE, mapper.decode( "-32768" ) );
        assertEquals( (Short) Short.MAX_VALUE, mapper.decode( "32767" ) );
    }

    @Override
    protected void testEncodeValue( ShortJsonMapper mapper )
    {
        assertEquals( "34", mapper.encode( new Short("34") ) );
        assertEquals( "-1", mapper.encode( new Short("-1") ) );
        assertEquals( "-32768", mapper.encode( Short.MIN_VALUE ) );
        assertEquals( "32767", mapper.encode( Short.MAX_VALUE ) );
    }
}
