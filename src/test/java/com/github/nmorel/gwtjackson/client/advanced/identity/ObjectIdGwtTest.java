package com.github.nmorel.gwtjackson.client.advanced.identity;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Company;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Wrapper;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class ObjectIdGwtTest extends GwtJacksonTestCase
{
    public interface WrapperMapper extends JsonMapper<Wrapper>, JsonMapperTester<Wrapper>
    {
        static WrapperMapper INSTANCE = GWT.create( WrapperMapper.class );
    }

    public interface CompanyMapper extends JsonMapper<Company>, JsonMapperTester<Company>
    {
        static CompanyMapper INSTANCE = GWT.create( CompanyMapper.class );
    }

    private ObjectIdTester tester = ObjectIdTester.INSTANCE;

    public void testColumnMetadata()
    {
        tester.testColumnMetadata( WrapperMapper.INSTANCE );
    }

    public void testMixedRefsIssue188()
    {
        tester.testMixedRefsIssue188( CompanyMapper.INSTANCE );
    }
}
