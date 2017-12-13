package domowe.ASYNCVOL2;

import java.util.List;

public class Producer4341 implements Runnable {

    private int number;
    private MonitorN monitor;
    private List<Integer> buffer;
    private long start;

    Producer4341(int number, MonitorN monitor, List<Integer> buffer) {

        this.number = number;
        this.monitor = monitor;
        this.buffer = buffer;

    }

    @Override
    public void run() {

        List<Integer> i;
        while (monitor.done < 10) {
            start=System.currentTimeMillis();
            i = monitor.putBegin(number);
            System.out.println("Producing into " + i + "\n");
            // do sth with ith elem of
            monitor.putEnd(start,i);
        }
    }
}
