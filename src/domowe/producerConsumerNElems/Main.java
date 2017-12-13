package domowe.producerConsumerNElems;

import java.util.Random;

public class Main {

    private final static int M = 100;
    private final static int producersNumber = 500;
    private final static int consumersNumber = 500;


    public static void main(String[] args) {

        MonitorN monitorN = new MonitorN(M);

        Thread producers[] = new Thread[producersNumber];
        Thread customers[] = new Thread[consumersNumber];

        Random random = new Random();

        for(int i=0;i<consumersNumber;i++){
            customers[i] = new Thread(new Consumer(random.nextInt(M), monitorN));
            customers[i].start();
        }

        for(int i=0;i<producersNumber;i++){
            producers[i] = new Thread(new Producer(random.nextInt(M), monitorN));
            producers[i].start();
        }

        for(int i=0;i<consumersNumber;i++){
            try {
                customers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<producersNumber;i++){
            try {
                producers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        monitorN.close();
    }
}