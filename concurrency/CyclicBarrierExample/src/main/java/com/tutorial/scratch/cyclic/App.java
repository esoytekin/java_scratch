package com.tutorial.scratch.cyclic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
	
	final ExecutorService es = Executors.newFixedThreadPool(5);
	final Random random = new Random();
	final List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<Integer>());
	
	void run() {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
			
			Integer reduce = synchronizedList.stream().reduce(0, (subtotal, e) -> e+subtotal);
			System.out.println("total is : " + reduce);
			
		});
		
		for (int i = 0; i < 5; i++) {
			
			es.submit(() -> {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + " started...");
				int nextInt = random.nextInt(10) + 1;
				System.out.println(threadName + ": next int is: " + nextInt);
				synchronizedList.add(nextInt);
				try {
					cyclicBarrier.await();
					System.out.println(threadName + " finished...");
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}
		
	   System.out.println("application terminate..");
			
		
	}
    public static void main( String[] args )
    {

    	new App().run();
    }
}
