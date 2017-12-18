package domowe.ActiveObject;

import java.util.List;

public class Producer implements Runnable {
    private int number;
    private List<Integer> buffer;
    private int sleepTime;
    private int runsNumber;
    private Proxy p;
    private int done = 0;

    Producer(int number, List<Integer> buffer, Proxy p, int sleepTime, int runsNumber) {
        this.p = p;
        this.number = number;
        this.buffer = buffer;
        this.sleepTime = sleepTime;
        this.runsNumber = runsNumber;
    }

    @Override
    public void run() {

        List<Integer> i;
        while (done < runsNumber) {
            Future f = p.put(number);
            while (!f.isAvailable()) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            done++;
        }
    }
}
