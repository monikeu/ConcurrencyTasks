package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws InterruptedException{
		
		MonitorN s = new MonitorN(11);
		
		List<Thread1> cons = new ArrayList<>();
		List<Thread2> produ = new ArrayList<>();
		
		for(int i=0; i<10 ; i++){
			cons.add(new Thread1("Cons", s));
			produ.add(new Thread2("Prod",s));
			
			cons.get(i).start();
			produ.get(i).start();
		}

	}
}
