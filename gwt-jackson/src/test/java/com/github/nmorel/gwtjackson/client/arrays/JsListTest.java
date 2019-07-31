package com.github.nmorel.gwtjackson.client.arrays;

import java.util.List;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;

public class JsListTest extends GwtJacksonTestCase {

	public void testOverridingValueAtIndex() {
		List<Integer> list = new JsList<Integer>();
		list.add(1);
		list.add(2);
		list.set(1, 3);

		assertEquals(list.get(1).intValue(), 3);
		assertEquals(list.size(), 2);
	}

	public void testSetValueAtIndexEqualToListSize() {
		List<Integer> list = new JsList<Integer>();
		list.add(1);
		list.add(2);
		list.set(2, 3);

		assertEquals(list.get(1).intValue(), 2);
		assertEquals(list.get(2).intValue(), 3);
		assertEquals(list.size(), 3);
	}

	public void testSetValueAtIndexBiggerThanListSize() {
		List<Integer> list = new JsList<Integer>();
		list.add(1);
		list.set(2, 3);

		assertNull(list.get(1));
		assertEquals(list.get(2).intValue(), 3);
		assertEquals(list.size(), 3);
	}
}
