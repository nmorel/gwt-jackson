package com.github.nmorel.gwtjackson.client.arrays;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayInteger;

/**
 * Wrapper that selects an array implementation based on
 * the mode you are running (Dev Mode vs Compiled)
 */
public class FastArrayShort {

	private JsArrayInteger stackNative;
	private List<Short> stackJava;


	public FastArrayShort() {
		if(GWT.isScript()) {
			stackNative = JsArrayInteger.createArray().cast();
		} else {
			stackJava = new JsList<Short>();
		}
	}


	public void set(int index, short value) {
		if(GWT.isScript()) {
			stackNative.set(index, value);
		} else {
			stackJava.set(index, value);
		}
	}

	public void push(short value) {
		if(GWT.isScript()) {
			stackNative.push(value);
		} else {
			stackJava.add(value);
		}
	}

	public short get(int index) {
		if(GWT.isScript()) {
			return (short) stackNative.get(index);
		} else {
			return stackJava.get(index);
		}
	}

	public short[] reinterpretCast() {
		if(GWT.isScript()) {
			return reinterpretCast(stackNative);
		} else {
			short[] ret = new short[stackJava.size()];
			for (int i = 0; i < stackJava.size(); i++) {
				ret[i] = stackJava.get(i);
			}
			return ret;
		}
	}

	private static native short[] reinterpretCast( JsArrayInteger value ) /*-{
	    return value;
	}-*/;
}
