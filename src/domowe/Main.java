package domowe;

import java.util.Random;

public class Main {

    private final static int M = 10;
    private final static int producersNumber = 10;
    private final static int consumersNumber = 10;


    public static void main(String[] args) {

        MonitorN monitorN = new MonitorN(M);

        Thread producers[] = new Thread[producersNumber];
        Thread customers[] = new Thread[consumersNumber];

        Random random = new Random();

        for(int i=0;i<producersNumber;i++){
            producers[i] = new Thread(new Producer(random.nextInt(M)+1, monitorN));
            producers[i].start();
        }

        for(int i=0;i<consumersNumber;i++){
            customers[i] = new Thread(new Consumer(random.nextInt(M), monitorN));
            customers[i].start();
        }
    }
}