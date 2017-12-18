package domowe.ActiveObject;

import java.io.*;
import java.util.List;

public class Servant {

    private Buffer buffer;
    private int threadNumb;
    private int n;
    private int sleepTime;
    private String consFileName;
    private String prodFileName;
    private Writer writerCons;
    private Writer writerProd;


    Servant(Buffer b, int threadNumb, int n, int sleepTime,String consFileName, String prodFileName) {

        this.buffer = b;
        this.threadNumb = threadNumb;
        this.n = n;
        this.sleepTime = sleepTime;
        this.consFileName = consFileName;
        this.prodFileName = prodFileName;

        try {
            writerCons = new BufferedWriter((new FileWriter(consFileName, true)));
//            writerCons.write("Time, Threads, SleepTime, Buffsize, Amount\n");
            writerProd = new BufferedWriter((new FileWriter(prodFileName, true)));
//            writerProd.write("Time, Threads, SleepTime, Buffsize, Amount\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void take(ConsumerMethodRequest consumerMethodRequest, List<Integer> toTake) {
        consumerMethodRequest.future.setAvalaible(true);
        try {
            long l = System.currentTimeMillis() - consumerMethodRequest.start;
            writerCons.write(l + "," + threadNumb + "," + sleepTime + "," + n + "," +consumerMethodRequest.n+ "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("Servant is taking " + toTake);
        buffer.freeQ.addAll(toTake);

    }

    public void put(ProducerMethodRequest producerMethodRequest, List<Integer> toPut) {
        producerMethodRequest.future.setAvalaible(true);
        try {
            long l = System.currentTimeMillis() - producerMethodRequest.start;
            writerProd.write(l + "," + threadNumb + "," + sleepTime + "," + n  + "," +producerMethodRequest.n+ "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("Servant is putting " + toPut);

        buffer.fullQ.addAll(toPut);
    }

    public void closeFiles() {
        try {
            writerProd.close();
            writerCons.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
