package labs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

private int value;

private Lock lock =new ReentrantLock();
private Condition cond = lock.newCondition();


public  Monitor(int n) {
    this.value = n;
}

 void produce() {
	lock.lock();
	while(value == 1){
		try {
			cond.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	value++;
	System.out.print("producing \n");
	System.out.print(value + "\n");   
    
	cond.signalAll();
	lock.unlock();

  
}

 
 void consume(){
	 lock.lock();
		while(value == 0){
			try {
				cond.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		value--;
	    
		cond.signalAll();
		System.out.print("consuming\n");
		System.out.print(value + "\n");
		lock.unlock();	   
	   
	}
//synchronized void produce() {
//	while(value == 1){
//		try {
//			wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//		
//    value++;
//    System.out.print("producing\n");
//    System.out.print(value + "\n");
//    notifyAll();
//   
//}

//synchronized void consume(){
//	while(value == 0){
//		try {
//			wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//    value--;
//    System.out.print("consuming\n");
//    System.out.print(value + "\n");
//    notifyAll();
//   
//}

public int getI(){
	return value;
}
}

// prod cons na locki
// prod cons to z kartkówki