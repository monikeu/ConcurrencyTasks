package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Main {

    private final static int runsNumber = 10;

    private static Random random = new Random();
    private static List<Integer> buffer = new ArrayList<>();


    public static void main(String[] args) {
        runExperiment("acCons0.csv", "acProd0.csv", 100, 5, 100);
    }

    public static void runExperiment(String consFileName, String prodFileName, int maxBuffSize, int maxSleepTime, int maxThreads) {

        Thread[] customers;
        Thread[] producers;
        int producersN[];
        int consumersN[];

        for (int buffSize = 100; buffSize <= maxBuffSize; buffSize += 100) {
            for (int sleepTime = 1; sleepTime <= maxSleepTime; sleepTime += 1) {
                for (int threadNumb = 10; threadNumb <= maxThreads; threadNumb += 10) {
                    System.out.println("Threads " + threadNumb + " buffsize " + buffSize + "sleeptime " + sleepTime);
                    producers = new Thread[threadNumb];
                    customers = new Thread[threadNumb];

                    producersN = new int[threadNumb];
                    consumersN = new int[threadNumb];

                    for (int i = 0; i < threadNumb; i++) {
                        int i1 = random.nextInt(buffSize);
                        producersN[i] = i1;
                        consumersN[threadNumb - i - 1] = i1;
                    }

                    Proxy p = new Proxy(threadNumb, buffSize, sleepTime, consFileName, prodFileName, runsNumber);
                    p.runScheduler();

                    for (int i = 0; i < threadNumb; i++) {
                        customers[i] = new Thread(new Consumer(consumersN[i], buffer, p, sleepTime, runsNumber));
                        customers[i].start();
                    }

                    for (int i = 0; i < threadNumb; i++) {
                        producers[i] = new Thread(new Producer(producersN[i], buffer, p, sleepTime, runsNumber));
                        producers[i].start();
                    }

                    try {
                        p.joinScheduler();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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

                }
            }
        }
    }
}
