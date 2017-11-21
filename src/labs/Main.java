package labs;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws InterruptedException{
		
		MonitorN s = new MonitorN(11);
		
		List<Thread1> cons = new ArrayList<>();
		List<Thread2> produ = new ArrayList<>();



		for(int i=0; i<10 ; i++){
			produ.add(new Thread2("Prod",s));
			produ.get(i).start();
		}

		for(int i=0; i<10 ; i++){
			cons.add(new Thread1("Cons", s));
			cons.get(i).start();
		}

	}
}
