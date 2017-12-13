package domowe.producerConsumerAsynchronousVersionWithQueuesWORKS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Consumer4341 implements Runnable {

    private  BufferedWriter writerCons;
    private int number;
    private MonitorN monitor;
    private List<Integer> buffer;
    public static  int doneCons = 0;
    long start;

    Consumer4341(int number, MonitorN monitor,   List<Integer> buffer) {


        this.number = number;
        this.monitor = monitor;
        this.buffer = buffer;
        try {
            writerCons = new BufferedWriter((new FileWriter("asyncCons.csv")));
            writerCons.write("Time, Amount\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        List<Integer> i;
        while (doneCons < 5000 && Producer4341.doneProds <5000) {
            start=System.currentTimeMillis();
            i = monitor.getBegin(number);
            write(number, System.currentTimeMillis()-start);
//            System.out.println("Consuming elem " + i + "\n");
            monitor.getEnd(i);
            doneCons++;
        }
    }

    synchronized void write(int n, long time){
        try {
            writerCons.write(time + "," + n + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}