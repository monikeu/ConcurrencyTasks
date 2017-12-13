package domowe.ActiveObject;

import java.util.List;
import java.util.Random;

public class Producer implements Runnable {
    private int number;
    private List<Integer> buffer;
    private Proxy p;
    private int done = 0;

    Producer(int number, List<Integer> buffer, Proxy p) {
        this.p = p;
        this.number = number;
        this.buffer = buffer;
    }

    @Override
    public void run() {

        Random random = new Random();
        List<Integer> i;
        while (true) {
            Future f = p.put(number);
//            System.out.println("Producer requested producing " + number + " elems\n");
            while (!f.isAvailable()) {
                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(Scheduler.done >= 10000) break; ;
            }
            if(Scheduler.done >= 10000)break; ;

        }
    }
}
