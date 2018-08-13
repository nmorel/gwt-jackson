package com.github.nmorel.gwtjackson.shared.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

public class DifferentTypeGetterTester extends AbstractTester {
	
	// Although the example getter and setter are unlikely,
	// it is nice to be able to have them use different types than the field.
	// It allows users to have getters that return Optional<T> of nullable fields of type T.
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class DifferentTypeGetterBean {
		public boolean fieldWithOtherTypeAccessor;
		
		public String getFieldWithOtherTypeAccessor() {
			return fieldWithOtherTypeAccessor ? "YES" : "NO";
		}
		
		public void setFieldWithOtherTypeAccessor(String fieldWithOtherTypeAccessor) {
			this.fieldWithOtherTypeAccessor = "YES".equals(fieldWithOtherTypeAccessor);
		}
	}
	
	public static DifferentTypeGetterTester INSTANCE = new DifferentTypeGetterTester();
	
	private DifferentTypeGetterTester() {}
	
	public void testDifferentTypeGetterBean(ObjectMapperTester<DifferentTypeGetterBean> mapper) {
		// This test is actually not that interesting. 
		// Actual test is the generation of Serializer/Deserializer of DifferentTypeGetterBean.
		String input = "{\"fieldWithOtherTypeAccessor\":\"true\"}";
		DifferentTypeGetterBean bean = mapper.read(input);
		assertEquals( "YES", bean.getFieldWithOtherTypeAccessor() );
		assertEquals( "{\"fieldWithOtherTypeAccessor\":true}", mapper.write(bean) );
		
		bean.setFieldWithOtherTypeAccessor("NO");
		assertEquals( "NO", bean.getFieldWithOtherTypeAccessor() );
		assertEquals( "{\"fieldWithOtherTypeAccessor\":false}", mapper.write(bean) );
	}
	
}
