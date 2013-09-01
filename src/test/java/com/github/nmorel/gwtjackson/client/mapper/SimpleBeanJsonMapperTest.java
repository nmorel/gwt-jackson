package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.mapper.SimpleBeanJsonMapperTester;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class SimpleBeanJsonMapperTest extends AbstractJsonMapperTest<SimpleBeanJsonMapperTest.SimpleBeanMapper>
{
    public static interface SimpleBeanMapper extends JsonMapper<SimpleBean>
    {
        static SimpleBeanMapper INSTANCE = GWT.create( SimpleBeanMapper.class );
    }

    private SimpleBeanJsonMapperTester tester = SimpleBeanJsonMapperTester.INSTANCE;

    @Override
    protected SimpleBeanMapper createMapper()
    {
        return SimpleBeanMapper.INSTANCE;
    }

    @Override
    protected void testEncodeValue( SimpleBeanMapper mapper )
    {
        tester.testEncodeValue( createEncoder( createMapper() ) );
    }

    @Override
    protected void testDecodeValue( SimpleBeanMapper mapper )
    {
        tester.testDecodeValue( createDecoder( createMapper() ) );
    }

    public void testWriteBeanWithNullProperties()
    {
        tester.testWriteWithNullProperties( createEncoder( createMapper() ) );
    }
}
