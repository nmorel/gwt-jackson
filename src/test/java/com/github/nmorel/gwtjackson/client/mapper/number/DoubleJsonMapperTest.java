package com.github.nmorel.gwtjackson.client.mapper.number;

import com.github.nmorel.gwtjackson.client.mapper.AbstractJsonMapperTest;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class DoubleJsonMapperTest extends AbstractJsonMapperTest<DoubleJsonMapper>
{
    @Override
    protected DoubleJsonMapper createMapper()
    {
        return new DoubleJsonMapper();
    }

    @Override
    protected void testDecodeValue( DoubleJsonMapper mapper )
    {
        assertEquals( 34.100247d, mapper.decode( "34.100247" ) );
        assertEquals( -487.15487d, mapper.decode( "-487.15487" ) );
        assertEquals( -784.15454d, mapper.decode( "\"-784.15454\"" ) );
        assertEquals( Double.MIN_VALUE, mapper.decode( "4.9E-324" ) );
        assertEquals( Double.MAX_VALUE, mapper.decode( "1.7976931348623157e+308" ) );
    }

    @Override
    protected void testEncodeValue( DoubleJsonMapper mapper )
    {
        assertEquals( "34.100247", mapper.encode( 34.100247 ) );
        assertEquals( "-784.15454", mapper.encode( -784.15454d ) );
        assertEquals( (GWT.isProdMode() ? "5e-324" : "4.9E-324"), mapper.encode( Double.MIN_VALUE ) );
        assertEquals( (GWT.isProdMode() ? "1.7976931348623157e+308" : "1.7976931348623157E308"), mapper.encode( Double.MAX_VALUE ) );
    }
}
