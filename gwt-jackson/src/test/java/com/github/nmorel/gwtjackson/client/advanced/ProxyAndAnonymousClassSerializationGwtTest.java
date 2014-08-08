package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 */
public class ProxyAndAnonymousClassSerializationGwtTest extends GwtJacksonTestCase {

    // RequestFactory proxy

    public static class RfBean {

        private int id;

        private String name;

        public int getId() {
            return id;
        }

        public void setId( int id ) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }
    }

    @ProxyFor( RfBean.class )
    public static interface RfBeanProxy extends ValueProxy {

        int getId();

        void setId( int id );

        String getName();

        void setName( String name );
    }

    public static class RfBeanService {

        public void save( RfBean bean ) {

        }
    }

    @Service( RfBeanService.class )
    public static interface ServiceContext extends RequestContext {

        Request<Void> save( RfBeanProxy bean );
    }

    public static interface TestRequestFactory extends RequestFactory {

        ServiceContext serviceContext();
    }

    public static interface RfBeanProxyWriter extends ObjectWriter<RfBeanProxy> {}

    public void testSerializationProxy() {
        TestRequestFactory requestFactory = GWT.create( TestRequestFactory.class );
        requestFactory.initialize( new SimpleEventBus() );

        ServiceContext serviceContext = requestFactory.serviceContext();
        RfBeanProxy proxy = serviceContext.create( RfBeanProxy.class );
        proxy.setId( 54 );
        proxy.setName( "Toto" );

        RfBeanProxyWriter writer = GWT.create( RfBeanProxyWriter.class );
        String json = writer.write( proxy );
        assertEquals( "{\"id\":54,\"name\":\"Toto\"}", json );
    }

    //####### Anonymous class

    public static abstract class AbstractBean {

        public abstract int getId();

        public abstract String getName();
    }

    public static interface AbstractBeanWriter extends ObjectWriter<AbstractBean> {}

    public void testSerializationAnonymousClass() {
        AbstractBeanWriter writer = GWT.create( AbstractBeanWriter.class );
        String json = writer.write( new AbstractBean() {
            @Override
            public int getId() {
                return 54;
            }

            @Override
            public String getName() {
                return "Toto";
            }
        } );
        assertEquals( "{\"id\":54,\"name\":\"Toto\"}", json );
    }

}
