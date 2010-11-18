package com.qtone.datasync.misc.util;

import org.testng.annotations.Test;

/**
 * @author ÑîÌÚ·É	2009-9-9 
 *
 */
public class MyThreadPoolTest {
	@Test
	public void testThreadPool() throws InterruptedException{
		MyThreadPool pool = new MyThreadPool();
		pool.submit(new Runnable(){

			public void run() {
				try {
					Thread.sleep(1000 * 3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		pool.shutdown();
	}
}
