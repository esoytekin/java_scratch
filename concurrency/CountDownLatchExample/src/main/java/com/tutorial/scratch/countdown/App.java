package com.tutorial.scratch.countdown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
	final ExecutorService es = Executors.newFixedThreadPool(5);
	final CountDownLatch cl = new CountDownLatch(5);

	void run() throws InterruptedException {
		
		for (int i = 0; i < 5; i++) {
			es.submit(()->{

				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + " completed task...");
				cl.countDown();

			});
			
		}
		
		es.shutdown();
		
		
		cl.await();
		System.out.println("completed app...");
		
		assert 0 == cl.getCount();
		
		
	}
    public static void main( String[] args ) throws InterruptedException
    {
    	
    	new App().run();
    	
    }
}
