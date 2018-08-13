package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.mapper.DifferentTypeGetterTester;
import com.github.nmorel.gwtjackson.shared.mapper.DifferentTypeGetterTester.DifferentTypeGetterBean;
import com.google.gwt.core.client.GWT;

public class DifferentTypeGetterGwtTest extends GwtJacksonTestCase {
	
	public interface DifferentTypeGetterBeanMapper extends ObjectMapper<DifferentTypeGetterBean>, ObjectMapperTester<DifferentTypeGetterBean> {
        static DifferentTypeGetterBeanMapper INSTANCE = GWT.create( DifferentTypeGetterBeanMapper.class );
    }
	
	public void testDifferentTypeGetter() {
		DifferentTypeGetterTester.INSTANCE.testDifferentTypeGetterBean(DifferentTypeGetterBeanMapper.INSTANCE);
	}
	
}
