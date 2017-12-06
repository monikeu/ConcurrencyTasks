package domowe.ASYNCVOL2;

import java.util.List;

public class Consumer4341 implements Runnable {

    private int number;
    private MonitorN monitor;
    private List<Integer> buffer;

    Consumer4341(int number, MonitorN monitor,   List<Integer> buffer) {

        this.number = number;
        this.monitor = monitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        List<Integer> i;
        while (true) {
            i = monitor.getBegin(number);
            System.out.println("Consuming elem " + i + "\n");
            monitor.getEnd(i);
        }
    }
}