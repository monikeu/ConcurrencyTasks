package labs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorNV1 {
	private int value;
	private int MAX = 10;
	private Lock lock =new ReentrantLock();
	private Condition pierwszkons = lock.newCondition();
	private Condition pierwszprod = lock.newCondition();




	public MonitorNV1(int n) {
	    this.value = n;
	}

	 void produce(int k) {
		lock.lock();
		
		while(2*MAX - value < k){
			try {
				pierwszprod.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		value=value + k;
		System.out.print("producing" + k + "\n");
		System.out.print(value + "\n");   
	    
		pierwszprod.signalAll();
		pierwszkons.signal();
		
		lock.unlock();

	  
	}

	 
	 void consume(int k){
		 lock.lock();
			while(k > value){
				try {
					pierwszkons.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			value= value - k;
			System.out.print("consuming " + k +  " \n");
			System.out.print(value + "\n");
			
			pierwszkons.signalAll();
			pierwszprod.signal();
			lock.unlock();	   
		   
		}
}
