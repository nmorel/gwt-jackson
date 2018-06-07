package com.github.nmorel.gwtjackson.client.arrays;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayNumber;

/**
 * Wrapper that selects an array implementation based on
 * the mode you are running (Dev Mode vs Compiled)
 */
public class FastArrayNumber {

	private JsArrayNumber stackNative;
	private List<Double> stackJava;


	public FastArrayNumber() {
		if(GWT.isScript()) {
			stackNative = JsArrayNumber.createArray().cast();
		} else {
			stackJava = new JsList<Double>();
		}
	}


	public void set(int index, double value) {
		if(GWT.isScript()) {
			stackNative.set(index, value);
		} else {
			stackJava.set(index, value);
		}
	}

	public void push(double value) {
		if(GWT.isScript()) {
			stackNative.push(value);
		} else {
			stackJava.add(value);
		}
	}

	public double get(int index) {
		if(GWT.isScript()) {
			return stackNative.get(index);
		} else {
			return stackJava.get(index);
		}
	}

	public double[] reinterpretCast() {
		if(GWT.isScript()) {
			return reinterpretCast(stackNative);
		} else {
			double[] ret = new double[stackJava.size()];
			for (int i = 0; i < stackJava.size(); i++) {
				ret[i] = stackJava.get(i);
			}
			return ret;
		}
	}

	private static native double[] reinterpretCast( JsArrayNumber value ) /*-{
	    return value;
	}-*/;
}
