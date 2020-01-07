package com.tutorial.scratch.future;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Hello world!
 *
 */
public class App 
{

	private final ExecutorService es = Executors.newFixedThreadPool(5);
	private final Memoizer<String,BigInteger> cachedMemoizer = Memoizer.cachedMemoizer(new ExpensiveCompute());
	private final String[] tasks = {"2","2","2","3","4444"};
	private final Semaphore semaphore = new Semaphore(2);

	private void run() {
		
		for (String task : tasks) {
			
			es.submit(() -> {
				try {
					semaphore.acquire();
					BigInteger compute = cachedMemoizer.compute(task);
					System.out.println(String.format("%s is computed as %d.", task, compute));
					semaphore.release();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			});
			
		}
		
		es.shutdown();
		
	}

    public static void main( String[] args )
    {
    	new App().run();
    }
}
