package com.github.nmorel.gwtjackson.client.arrays;

import java.util.ArrayList;

/**
 * ArrayList that more closely simulates JavaScipt set method,
 * which adds null values if index is bigger then the current
 * list capacity
 */
public class JsList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 6747119276170603387L;

	@Override
	public T set(int index, T element) {
		if(index < size()) {
			return super.set(index, element);
		} else {
			for(int i = size(); i < index; i++) {
				add(null);
			}
			add(element);
			return null;
		}
	}
}
