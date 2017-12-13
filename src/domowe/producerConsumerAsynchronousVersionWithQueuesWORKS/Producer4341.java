package domowe.producerConsumerAsynchronousVersionWithQueuesWORKS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Producer4341 implements Runnable {

    private  BufferedWriter writerProd;
    private int number;
    private MonitorN monitor;
    private List<Integer> buffer;
    private long start;

    public static int doneProds = 0;


    Producer4341(int number, MonitorN monitor, List<Integer> buffer) {

        this.number = number;
        this.monitor = monitor;
        this.buffer = buffer;
        try {
            writerProd = new BufferedWriter((new FileWriter("asyncProd.csv")));
            writerProd.write("Time, Amount\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        List<Integer> i;
        while (Consumer4341.doneCons < 5000 && doneProds <5000) {
            start=System.currentTimeMillis();
            i = monitor.putBegin(number);
            write(number, System.currentTimeMillis()-start);
//            System.out.println("Producing into " + i + "\n");
            // do sth with ith elem of
            monitor.putEnd(i);
            doneProds++;
        }
    }

    synchronized void write(int n, long time){
        try {
            writerProd.write(time + "," + n + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
