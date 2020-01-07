package com.tutorial.scratch.future;

public interface Computable<A,V> {
	
	V compute(A value) throws InterruptedException;

}
