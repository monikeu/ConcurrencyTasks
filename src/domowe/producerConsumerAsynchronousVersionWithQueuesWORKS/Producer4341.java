package domowe.producerConsumerAsynchronousVersionWithQueuesWORKS;

import java.util.List;

public class Producer4341 implements Runnable {

    private final MonitorN monitor;
    private int number;
    private int sleepTime;
    private int threadNumb;
    private int runsNumber;

    public int doneProds = 0;


    Producer4341(int number, MonitorN monitor, int runsNumber, int sleepTime, int threadNumb) {

        this.number = number;
        this.runsNumber = runsNumber;
        this.monitor = monitor;
        this.sleepTime = sleepTime;
        this.threadNumb = threadNumb;
    }


    @Override
    public void run() {

        List<Integer> i;
        while (doneProds < runsNumber) {
            i = monitor.putBegin(number,sleepTime, threadNumb);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monitor.putEnd(i);
            doneProds++;
        }
    }

}
