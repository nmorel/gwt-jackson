package com.github.nmorel.gwtjackson.client.mapper.number;

import com.github.nmorel.gwtjackson.client.mapper.AbstractJsonMapperTest;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class FloatJsonMapperTest extends AbstractJsonMapperTest<FloatJsonMapper>
{
    @Override
    protected FloatJsonMapper createMapper()
    {
        return new FloatJsonMapper();
    }

    @Override
    protected void testDecodeValue( FloatJsonMapper mapper )
    {
        assertEquals( new Float( "34.10245" ), mapper.decode( "34.10245" ) );
        assertEquals( new Float( "-784.15454" ), mapper.decode( "\"-784.15454\"" ) );
        // the float emulation gives slightly different results => use double!
        if ( !GWT.isProdMode() )
        {
            assertEquals( Float.MIN_VALUE, mapper.decode( "1.4e-45" ) );
            assertEquals( Float.MAX_VALUE, mapper.decode( "3.4028235e38" ) );
        }
    }

    @Override
    protected void testEncodeValue( FloatJsonMapper mapper )
    {
        assertEquals( "34.10245", mapper.encode( new Float( "34.10245" ) ) );
        assertEquals( "-784.15454", mapper.encode( new Float( "-784.15454" ) ) );
        // the float emulation gives slightly different results => use double!
        if ( !GWT.isProdMode() )
        {
            assertEquals( "1.4E-45", mapper.encode( Float.MIN_VALUE ) );
            assertEquals( "3.4028235E38", mapper.encode( Float.MAX_VALUE ) );
        }
    }
}
