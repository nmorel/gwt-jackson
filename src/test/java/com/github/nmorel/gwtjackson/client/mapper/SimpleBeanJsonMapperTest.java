package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.SimpleBean;
import com.github.nmorel.gwtjackson.shared.mapper.SimpleBeanJsonMapperTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class SimpleBeanJsonMapperTest extends AbstractJsonMapperTest<SimpleBeanJsonMapperTest.SimpleBeanMapper>
{
    public static interface SimpleBeanMapper extends JsonMapper<SimpleBean>
    {
    }

    @Override
    protected SimpleBeanMapper createMapper()
    {
        return GWT.create( SimpleBeanMapper.class );
    }

    @Override
    protected void testEncodeValue( SimpleBeanMapper mapper )
    {
        SimpleBeanJsonMapperTester.INSTANCE.testEncodeValue( new JsonEncoderTester<SimpleBean>()
        {
            @Override
            public String encode( SimpleBean input )
            {
                return createMapper().encode( input );
            }
        } );
    }

    @Override
    protected void testDecodeValue( SimpleBeanMapper mapper )
    {
        SimpleBeanJsonMapperTester.INSTANCE.testDecodeValue( new JsonDecoderTester<SimpleBean>()
        {
            @Override
            public SimpleBean decode( String input )
            {
                return createMapper().decode( input );
            }
        } );
    }

    public void testWriteBeanWithNullProperties()
    {
        SimpleBeanJsonMapperTester.INSTANCE.testWriteWithNullProperties( new JsonEncoderTester<SimpleBean>()
        {
            @Override
            public String encode( SimpleBean input )
            {
                return createMapper().encode( input );
            }
        } );
    }
}
