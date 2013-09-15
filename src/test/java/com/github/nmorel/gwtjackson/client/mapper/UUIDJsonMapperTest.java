package com.github.nmorel.gwtjackson.client.mapper;

import java.util.UUID;

/** @author Nicolas Morel */
public class UUIDJsonMapperTest extends AbstractJsonMapperTest<UUIDJsonMapper>
{
    private static String uuid = "550e8400-e29b-41d4-a716-446655440000";

    @Override
    protected UUIDJsonMapper createMapper()
    {
        return new UUIDJsonMapper();
    }

    @Override
    protected void testDecodeValue( UUIDJsonMapper mapper )
    {
        assertEquals( UUID.fromString( uuid ), mapper.decode( "\"" + uuid + "\"" ) );
    }

    @Override
    protected void testEncodeValue( UUIDJsonMapper mapper )
    {
        assertEquals( "\"" + uuid + "\"", mapper.encode( UUID.fromString( uuid ) ) );
    }
}
