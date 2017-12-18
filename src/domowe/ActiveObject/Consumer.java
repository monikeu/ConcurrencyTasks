package domowe.ActiveObject;

import java.util.List;
import java.util.Random;

public class Consumer implements Runnable {
    private int number;
    private List<Integer> buffer;
    private int sleepTime;
    private int runsNumber;
    private Proxy p;
    private int done = 0;

    Consumer(int number, List<Integer> buffer, Proxy p, int sleepTime, int runsNumber) {
        this.p = p;
        this.number = number;
        this.buffer = buffer;
        this.sleepTime = sleepTime;
        this.runsNumber = runsNumber;
    }


    @Override
    public void run() {
        Random r = new Random();
        List<Integer> i;
        while (done < runsNumber) {
            Future f = p.take(number);
            while (!f.isAvailable()) {
                try {
                    Thread.sleep(r.nextInt(sleepTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            done++;
        }
    }
}
