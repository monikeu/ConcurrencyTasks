package domowe.producerConsumerAsynchronous;

import java.util.Random;

public class Main {

    private final static int runsNumber = 10;
    private static int producersN[];
    private static int consumersN[];

    static Thread[] customers;
    static Thread[] producers;
    static Monitor monitorN;
    static Random random = new Random();

    public static void main(String[] args) {

        runExperiment("AsyncCons0.csv", "AsyncProd0.csv", 100, 5, 100);

//        runExperiment("timeNThreadNumbCons.csv", "timeNThreadNumbProd.csv", 100, 100, 1000);
//        runExperiment("timeNSleepTimeCons.csv", "timeNSleepTimeProd.csv", 100, 1000, 100);
//        runExperiment("timeNBuffSizeCons.csv", "timeNBuffSizeProd.csv", 1000, 100, 100);
//        runExperiment();

    }

//    "timeNThreadNumbCons.csv" "timeNThreadNumbProd.csv"

    public static void runExperiment(String consFileName, String prodFileName, int maxBuffSize, int maxSleepTime, int maxThreads) {

        for (int buffSize = 100; buffSize <= maxBuffSize; buffSize += 100) {
            for (int sleepTime = 1; sleepTime <= maxSleepTime; sleepTime += 1) {
                for (int threadNumb = 10; threadNumb <= maxThreads; threadNumb += 10) {

                    System.out.println("Threads " + threadNumb + " buffsize " + buffSize + " sleeptime " + sleepTime);
                        producersN = new int[threadNumb];
                        consumersN = new int[threadNumb];

                        producers = new Thread[threadNumb];
                        customers = new Thread[threadNumb];


                        for (int i = 0; i < threadNumb; i++) {
                            int i1 = random.nextInt(buffSize);
                            producersN[i] = i1;
                            consumersN[threadNumb - i - 1] = i1;
                        }

                        monitorN = new Monitor(buffSize, threadNumb, consFileName, prodFileName);

                        for (int i = 0; i < threadNumb; i++) {
                            customers[i] = new Thread(new Consumer(consumersN[i], monitorN, runsNumber, sleepTime, threadNumb));
                            customers[i].start();
                        }


                        for (int i = 0; i < threadNumb; i++) {
                            producers[i] = new Thread(new Producer(producersN[i], monitorN, runsNumber, sleepTime, threadNumb));
                            producers[i].start();
                        }

                        for (int i = 0; i < threadNumb; i++) {
                            try {
                                customers[i].join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        for (int i = 0; i < threadNumb; i++) {
                            try {
                                producers[i].join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        monitorN.close();

                }
            }
        }
    }
}
