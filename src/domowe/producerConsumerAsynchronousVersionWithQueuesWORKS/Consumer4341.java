package domowe.producerConsumerAsynchronousVersionWithQueuesWORKS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Consumer4341 implements Runnable {

    private final MonitorN monitor;
    private  BufferedWriter writerCons;
    private int number;
    private int runsNumber;
    private int sleepTime;
    private int threadNumb;
    private int doneCons =0;


    Consumer4341(int number, MonitorN monitor, int runsNumber, int sleepTime, int threadNumb) {
        this.number = number;
        this.monitor = monitor;
        this.runsNumber = runsNumber;
        this.sleepTime = sleepTime;
        this.threadNumb = threadNumb;
    }

    @Override
    public void run() {
        List<Integer> i;
        while (doneCons < runsNumber) {
            i = monitor.getBegin(number, sleepTime, threadNumb);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monitor.getEnd(i);
            doneCons++;
        }
    }

}