package com.github.nmorel.gwtjackson.client.mapper;

/** @author Nicolas Morel */
public class CharacterJsonMapperTest extends AbstractJsonMapperTest<CharacterJsonMapper>
{
    @Override
    protected CharacterJsonMapper createMapper()
    {
        return new CharacterJsonMapper();
    }

    @Override
    protected void testDecodeValue( CharacterJsonMapper mapper )
    {
        assertEquals( (Character) 'e', mapper.decode( "e" ) );
        assertEquals( (Character) '\u00e9', mapper.decode( "\"\u00e9\"" ) );
    }

    @Override
    protected void testEncodeValue( CharacterJsonMapper mapper )
    {
        // we convert character as number
        assertEquals( "" + ((int) 'e'), mapper.encode( 'e' ) );
        assertEquals( "0", mapper.encode( '\u0000' ) );
        assertEquals( "65535", mapper.encode( '\uffff' ) );
    }
}
