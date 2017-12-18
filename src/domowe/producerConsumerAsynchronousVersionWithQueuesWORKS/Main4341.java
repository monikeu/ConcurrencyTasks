package domowe.producerConsumerAsynchronousVersionWithQueuesWORKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main4341 {

    private final static int runsNumber = 10;
    private static int producersN[];
    private static int consumersN[];

    static Thread[] customers;
    static Thread[] producers;
    static MonitorN monitorN;
    static Random random = new Random();

    public static void main(String[] args) {

        runExperiment("AsyncCons.csv", "AsyncProd.csv", 500, 10, 1000);

//        runExperiment("timeNThreadNumbCons.csv", "timeNThreadNumbProd.csv", 100, 100, 1000);
//        runExperiment("timeNSleepTimeCons.csv", "timeNSleepTimeProd.csv", 100, 1000, 100);
//        runExperiment("timeNBuffSizeCons.csv", "timeNBuffSizeProd.csv", 1000, 100, 100);
//        runExperiment();

    }

//    "timeNThreadNumbCons.csv" "timeNThreadNumbProd.csv"

    public static void runExperiment(String consFileName, String prodFileName, int maxBuffSize, int maxSleepTime, int maxThreads) {

        for (int buffSize = 100; buffSize <= maxBuffSize; buffSize += 100) {
            for (int sleepTime = 10; sleepTime <= maxSleepTime; sleepTime += 10) {
                for (int threadNumb = 200; threadNumb <= maxThreads; threadNumb += 200) {

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

                        monitorN = new MonitorN(buffSize, threadNumb, consFileName, prodFileName);

                        for (int i = 0; i < threadNumb; i++) {
                            customers[i] = new Thread(new Consumer4341(consumersN[i], monitorN, runsNumber, sleepTime, threadNumb));
                            customers[i].start();
                        }


                        for (int i = 0; i < threadNumb; i++) {
                            producers[i] = new Thread(new Producer4341(producersN[i], monitorN, runsNumber, sleepTime, threadNumb));
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
