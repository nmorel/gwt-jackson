package com.github.nmorel.gwtjackson.client.arrays;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayInteger;

/**
 * Wrapper that selects an array implementation based on
 * the mode you are running (Dev Mode vs Compiled)
 */
public class FastArrayInteger {

	private JsArrayInteger stackNative;
	private List<Integer> stackJava;


	public FastArrayInteger(){
		if(GWT.isScript()) {
			stackNative = JsArrayInteger.createArray().cast();
		} else {
			stackJava = new JsList<Integer>();
		}
	}


	public void set(int index, int value) {
		if(GWT.isScript()) {
			stackNative.set(index, value);
		} else {
			stackJava.set(index, value);
		}
	}

	public void push(int value) {
		if(GWT.isScript()) {
			stackNative.push(value);
		} else {
			stackJava.add(value);
		}
	}

	public int get(int index) {
		if(GWT.isScript()) {
			return stackNative.get(index);
		} else {
			return stackJava.get(index);
		}
	}

	public int[] reinterpretCast() {
		if(GWT.isScript()) {
			return reinterpretCast(stackNative);
		} else {
			int[] ret = new int[stackJava.size()];
			for (int i = 0; i < stackJava.size(); i++) {
				ret[i] = stackJava.get(i);
			}
			return ret;
		}
	}

	private static native int[] reinterpretCast( JsArrayInteger value ) /*-{
	    return value;
	}-*/;
}
