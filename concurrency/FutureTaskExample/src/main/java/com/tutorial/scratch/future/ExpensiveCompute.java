package com.tutorial.scratch.future;

import java.math.BigInteger;

public class ExpensiveCompute implements Computable<String, BigInteger> {

	@Override
	public BigInteger compute(String value) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return new BigInteger(value);
	}

}
