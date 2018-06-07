package com.github.nmorel.gwtjackson.client.arrays;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;

/**
 * Wrapper that selects an array implementation based on
 * the mode you are running (Dev Mode vs Compiled)
 */
public class FastArrayString {

	private JsArrayString stackNative;
	private List<String> stackJava;


	public FastArrayString(){
		if(GWT.isScript()) {
			stackNative = JsArrayString.createArray().cast();
		} else {
			stackJava = new JsList<String>();
		}
	}


	public void set(int index, String value) {
		if(GWT.isScript()) {
			stackNative.set(index, value);
		} else {
			stackJava.set(index, value);
		}
	}

	public void push(String value) {
		if(GWT.isScript()) {
			stackNative.push(value);
		} else {
			stackJava.add(value);
		}
	}

	public String get(int index) {
		if(GWT.isScript()) {
			return stackNative.get(index);
		} else {
			return stackJava.get(index);
		}
	}

	public String[] reinterpretCast() {
		if(GWT.isScript()) {
			return reinterpretCast(stackNative);
		} else {
			String[] ret = new String[stackJava.size()];
			for (int i = 0; i < stackJava.size(); i++) {
				ret[i] = stackJava.get(i);
			}
			return ret;
		}
	}

	private static native String[] reinterpretCast( JsArrayString value ) /*-{
	    return value;
	}-*/;
}
