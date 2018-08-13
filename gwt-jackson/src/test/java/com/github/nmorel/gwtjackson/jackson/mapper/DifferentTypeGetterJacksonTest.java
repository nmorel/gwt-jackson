package com.github.nmorel.gwtjackson.jackson.mapper;

import org.junit.Test;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.mapper.DifferentTypeGetterTester;
import com.github.nmorel.gwtjackson.shared.mapper.DifferentTypeGetterTester.DifferentTypeGetterBean;

public class DifferentTypeGetterJacksonTest extends AbstractJacksonTest {

	@Test
	public void testDifferentTypeGetter()  {
		DifferentTypeGetterTester.INSTANCE.testDifferentTypeGetterBean( createMapper(DifferentTypeGetterBean.class) );
	}
	
}
