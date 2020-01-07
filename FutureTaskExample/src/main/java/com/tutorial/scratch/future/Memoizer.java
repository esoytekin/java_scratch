package com.tutorial.scratch.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer<A,V> implements Computable<A, V> {
	
	private final Computable<A, V> computable;
	private final Map<A,Future<V>> cache = new ConcurrentHashMap<>();

	
	public Memoizer(final Computable<A,V> c) {
		this.computable = c;
	}
	
	static <A,V> Memoizer<A,V> cachedMemoizer(final Computable<A,V> c) {
		return new Memoizer<A, V>(c);
	}

	@Override
	public V compute(A value) throws InterruptedException {

		Future<V> future = cache.get(value);
		if (future == null) {

			FutureTask<V> futureTask = new FutureTask<>(() -> computable.compute(value));
			
			future = cache.putIfAbsent(value, futureTask);
			
			//if another thread is not computing already
			if (future == null) {
				future = futureTask; 
				futureTask.run();
			}
			
		}

		try {
			return future.get();
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
	}

	private RuntimeException launderThrowable(Throwable e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException)e;
		} else if (e instanceof Error) {
			throw (Error)e;
		} else {
			throw new IllegalStateException("not unchecked", e);
		}
	}

}
